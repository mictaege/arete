# Internals Specification

## Purpose

Internal helper contracts of the arete core library that other family members depend on:
`Normalizer`/`UniqueIdToHashNormalizer` (stable hashing of JUnit unique ids) and `InnerClassUtils`
(package-private instantiation of nested test classes). These are not user-facing annotations, but
the hash contract is load-bearing for arete-gradle's report and history.

## Requirements

### Requirement: UniqueIdToHashNormalizer produces stable hashes

`UniqueIdToHashNormalizer` SHALL implement `Normalizer<String, String>`, hashing the input with
Guava `Hashing.murmur3_128()`, taking `hash().asLong()` as decimal string and replacing `-` with `_`
(e.g. for use in file names). The algorithm and mapping SHALL NOT change across releases: arete-gradle
derives `uniqueHash` from it for report file names (`specs/<hash>.html`), in-page anchors, the History
store directory keys, and `@SeeAlso`/`@Ref` link targets — a change would silently break existing
links, anchors and stored history.

#### Scenario: Hash is deterministic and file-name safe
- **WHEN** the same JUnit uniqueId is normalized twice
- **THEN** both results are identical and contain no `-`

### Requirement: Normalizer is the generic hashing interface

`Normalizer<I, O>` SHALL be the generic interface with a single `normalize(I): O` method;
`UniqueIdToHashNormalizer` implements it as `Normalizer<String, String>`. arete-gradle's
`TestIdentifierNormalizer` extends it for `TestIdentifier` → `String` via `UniqueIdToHashNormalizer`.

#### Scenario: TestIdentifier normalization delegates to the string normalizer
- **WHEN** arete-gradle normalizes a `TestIdentifier`
- **THEN** the result equals `UniqueIdToHashNormalizer().normalize(testIdentifier.uniqueId)`

### Requirement: InnerClassUtils instantiates nested classes

The package-private `InnerClassUtils.newInstance(Class<T>)` SHALL instantiate inner (non-static
nested) classes by walking the enclosing-class chain, constructing each outer instance and passing
it to the inner constructor (found via `getDeclaredConstructor(outerClass)`, made accessible);
failures SHALL surface as `IllegalStateException` naming the class.

#### Scenario: Deeply nested scenario instantiation
- **WHEN** a `@Scenario` is nested inside a `@Feature` inside a `@Spec`
- **THEN** `newInstance` builds the outer spec and feature instances and returns the scenario
  instance wired to them

package com.github.mictaege.arete;

public interface Normalizer<I,O> {

    O normalize(final I input);

}

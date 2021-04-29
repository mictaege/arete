package com.github.mictaege.arete;

import static com.github.mictaege.arete.NamingTools.Capitalizer.CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.Capitalizer.LEAVE_AS_IS;
import static com.github.mictaege.arete.NamingTools.Capitalizer.UN_CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.Splitter.SPLIT_BY_CAMELCASE;
import static com.github.mictaege.arete.NamingTools.Splitter.SPLIT_BY_UNDERSCORE;
import static com.github.mictaege.arete.NamingTools.toWords;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class NamingToolsSpec {

    @Describe class CamelCaseHandling {

        @ItShould void splitMultiTokenStrings() {
            assertThat(toWords("camelCase", SPLIT_BY_CAMELCASE, LEAVE_AS_IS), is("camel Case"));
        }
        
        @ItShould void splitSingleTokenStrings() {
            assertThat(toWords("camel", SPLIT_BY_CAMELCASE, LEAVE_AS_IS), is("camel"));
        }
        
        @ItShould void splitAndCapitalize() {
            assertThat(toWords("camelCase", SPLIT_BY_CAMELCASE, CAPITALIZE_ALL), is("Camel Case"));
        }
        
        @ItShould void splitAndUncapitalize() {
            assertThat(toWords("camelCase", SPLIT_BY_CAMELCASE, UN_CAPITALIZE_ALL), is("camel case"));
        }
        
        @ItShould void notSplitAcronyms() {
            assertThat(toWords("camelCASE", SPLIT_BY_CAMELCASE, CAPITALIZE_ALL), is("Camel CASE"));
        }

    }

    @Describe class UnderscoreHandling {
        
        @ItShould void splitMultiTokenStrings() {
            assertThat(toWords("camel_case", SPLIT_BY_UNDERSCORE, LEAVE_AS_IS), is("camel case"));
        }
        
        @ItShould void splitSingleTokenStrings() {
            assertThat(toWords("camel", SPLIT_BY_UNDERSCORE, LEAVE_AS_IS), is("camel"));
        }
        
        @ItShould void splitAndCapitalize() {
            assertThat(toWords("camel_Case", SPLIT_BY_UNDERSCORE, CAPITALIZE_ALL), is("Camel Case"));
        }
        
        @ItShould void splitAndUncapitalize() {
            assertThat(toWords("camel_Case", SPLIT_BY_UNDERSCORE, UN_CAPITALIZE_ALL), is("camel case"));
        }
        
        @ItShould void notSplitAcronyms() {
            assertThat(toWords("camel_CASE", SPLIT_BY_UNDERSCORE, CAPITALIZE_ALL), is("Camel CASE"));
        }
        
    }

    @Describe class StyleDetection {

        @ItShould void detectUnderscoreStyle() {
            assertThat(toWords("camel_case", LEAVE_AS_IS), is("camel case"));
        }

        @ItShould void detectCamelCaseStyle() {
            assertThat(toWords("camelCase", LEAVE_AS_IS), is("camel Case"));
        }

    }

}
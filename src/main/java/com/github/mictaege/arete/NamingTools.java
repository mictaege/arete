package com.github.mictaege.arete;

import static java.util.Optional.ofNullable;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import com.google.common.base.Joiner;

class NamingTools {

    enum Splitter {
        SPLIT_BY_CAMELCASE(StringUtils::splitByCharacterTypeCamelCase),
        SPLIT_BY_UNDERSCORE(s -> StringUtils.split(s, "_"));

        static Splitter findBest(final String rawString) {
            if (rawString.contains("_")) {
                return SPLIT_BY_UNDERSCORE;
            } else {
                return SPLIT_BY_CAMELCASE;
            }
        }

        private Function<String, String[]> splitterFun;

        Splitter(final Function<String, String[]> splitterFun) {
            this.splitterFun = splitterFun;
        }

        Function<String, String[]> getSplitter() {
            return splitterFun;
        }
    }

    enum Capitalizer {
        UN_CAPITALIZE_ALL(WordUtils::uncapitalize),
        CAPITALIZE_ALL(WordUtils::capitalize),
        LEAVE_AS_IS(s -> s);

        private final Function<String, String> capitalizerFun;

        Capitalizer(final Function<String, String> capitalizerFun) {
            this.capitalizerFun = capitalizerFun;
        }

        public Function<String, String> getCapitalizer() {
            return capitalizerFun;
        }
    }

    static String toWords(final String rawName, final Capitalizer capitalizer) {
        return toWords(rawName, Splitter.findBest(rawName), capitalizer);
    }

    static String toWords(final String rawName, final Splitter splitter, final Capitalizer capitalizer) {
        return ofNullable(rawName)
                .map(splitter.getSplitter())
                .map(t -> Joiner.on(" ").join(t))
                .map(capitalizer.getCapitalizer())
                .orElse("");
    }

}

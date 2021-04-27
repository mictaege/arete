package com.github.mictaege.arete;

import static java.util.Optional.ofNullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import com.google.common.base.Joiner;

class Tools {

    static String uncapitalize(final String camelCase) {
        return ofNullable(camelCase)
                .map(StringUtils::splitByCharacterTypeCamelCase)
                .map(t -> Joiner.on(" ").join(t))
                .map(WordUtils::uncapitalize)
                .orElse("");
    }

    static String capitalize(final String camelCase) {
        return ofNullable(camelCase)
                .map(StringUtils::splitByCharacterTypeCamelCase)
                .map(t -> Joiner.on(" ").join(t))
                .map(WordUtils::capitalize)
                .orElse("");
    }

}

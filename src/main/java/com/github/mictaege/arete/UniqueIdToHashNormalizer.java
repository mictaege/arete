package com.github.mictaege.arete;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.hash.Hashing;

public class UniqueIdToHashNormalizer implements Normalizer<String, String> {

    @Override
    public String normalize(final String uniqueId) {
        final String hashStr = "" + Hashing.murmur3_128().newHasher().putString(uniqueId, UTF_8).hash().asLong();
        return hashStr.replace("-", "_");
    }

}

package com.github.mictaege.arete;

import static java.util.Comparator.comparingInt;

class SeqAndStepPriority implements Comparable<SeqAndStepPriority> {
    static SeqAndStepPriority priority(final int sequence, final int semantic, final int step) {
        return new SeqAndStepPriority(sequence, semantic, step);
    }

    private final int sequence;
    private final int semantic;
    private final int step;

    SeqAndStepPriority(final int sequence, final int semantic, final int step) {
        this.sequence = sequence;
        this.semantic = semantic;
        this.step = step;
    }

    int getSequence() {
        return sequence;
    }

    int getSemantic() {
        return semantic;
    }

    int getStep() {
        return step;
    }

    @Override
    public int compareTo(final SeqAndStepPriority o) {
        return comparingInt(SeqAndStepPriority::getSequence)
                .thenComparingInt(SeqAndStepPriority::getSemantic)
                .thenComparingInt(SeqAndStepPriority::getStep)
                .compare(this, o);
    }
}

package com.github.mictaege.arete;

import static java.util.Comparator.comparingInt;

class SeqAndStepOrder implements Comparable<SeqAndStepOrder> {
    static SeqAndStepOrder stepOrderDef(final int sequence, final int step) {
        return new SeqAndStepOrder(sequence, step);
    }

    private final int sequence;
    private final int step;

    SeqAndStepOrder(final int sequence, final int step) {
        this.sequence = sequence;
        this.step = step;
    }

    int getSequence() {
        return sequence;
    }

    int getStep() {
        return step;
    }

    @Override
    public int compareTo(final SeqAndStepOrder o) {
        return comparingInt(SeqAndStepOrder::getSequence)
                .thenComparingInt(SeqAndStepOrder::getStep)
                .compare(this, o);
    }
}

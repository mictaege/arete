package com.github.mictaege.arete;

import org.junit.jupiter.api.Order;

import static java.util.Comparator.comparingInt;

class AreteOrder implements Comparable<AreteOrder> {

    static AreteOrder stepOrder(final int step) {
        return new AreteOrder(Order.DEFAULT, Order.DEFAULT, step);
    }

    static AreteOrder scenarioOrder(final int sequence, final int semantic, final int step) {
        return new AreteOrder(sequence, semantic, step);
    }

    private final int sequence;
    private final int semantic;
    private final int step;

    AreteOrder(final int sequence, final int semantic, final int step) {
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
    public int compareTo(final AreteOrder o) {
        return comparingInt(AreteOrder::getSequence)
                .thenComparingInt(AreteOrder::getSemantic)
                .thenComparingInt(AreteOrder::getStep)
                .compare(this, o);
    }
}

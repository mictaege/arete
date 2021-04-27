package com.github.mictaege.arete;

import static com.github.mictaege.arete.ScenarioOrderer.StepOrderDef.stepOrderDef;
import static java.lang.Math.max;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;

public class ScenarioOrderer implements MethodOrderer {

    @Override
    public void orderMethods(MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparing(ScenarioOrderer::getOrder));
    }

    private static StepOrderDef getOrder(MethodDescriptor descriptor) {
        final AtomicReference<StepOrderDef> order = new AtomicReference<>(stepOrderDef(1, Order.DEFAULT));
        descriptor.findAnnotation(Given.class).ifPresent(g -> {
            order.set(stepOrderDef(g.seq(), max(g.value(), g.step())));
        });
        descriptor.findAnnotation(When.class).ifPresent(w -> {
            order.set(stepOrderDef(w.seq(), max(w.value(), w.step()) + 1000));
        });
        descriptor.findAnnotation(Then.class).ifPresent(t -> {
            order.set(stepOrderDef(t.seq(), max(t.value(), t.step()) + 1500));
        });
        descriptor.findAnnotation(Order.class).ifPresent(t -> {
            order.set(stepOrderDef(1, t.value()));
        });
        return order.get();
    }

    static class StepOrderDef implements Comparable<StepOrderDef> {
        static StepOrderDef stepOrderDef(final int sequence, final int step) {
            return new StepOrderDef(sequence, step);
        }
        private final int sequence;
        private final int step;

        StepOrderDef(final int sequence, final int step) {
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
        public int compareTo(final StepOrderDef o) {
            return comparingInt(StepOrderDef::getSequence)
                    .thenComparingInt(StepOrderDef::getStep)
                    .compare(this, o);
        }
    }

}


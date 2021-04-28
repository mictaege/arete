package com.github.mictaege.arete;

import static com.github.mictaege.arete.SeqAndStepOrder.stepOrderDef;
import static java.lang.Math.max;
import static java.util.Comparator.comparing;

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

    private static SeqAndStepOrder getOrder(MethodDescriptor descriptor) {
        final AtomicReference<SeqAndStepOrder> order = new AtomicReference<>(stepOrderDef(1, Order.DEFAULT));
        descriptor.findAnnotation(Given.class).ifPresent(g -> {
            order.set(stepOrderDef(g.seq(), max(g.value(), g.step())));
        });
        descriptor.findAnnotation(When.class).ifPresent(w -> {
            order.set(stepOrderDef(w.seq(), max(w.value(), w.step()) + 1000));
        });
        descriptor.findAnnotation(Then.class).ifPresent(t -> {
            order.set(stepOrderDef(t.seq(), max(t.value(), t.step()) + 1500));
        });
        descriptor.findAnnotation(Order.class).ifPresent(o -> {
            order.set(stepOrderDef(1, o.value()));
        });
        return order.get();
    }

}


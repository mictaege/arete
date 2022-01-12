package com.github.mictaege.arete;

import static com.github.mictaege.arete.SeqAndStepPriority.priority;
import static java.lang.Math.max;
import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Order.DEFAULT;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;

public class ScenarioOrderer implements MethodOrderer {

    @Override
    public void orderMethods(final MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparing(ScenarioOrderer::getOrder));
    }

    private static SeqAndStepPriority getOrder(final MethodDescriptor descriptor) {
        final AtomicReference<SeqAndStepPriority> order = new AtomicReference<>(priority(DEFAULT, DEFAULT, DEFAULT));
        descriptor.findAnnotation(Given.class).ifPresent(g -> {
            order.set(priority(g.seq(), 1, max(g.value(), g.step())));
        });
        descriptor.findAnnotation(When.class).ifPresent(w -> {
            order.set(priority(w.seq(), 2, max(w.value(), w.step())));
        });
        descriptor.findAnnotation(Then.class).ifPresent(t -> {
            order.set(priority(t.seq(), 3, max(t.value(), t.step())));
        });
        descriptor.findAnnotation(Examples.class).ifPresent(e -> {
            order.set(priority(DEFAULT, DEFAULT, e.order()));
        });
        descriptor.findAnnotation(Order.class).ifPresent(o -> {
            order.set(priority(DEFAULT, DEFAULT, o.value()));
        });
        return order.get();
    }

}


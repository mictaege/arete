package com.github.mictaege.arete;

import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.mictaege.arete.AreteOrder.scenarioOrder;
import static com.github.mictaege.arete.AreteOrder.stepOrder;
import static java.lang.Math.max;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

public class AreteOrderer implements ClassOrderer, MethodOrderer {

    @Override
    public void orderClasses(ClassOrdererContext context) {
        context.getClassDescriptors().sort(comparingInt(AreteOrderer::getOrder));
    }

    @Override
    public void orderMethods(final MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparing(AreteOrderer::getOrder));
    }

    private static int getOrder(final ClassDescriptor descriptor) {
        final AtomicReference<Integer> order = new AtomicReference<>(Order.DEFAULT);
        descriptor.findAnnotation(Feature.class).ifPresent(f -> {
            order.set(max(f.value(), f.order()));
        });
        descriptor.findAnnotation(Scenario.class).ifPresent(s -> {
            order.set(max(s.value(), s.order()));
        });
        descriptor.findAnnotation(Describe.class).ifPresent(d -> {
            order.set(max(d.value(), d.order()));
        });
        descriptor.findAnnotation(Journey.class).ifPresent(j -> {
            order.set(max(j.value(), j.order()));
        });
        descriptor.findAnnotation(VariableJourney.class).ifPresent(v -> {
            order.set(max(v.value(), v.order()));
        });
        descriptor.findAnnotation(Order.class).ifPresent(o -> {
            order.set(o.value());
        });
        return order.get();
    }

    private static AreteOrder getOrder(final MethodDescriptor descriptor) {
        final AtomicReference<AreteOrder> order = new AtomicReference<>(stepOrder(Order.DEFAULT));
        descriptor.findAnnotation(Given.class).ifPresent(g -> {
            order.set(scenarioOrder(g.seq(), 1, max(g.value(), g.step())));
        });
        descriptor.findAnnotation(When.class).ifPresent(w -> {
            order.set(scenarioOrder(w.seq(), 2, max(w.value(), w.step())));
        });
        descriptor.findAnnotation(Then.class).ifPresent(t -> {
            order.set(scenarioOrder(t.seq(), 3, max(t.value(), t.step())));
        });
        descriptor.findAnnotation(ItShould.class).ifPresent(i -> {
            order.set(stepOrder(max(i.value(), i.order())));
        });
        descriptor.findAnnotation(Step.class).ifPresent(s -> {
            order.set(stepOrder(max(s.value(), s.order())));
        });
        descriptor.findAnnotation(Examples.class).ifPresent(e -> {
            order.set(stepOrder(max(e.value(), e.order())));
        });
        descriptor.findAnnotation(ExampleGrid.class).ifPresent(e -> {
            order.set(stepOrder(max(e.value(), e.order())));
        });
        descriptor.findAnnotation(ExampleCsv.class).ifPresent(e -> {
            order.set(stepOrder(max(e.value(), e.order())));
        });
        descriptor.findAnnotation(Order.class).ifPresent(o -> {
            order.set(stepOrder(o.value()));
        });
        return order.get();
    }
}


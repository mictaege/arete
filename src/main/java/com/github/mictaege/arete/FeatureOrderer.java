package com.github.mictaege.arete;

import static java.util.Comparator.comparingInt;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;

public class FeatureOrderer implements MethodOrderer {

    @Override
    public void orderMethods(final MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparingInt(FeatureOrderer::getOrder));
    }

    private static int getOrder(final MethodDescriptor descriptor) {
        final AtomicReference<Integer> order = new AtomicReference<>(Order.DEFAULT);
        descriptor.findAnnotation(Feature.class).ifPresent(f -> {
            order.set(f.value());
        });
        descriptor.findAnnotation(Describe.class).ifPresent(d -> {
            order.set(d.value());
        });
        descriptor.findAnnotation(Scenario.class).ifPresent(s -> {
            order.set(s.value());
        });
        descriptor.findAnnotation(Examples.class).ifPresent(i -> {
            order.set(i.order());
        });
        descriptor.findAnnotation(Order.class).ifPresent(o -> {
            order.set(o.value());
        });
        return order.get();
    }

}


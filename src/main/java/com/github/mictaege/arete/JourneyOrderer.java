package com.github.mictaege.arete;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;

import java.util.concurrent.atomic.AtomicReference;

import static java.util.Comparator.comparingInt;

public class JourneyOrderer implements MethodOrderer {

    @Override
    public void orderMethods(final MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparingInt(JourneyOrderer::getOrder));
    }

    private static int getOrder(final MethodDescriptor descriptor) {
        final AtomicReference<Integer> order = new AtomicReference<>(Order.DEFAULT);
        descriptor.findAnnotation(Step.class).ifPresent(i -> {
            order.set(i.value());
        });
        descriptor.findAnnotation(Order.class).ifPresent(o -> {
            order.set(o.value());
        });
        return order.get();
    }

}


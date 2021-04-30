package com.github.mictaege.arete;

import static java.util.Comparator.comparingInt;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;

public class DescribeOrderer implements MethodOrderer {

    @Override
    public void orderMethods(final MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparingInt(DescribeOrderer::getOrder));
    }

    private static int getOrder(final MethodDescriptor descriptor) {
        final AtomicReference<Integer> order = new AtomicReference<>(Order.DEFAULT);
        descriptor.findAnnotation(ItShould.class).ifPresent(i -> {
            order.set(i.value());
        });
        descriptor.findAnnotation(Order.class).ifPresent(o -> {
            order.set(o.value());
        });
        return order.get();
    }

}


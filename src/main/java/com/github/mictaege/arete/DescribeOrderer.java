package com.github.mictaege.arete;

import static java.util.Comparator.comparingInt;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;

public class DescribeOrderer implements MethodOrderer {

    @Override
    public void orderMethods(MethodOrdererContext context) {
        context.getMethodDescriptors().sort(comparingInt(DescribeOrderer::getOrder));
    }

    private static int getOrder(MethodDescriptor descriptor) {
        final AtomicReference<Integer> order = new AtomicReference<>(Order.DEFAULT);
        descriptor.findAnnotation(ItShould.class).ifPresent(g -> {
            order.set(g.value());
        });
        descriptor.findAnnotation(Order.class).ifPresent(t -> {
            order.set(t.value());
        });
        return order.get();
    }

}


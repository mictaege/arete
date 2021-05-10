package com.github.mictaege.arete;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Order;
import org.mockito.Mock;

@Spec class DescribeOrdererSpec {

    DescribeOrderer orderer = new DescribeOrderer();

    @Scenario class OrderByDefault {
        @Mock MethodOrdererContext orderCtx;
        @Mock MethodDescriptor methodDesc0;
        @Mock MethodDescriptor methodDesc1;

        @BeforeAll void context() {
            openMocks(this);
            when(orderCtx.getMethodDescriptors()).then(i -> asList(methodDesc0, methodDesc1));
        }

        @Given void methodsWithoutAnyAnnotation() {
        }

        @When void methodsOrdered() {
            orderer.orderMethods(orderCtx);
        }

        @Then void itShouldUseTheDefaultOrdering() {
            verify(methodDesc0).findAnnotation(ItShould.class);
            verify(methodDesc0).findAnnotation(Order.class);
            verify(methodDesc1).findAnnotation(ItShould.class);
            verify(methodDesc1).findAnnotation(Order.class);
        }
    }

    @Scenario class OrderByItShouldPriority {
        @Mock MethodOrdererContext orderCtx;
        @Mock MethodDescriptor methodDesc0;
        @Mock ItShould itShould0;
        @Mock MethodDescriptor methodDesc1;
        @Mock ItShould itShould1;

        @BeforeAll void context() {
            openMocks(this);
            when(orderCtx.getMethodDescriptors()).then(i -> asList(methodDesc0, methodDesc1));
        }

        @Given void methodsWithItShouldAnnotation() {
            when(methodDesc0.findAnnotation(ItShould.class)).thenReturn(of(itShould0));
            when(methodDesc1.findAnnotation(ItShould.class)).thenReturn(of(itShould1));
        }

        @When void methodsOrdered() {
            orderer.orderMethods(orderCtx);
        }

        @Then void itShouldUseTheItShouldPriority() {
            verify(itShould0).value();
            verify(itShould1).value();
        }
    }

    @Scenario class Order_By_JUnit_Order {
        @Mock MethodOrdererContext orderCtx;
        @Mock MethodDescriptor methodDesc0;
        @Mock Order order0;
        @Mock MethodDescriptor methodDesc1;
        @Mock Order order1;

        @BeforeAll void context() {
            openMocks(this);
            when(orderCtx.getMethodDescriptors()).then(i -> asList(methodDesc0, methodDesc1));
        }

        @Given void methods_With_JUnit_Order_Annotation() {
            when(methodDesc0.findAnnotation(Order.class)).thenReturn(of(order0));
            when(methodDesc1.findAnnotation(Order.class)).thenReturn(of(order1));
        }

        @When void methods_Ordered() {
            orderer.orderMethods(orderCtx);
        }

        @Then void it_Should_Use_The_JUnit_Order_Priority() {
            verify(order0).value();
            verify(order1).value();
        }
    }

}
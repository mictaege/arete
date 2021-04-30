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

class ScenarioOrdererSpec {

    ScenarioOrderer orderer = new ScenarioOrderer();

    @Scenario class OrderByDefault {
        @Mock
        MethodOrdererContext orderCtx;
        @Mock
        MethodDescriptor methodDesc0;
        @Mock MethodDescriptor methodDesc1;

        @BeforeAll
        void context() {
            openMocks(this);
            when(orderCtx.getMethodDescriptors()).then(i -> asList(methodDesc0, methodDesc1));
        }

        @Given void methodsWithoutAnyAnnotation() {}

        @When void methodsOrdered() {
            orderer.orderMethods(orderCtx);
        }

        @Then void itShouldUseTheDefaultOrdering() {
            verify(methodDesc0).findAnnotation(Given.class);
            verify(methodDesc0).findAnnotation(When.class);
            verify(methodDesc0).findAnnotation(Then.class);
            verify(methodDesc0).findAnnotation(Order.class);
            verify(methodDesc1).findAnnotation(Given.class);
            verify(methodDesc1).findAnnotation(When.class);
            verify(methodDesc1).findAnnotation(Then.class);
            verify(methodDesc1).findAnnotation(Order.class);
        }
    }

    @Scenario class OrderByGivenPriorities {
        @Mock
        MethodOrdererContext orderCtx;
        @Mock
        MethodDescriptor methodDesc0;
        @Mock Given given0;
        @Mock MethodDescriptor methodDesc1;
        @Mock Given given1;

        @BeforeAll
        void context() {
            openMocks(this);
            when(orderCtx.getMethodDescriptors()).then(i -> asList(methodDesc0, methodDesc1));
        }

        @Given void methodsWithGivenAnnotation() {
            when(methodDesc0.findAnnotation(Given.class)).thenReturn(of(given0));
            when(methodDesc1.findAnnotation(Given.class)).thenReturn(of(given1));
        }

        @When void methodsOrdered() {
            orderer.orderMethods(orderCtx);
        }

        @Then void itShouldUseTheGivenPriorities() {
            verify(given0).seq();
            verify(given0).step();
            verify(given0).value();
            verify(given1).seq();
            verify(given1).step();
            verify(given1).value();
        }
    }

    @Scenario class OrderByWhenPriorities {
        @Mock
        MethodOrdererContext orderCtx;
        @Mock
        MethodDescriptor methodDesc0;
        @Mock When when0;
        @Mock MethodDescriptor methodDesc1;
        @Mock When when1;

        @BeforeAll
        void context() {
            openMocks(this);
            when(orderCtx.getMethodDescriptors()).then(i -> asList(methodDesc0, methodDesc1));
        }

        @Given void methodsWithWhenAnnotation() {
            when(methodDesc0.findAnnotation(When.class)).thenReturn(of(when0));
            when(methodDesc1.findAnnotation(When.class)).thenReturn(of(when1));
        }

        @When void methodsOrdered() {
            orderer.orderMethods(orderCtx);
        }

        @Then void itShouldUseTheWhenPriorities() {
            verify(when0).seq();
            verify(when0).step();
            verify(when0).value();
            verify(when1).seq();
            verify(when1).step();
            verify(when1).value();
        }
    }

    @Scenario class OrderByThenPriorities {
        @Mock
        MethodOrdererContext orderCtx;
        @Mock
        MethodDescriptor methodDesc0;
        @Mock Then then0;
        @Mock MethodDescriptor methodDesc1;
        @Mock Then then1;

        @BeforeAll
        void context() {
            openMocks(this);
            when(orderCtx.getMethodDescriptors()).then(i -> asList(methodDesc0, methodDesc1));
        }

        @Given void methodsWithThenAnnotation() {
            when(methodDesc0.findAnnotation(Then.class)).thenReturn(of(then0));
            when(methodDesc1.findAnnotation(Then.class)).thenReturn(of(then1));
        }

        @When void methodsOrdered() {
            orderer.orderMethods(orderCtx);
        }

        @Then void itShouldUseTheThenPriorities() {
            verify(then0).seq();
            verify(then0).step();
            verify(then0).value();
            verify(then1).seq();
            verify(then1).step();
            verify(then1).value();
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
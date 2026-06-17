package com.github.mictaege.arete;

import static com.github.mictaege.arete.AreteOrder.scenarioOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@Spec class SeqAndStepPrioritySpec {

    @Feature class OrderWithinTheSameLevel {

        @Scenario class ShouldOrderTwoStepsWithDifferentStepOrder {
            AreteOrder stepOne, stepTwo;
            @Given(1) void aStepWithStepOrderOne() {
                stepOne = scenarioOrder(1, 1, 1);
            }
            @Given(2) void aStepWithStepOrderTwo() {
                stepTwo = scenarioOrder(1, 1, 2);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldOrderTwoStepsWithDifferentSemantic {
            AreteOrder stepOne, stepTwo;
            @Given(1) void aStepWithSemanticOne() {
                stepOne = scenarioOrder(1, 1, 1);
            }
            @Given(2) void aStepWithSemanticTwo() {
                stepTwo = scenarioOrder(1, 2, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldOrderTwoStepsWithDifferentSequence {
            AreteOrder stepOne, stepTwo;
            @Given(1) void aStepWithSequenceOne() {
                stepOne = scenarioOrder(1, 1, 1);
            }
            @Given(2) void aStepWithSequenceTwo() {
                stepTwo = scenarioOrder(2, 1, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }
    }

    @Feature class OrderEqualPriorities {

        @Scenario class ShouldOrderTwoStepsWithEqualPriorities {
            AreteOrder stepOne, stepTwo;
            @Given(1) void aStepWithSomePriorities() {
                stepOne = scenarioOrder(3, 4, 2);
            }
            @Given(2) void anotherStepWithTheSamePriorities() {
                stepTwo = scenarioOrder(3, 4, 2);
            }
            @Then void bothStepsShouldBeEqual(){
                assertThat(stepOne, comparesEqualTo(stepTwo));
                assertThat(stepTwo, comparesEqualTo(stepOne));
            }
        }
    }

    @Feature class OrderAcrossDifferentLevels {

        @Scenario class ShouldFavourSemanticOverStepOrder {
            AreteOrder stepOne, stepTwo;
            @Given(1) void aStepWithSemanticOneAndStepOrderTwo() {
                stepOne = scenarioOrder(1, 1, 2);
            }
            @Given(2) void aStepWithSemanticTwoAndStepOrderOne() {
                stepTwo = scenarioOrder(1, 2, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldFavourSequenceOverSemantic {
            AreteOrder stepOne, stepTwo;
            @Given(1) void aStepWithSequenceOneAndSemanticTwo() {
                stepOne = scenarioOrder(1, 2, 1);
            }
            @Given(2) void aStepWithSequenceTwoAndSemanticOne() {
                stepTwo = scenarioOrder(2, 1, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldFavourSequenceOverStepOrder {
            AreteOrder stepOne, stepTwo;
            @Given(1) void aStepWithSequenceOneAndStepOrderTwo() {
                stepOne = scenarioOrder(1, 1, 2);
            }
            @Given(2) void aStepWithSequenceTwoAndStepOrderOne() {
                stepTwo = scenarioOrder(2, 1, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }
    }
}
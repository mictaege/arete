package com.github.mictaege.arete;

import static com.github.mictaege.arete.SeqAndStepPriority.priority;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

class SeqAndStepPrioritySpec {

    @Feature class OrderWithinTheSameLevel {

        @Scenario class ShouldOrderTwoStepsWithDifferentStepOrder {
            SeqAndStepPriority stepOne, stepTwo;
            @Given(1) void aStepWithStepOrderOne() {
                stepOne = priority(1, 1, 1);
            }
            @Given(2) void aStepWithStepOrderTwo() {
                stepTwo = priority(1, 1, 2);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldOrderTwoStepsWithDifferentSemantic {
            SeqAndStepPriority stepOne, stepTwo;
            @Given(1) void aStepWithSemanticOne() {
                stepOne = priority(1, 1, 1);
            }
            @Given(2) void aStepWithSemanticTwo() {
                stepTwo = priority(1, 2, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldOrderTwoStepsWithDifferentSequence {
            SeqAndStepPriority stepOne, stepTwo;
            @Given(1) void aStepWithSequenceOne() {
                stepOne = priority(1, 1, 1);
            }
            @Given(2) void aStepWithSequenceTwo() {
                stepTwo = priority(2, 1, 1);
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
            SeqAndStepPriority stepOne, stepTwo;
            @Given(1) void aStepWithSomePriorities() {
                stepOne = priority(3, 4, 2);
            }
            @Given(2) void anotherStepWithTheSamePriorities() {
                stepTwo = priority(3, 4, 2);
            }
            @Then void bothStepsShouldBeEqual(){
                assertThat(stepOne, comparesEqualTo(stepTwo));
                assertThat(stepTwo, comparesEqualTo(stepOne));
            }
        }
    }

    @Feature class OrderAcrossDifferentLevels {

        @Scenario class ShouldFavourSemanticOverStepOrder {
            SeqAndStepPriority stepOne, stepTwo;
            @Given(1) void aStepWithSemanticOneAndStepOrderTwo() {
                stepOne = priority(1, 1, 2);
            }
            @Given(2) void aStepWithSemanticTwoAndStepOrderOne() {
                stepTwo = priority(1, 2, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldFavourSequenceOverSemantic {
            SeqAndStepPriority stepOne, stepTwo;
            @Given(1) void aStepWithSequenceOneAndSemanticTwo() {
                stepOne = priority(1, 2, 1);
            }
            @Given(2) void aStepWithSequenceTwoAndSemanticOne() {
                stepTwo = priority(2, 1, 1);
            }
            @Then(1) void theFirstStepShouldBeLess(){
                assertThat(stepOne, lessThan(stepTwo));
            }
            @Then(2) void theSecondStepShouldBeGreater(){
                assertThat(stepTwo, greaterThan(stepOne));
            }
        }

        @Scenario class ShouldFavourSequenceOverStepOrder {
            SeqAndStepPriority stepOne, stepTwo;
            @Given(1) void aStepWithSequenceOneAndStepOrderTwo() {
                stepOne = priority(1, 1, 2);
            }
            @Given(2) void aStepWithSequenceTwoAndStepOrderOne() {
                stepTwo = priority(2, 1, 1);
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
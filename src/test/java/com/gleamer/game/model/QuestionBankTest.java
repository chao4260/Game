package com.gleamer.game.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionBankTest {
    @Test
    public void initializeQuestionBank() {
        // Given & When
        QuestionBank questionBank = new QuestionBank(2);

        // Then
        assertEquals(questionBank.getQuestions(), Map.of(
                Category.POP, new LinkedList<>(List.of("Pop Question 0", "Pop Question 1")),
                Category.SCIENCE, new LinkedList<>(List.of("Science Question 0", "Science Question 1")),
                Category.SPORTS, new LinkedList<>(List.of("Sports Question 0", "Sports Question 1")),
                Category.ROCK, new LinkedList<>(List.of("Rock Question 0", "Rock Question 1"))
        ));
    }

    @Test
    public void getQuestionByCategory() {
        // Given
        QuestionBank questionBank = new QuestionBank(2);

        // When
        String question = questionBank.getQuestionByCategory(Category.ROCK);

        // Then
        assertEquals(question, "Rock Question 1");
    }
}

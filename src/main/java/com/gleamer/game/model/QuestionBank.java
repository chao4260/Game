package com.gleamer.game.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class QuestionBank {
    private final Map<Category, LinkedList<String>> questions;

    public QuestionBank(int numberOfQuestionsPerCategory) {
        this.questions = new HashMap<>(generateQuestions(numberOfQuestionsPerCategory));
    }

    public String getQuestionByCategory(Category category) {
        return questions.get(category).removeLast();
    }

    private static Map<Category, LinkedList<String>> generateQuestions(int numberOfQuestionsPerCategory) {
        LinkedList<String> popQuestions = new LinkedList<>();
        LinkedList<String> scienceQuestions = new LinkedList<>();
        LinkedList<String> sportsQuestions = new LinkedList<>();
        LinkedList<String> rockQuestions = new LinkedList<>();

        for (int i = 0; i < numberOfQuestionsPerCategory; i++) {
            popQuestions.addLast(createQuestion(Category.POP, i));
            scienceQuestions.addLast(createQuestion(Category.SCIENCE, i));
            sportsQuestions.addLast(createQuestion(Category.SPORTS, i));
            rockQuestions.addLast(createQuestion(Category.ROCK, i));
        }

        return Map.of(
                Category.POP, popQuestions,
                Category.SCIENCE, scienceQuestions,
                Category.SPORTS, sportsQuestions,
                Category.ROCK, rockQuestions
        );
    }

    private static String createQuestion(Category category, int index) {
        return category.getCategoryName() + " Question " + index;
    }

    Map<Category, LinkedList<String>> getQuestions() {
        return questions;
    }
}

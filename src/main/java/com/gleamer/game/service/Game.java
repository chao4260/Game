package com.gleamer.game.service;

public interface Game {

    boolean isPlayable();

    boolean addPlayer(String playerName);

    void roll(int roll);

    boolean processAnswerAndCheckWinner(boolean correctAnswer);

    boolean isQuestionWasAsked();

}

package com.gleamer.game.model;

public record GameConfiguration(int numberOfQuestionPerCategory,
                                int maxPlayers,
                                int minPlayers,
                                int mapSize,
                                int goldForWin) {
}
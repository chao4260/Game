package com.gleamer.game;

import com.gleamer.game.model.GameConfiguration;

import java.util.Scanner;

public class GameApplication {
    private static final int NUMBER_OF_QUESTION_PER_CATEGORY = 50;
    private static final int MAX_PLAYERS = 6;
    private static final int MIN_PLAYERS = 2;
    private static final int MAP_SIZE = 12;
    private static final int GOLD_FOR_WIN = 6;

    public static void main(String[] args) {
        GameConfiguration configuration = new GameConfiguration(
                NUMBER_OF_QUESTION_PER_CATEGORY,
                MAX_PLAYERS,
                MIN_PLAYERS,
                MAP_SIZE,
                GOLD_FOR_WIN);
        Game game = new Game(configuration);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Game start");
        System.out.println("Add players");

        boolean continueToAddPlayer = true;
        while (continueToAddPlayer) {
            System.out.println("Enter the player name: ");
            game.addPlayer(scanner.next());

            if (game.isPlayable()) {
                System.out.println("Game is playable now");
                System.out.println("Continue to add player? y/N");
                continueToAddPlayer = "y".equals(scanner.next());
            }
        }

        if (game.isPlayable()) {
            boolean gameOver = false;
            while (!gameOver) {
                System.out.println("Roll the dice: ");
                game.roll(scanner.nextInt());

                if (game.isQuestionWasAsked()) {
                    System.out.println("Did the player answer the question correctly? y/N");
                    boolean correctAnswer = "y".equals(scanner.next());
                    gameOver = game.processAnswerAndCheckWinner(correctAnswer);
                }
            }
        }
    }
}

package com.gleamer.game;

import com.gleamer.game.model.Category;
import com.gleamer.game.model.GameConfiguration;
import com.gleamer.game.model.Player;
import com.gleamer.game.model.QuestionBank;

import java.util.ArrayList;

public class Game {
    private final GameConfiguration configuration;
    private final ArrayList<Player> players;
    private final QuestionBank questionBank;

    private int currentPlayerIndex;
    private boolean questionWasAsked;

    public Game(GameConfiguration configuration) {
        this.configuration = configuration;
        this.players = new ArrayList<>();
        this.questionBank = new QuestionBank(configuration.numberOfQuestionPerCategory());
    }

    public boolean isPlayable() {
        return players.size() >= configuration.minPlayers();
    }

    public boolean addPlayer(String playerName) {
        if (players.size() >= configuration.maxPlayers()) {
            System.out.println("The maximum number of players is reached!");
            return false;
        }

        if (players.stream().anyMatch(player -> player.getName().equals(playerName))) {
            System.out.println(playerName + " is already registered");
            return false;
        }

        players.add(new Player(playerName));
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                currentPlayer.setInPenaltyBox(false);
                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                moveToNewPlace(currentPlayer, roll);
            } else {
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
                nextPlayer();
            }
        } else {
            moveToNewPlace(currentPlayer, roll);
        }
    }

    public boolean processAnswerAndCheckWinner(boolean correctAnswer) {
        boolean win = false;

        if (correctAnswer) {
            increasePurse();
            win = didPlayerWin();
        } else {
            goToPenaltyBox();
        }

        nextPlayer();
        return win;
    }

    private boolean didPlayerWin() {
        Player currentPlayer = players.get(currentPlayerIndex);
        boolean win = currentPlayer.getPurse() == configuration.goldForWin();
        if (win) {
            System.out.println("Congratulations to " + currentPlayer.getName() + " for winning the game!");
        }
        return win;
    }

    public boolean isQuestionWasAsked() {
        return questionWasAsked;
    }

    private void moveToNewPlace(Player player, int roll) {
        int newPlace = (player.getPlace() + roll) % configuration.mapSize();
        player.setPlace(newPlace);
        System.out.println(player.getName() + "'s new location is " + player.getPlace());

        Category category = currentCategory(newPlace);
        System.out.println("The category is " + category);
        askQuestion(category);
    }

    private void askQuestion(Category category) {
        questionWasAsked = true;
        System.out.println(questionBank.getQuestionByCategory(category));
    }

    private void increasePurse() {
        System.out.println("Answer was correct!!!!");

        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.setPurse(currentPlayer.getPurse() + 1);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getPurse() + " Gold Coin(s).");
    }

    private void goToPenaltyBox() {
        System.out.println("Question was incorrectly answered");

        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.setInPenaltyBox(true);
        System.out.println(currentPlayer.getName() + " was sent to the penalty box");
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        questionWasAsked = false;
    }

    Category currentCategory(int place) {
        return switch (place % 4) {
            case 0 -> Category.POP;
            case 1 -> Category.SCIENCE;
            case 2 -> Category.SPORTS;
            case 3 -> Category.ROCK;
            default -> throw new IllegalArgumentException("Unexpected place: " + place);
        };
    }

    ArrayList<Player> getPlayers() {
        return players;
    }

    int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}

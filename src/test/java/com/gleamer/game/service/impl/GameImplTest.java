package com.gleamer.game.service.impl;

import com.gleamer.game.model.Category;
import com.gleamer.game.model.GameConfiguration;
import com.gleamer.game.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameImplTest {
    private static final int NUMBER_OF_QUESTION_PER_CATEGORY = 2;
    private static final int MAX_PLAYERS = 3;
    private static final int MIN_PLAYERS = 2;
    private static final int MAP_SIZE = 12;
    private static final int GOLD_FOR_WIN = 6;

    private static final String PLAYER_1 = "P1";
    private static final String PENALTY_PLAYER_1 = "PP1";

    private GameImpl game;

    @BeforeEach
    public void setup() {
        game = new GameImpl(new GameConfiguration(
                NUMBER_OF_QUESTION_PER_CATEGORY,
                MAX_PLAYERS,
                MIN_PLAYERS,
                MAP_SIZE,
                GOLD_FOR_WIN
        ));
    }

    @Test
    public void isPlayable() {
        // Given
        game.addPlayer("P1");
        game.addPlayer("P2");

        // When
        boolean isPlayable = game.isPlayable();

        // Then
        assertTrue(isPlayable);
    }

    @Test
    public void isPlayable_false() {
        assertFalse(game.isPlayable());
    }

    @Test
    public void addPlayer() {
        assertTrue(game.addPlayer("P1"));
    }

    @Test
    public void addPlayer_maxPlayerReached() {
        assertTrue(game.addPlayer("P1"));
        assertTrue(game.addPlayer("P2"));
        assertTrue(game.addPlayer("P3"));
        assertFalse(game.addPlayer("P4"));
    }

    @Test
    public void addPlayer_samePlayer() {
        assertTrue(game.addPlayer("P1"));
        assertFalse(game.addPlayer("P1"));
    }

    @Test
    public void roll() {
        // Given
        addDefaultPlayer();
        Player expectedPlayer = new Player(PLAYER_1);
        expectedPlayer.setPlace(2);

        // When
        game.roll(2);

        // Then
        assertEquals(game.getPlayers(), List.of(expectedPlayer));
        assertTrue(game.isQuestionWasAsked());
    }

    @Test
    public void roll_penaltyPlayer_even() {
        // Given
        addPenaltyPlayer();
        Player expectedPlayer = new Player(PENALTY_PLAYER_1);
        expectedPlayer.setPlace(3);

        // When
        game.roll(3);

        // Then
        assertEquals(game.getPlayers(), List.of(expectedPlayer));
        assertTrue(game.isQuestionWasAsked());
    }

    @Test
    public void roll_penaltyPlayer_odd() {
        // Given
        addPenaltyPlayer();
        addDefaultPlayer();
        Player expectedPlayer = new Player(PENALTY_PLAYER_1);
        expectedPlayer.setInPenaltyBox(true);

        // When
        game.roll(4);

        // Then
        assertEquals(game.getPlayers(), List.of(expectedPlayer, new Player(PLAYER_1)));
        assertEquals(game.getCurrentPlayerIndex(), 1);
        assertFalse(game.isQuestionWasAsked());
    }

    @Test
    public void processAnswerAndCheckWinner_rightAnswer() {
        // Given
        addDefaultPlayer();
        game.addPlayer("P2");
        game.getPlayers().get(0).setPurse(5);
        Player expectedPlayer = new Player(PLAYER_1);
        expectedPlayer.setPurse(6);

        // When
        boolean result = game.processAnswerAndCheckWinner(true);

        // Then
        assertTrue(result);
        assertEquals(game.getPlayers(), List.of(expectedPlayer, new Player("P2")));
        assertEquals(game.getCurrentPlayerIndex(), 1);
        assertFalse(game.isQuestionWasAsked());
    }

    @Test
    public void processAnswerAndCheckWinner_wrongAnswer() {
        // Given
        addDefaultPlayer();
        game.addPlayer("P2");
        Player expectedPlayer = new Player(PLAYER_1);
        expectedPlayer.setInPenaltyBox(true);

        // When
        game.processAnswerAndCheckWinner(false);

        // Then
        assertEquals(game.getPlayers(), List.of(expectedPlayer, new Player("P2")));
        assertEquals(game.getCurrentPlayerIndex(), 1);
        assertFalse(game.isQuestionWasAsked());
    }

    @ParameterizedTest()
    @CsvSource({
            "1, SCIENCE",
            "4, POP",
            "7, ROCK",
            "6, SPORTS",
    })
    public void currentCategory(int place, Category category) {
        assertEquals(game.currentCategory(place), category);
    }

    @Test
    public void currentCategory_IAE() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> game.currentCategory(-1));
        assertEquals("Unexpected place: -1", exception.getMessage());
    }

    private void addDefaultPlayer() {
        game.addPlayer(PLAYER_1);
    }

    private void addPenaltyPlayer() {
        game.addPlayer(PENALTY_PLAYER_1);
        game.processAnswerAndCheckWinner(false);
    }
}

package com.gleamer.game.model;

import java.util.Objects;

public class Player {
    private final String name;
    private int place;
    private int purse;
    private boolean isInPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPurse() {
        return purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        isInPenaltyBox = inPenaltyBox;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return place == player.place && purse == player.purse && isInPenaltyBox == player.isInPenaltyBox && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, place, purse, isInPenaltyBox);
    }
}

package com.pollyfat.squarega.entity;

/**
 * Created by polly on 2016/5/30.
 *
 */
public class RecordItem {
    private Player playerOne;
    private Player playerTwo;
    private String time;
    private String date;
    private int level;
    private int winnerScore;
    private int loserScore;
    boolean isFirstWin;

    public boolean isFirstWin() {
        return isFirstWin;
    }

    public void setFirstWin(boolean firstWin) {
        isFirstWin = firstWin;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void setLoserScore(int loserScore) {
        this.loserScore = loserScore;
    }
}

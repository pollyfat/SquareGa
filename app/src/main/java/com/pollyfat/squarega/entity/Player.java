package com.pollyfat.squarega.entity;

/**
 * 玩家对象
 * Created by bugre on 2016/4/26.
 */
public class Player {
    private String name;
    private int winCount;//获胜局数
    private int winSquare;//获得的方块数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getWinSquare() {
        return winSquare;
    }

    public void setWinSquare(int winSquare) {
        this.winSquare = winSquare;
    }
}

package com.pollyfat.squarega.entity;

/**
 * 玩家对象
 * Created by bugre on 2016/4/26.
 */
public class Player {
    private String name;
    private int winCount;//获胜局数
    private int winSquare;//获得的方块数
    private String avatar;

    public Player(String avatar) {
        this.avatar = avatar;
    }

    public Player(String name, String avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

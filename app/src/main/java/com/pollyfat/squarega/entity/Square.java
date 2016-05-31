package com.pollyfat.squarega.entity;

/**
 * Created by polly on 2016/5/8.
 * 方块对象
 */
public class Square {

    private Player owner;
    private int mX,mY;
    private boolean top, left, bottom, right;
    private float coordX;
    private float coordY;
    private boolean coordSet;

    public void resetLine(){
        this.top = false;
        this.left = false;
        this.bottom = false;
        this.right = false;
    }

    public boolean isCoordSet() {
        return coordSet;
    }

    public void setCoordSet(boolean coordSet) {
        this.coordSet = coordSet;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public int getmX() {
        return mX;
    }

    public void setmX(int mX) {
        this.mX = mX;
    }

    public int getmY() {
        return mY;
    }
    public void setmY(int mY) {
        this.mY = mY;
    }
    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}

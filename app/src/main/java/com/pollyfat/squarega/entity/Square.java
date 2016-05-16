package com.pollyfat.squarega.entity;

import com.pollyfat.squarega.view.DotView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bugre on 2016/5/8.
 * 方块对象
 */
public class Square {

    private Player owner;
    private int mX,mY;

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
        private boolean top, left, bottom, right;
//    private DotView topLeft, topRight, botLeft, botRight;
//    private static List<DotView> dots = new ArrayList<>();
//
//    public List<DotView> getDots() {
//        return dots;
//    }
//
//    public void setDots(List<DotView> dots) {
//        this.dots = dots;
//    }
//
//
//    public DotView getTopLeft() {
//        return topLeft;
//    }
//
//    public void setTopLeft(DotView topLeft) {
//        this.topLeft = topLeft;
//    }
//
//    public DotView getTopRight() {
//        return topRight;
//    }
//
//    public void setTopRight(DotView topRight) {
//        this.topRight = topRight;
//    }
//
//    public DotView getBotLeft() {
//        return botLeft;
//    }
//
//    public void setBotLeft(DotView botLeft) {
//        this.botLeft = botLeft;
//    }
//
//    public DotView getBotRight() {
//        return botRight;
//    }
//
//    public void setBotRight(DotView botRight) {
//        this.botRight = botRight;
//    }
//
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

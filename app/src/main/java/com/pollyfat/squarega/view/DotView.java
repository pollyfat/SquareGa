package com.pollyfat.squarega.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by polly on 2016/4/5.
 *
 * 圆点
 */
public class DotView extends ImageView {

    //判断跟上下左右的点是否已连接
    boolean right = false, left = false, down = false, up = false;
    Square one, two, three, four;
    int mX;
    int mY;
    boolean mClickable = false;
    float coordX, coordY;

    public void resetConn(){
        this.right = false;
        this.left = false;
        this.down = false;
        this.up = false;
    }

    public List<Square> getSqares(){
        List<Square> squares = new ArrayList<>();
        squares.add(getOne());
        squares.add(getTwo());
        squares.add(getThree());
        squares.add(getFour());
        return squares;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

    public Square getOne() {
        return one;
    }

    public void setOne(Square one) {
        this.one = one;
    }

    public Square getTwo() {
        return two;
    }

    public void setTwo(Square two) {
        this.two = two;
    }

    public Square getThree() {
        return three;
    }

    public void setThree(Square three) {
        this.three = three;
    }

    public Square getFour() {
        return four;
    }

    public void setFour(Square four) {
        this.four = four;
    }

    public DotView(Context context) {
        super(context);
    }

    public boolean ismClickable() {
        return mClickable;
    }

    public void setmClickable(boolean mClickable) {
        this.mClickable = mClickable;
    }


    public int getmY() {
        return mY;
    }

    public void setmY(int mY) {
        this.mY = mY;
    }

    public int getmX() {
        return mX;
    }

    public void setmX(int mX) {
        this.mX = mX;
    }


}

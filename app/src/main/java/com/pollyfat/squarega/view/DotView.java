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
    boolean right=false,left=false,down=false,up=false;
    Square one,two,three,four;
    int mX;
    int mY;
    boolean mClickable = false;
    boolean mSelected = false;
    float coordX,coordY;
    static List<DotView> dots = new ArrayList<>();

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
        dots.add(this);
    }

    public boolean ismSelected() {
        return mSelected;
    }

    public void setmSelected(boolean mSelected) {
        this.mSelected = mSelected;
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

    /**
     * 找到上下左右的点，并设置为可点击
     */
    public List<DotView> findConnDots() {
        List<DotView> connDots = new ArrayList();
        int dotCount = dots.size();
        //设置上下左右可点击
        for (int i = 0; i < dotCount; i++) {
            int row, column;
            DotView d =  dots.get(i);
            row = d.getmX();
            column = d.getmY();
            if (row == this.getmX()) {
                if (column == this.getmY() + 1 || column == this.getmY() - 1) {
                    connDots.add(d);
                }
            }
            if (column == this.getmY()) {
                if (row == this.getmX() + 1 || row == this.getmX() - 1) {
                    connDots.add(d);
                }
            }
        }
        return connDots;
    }

    public Map<Integer,Square> getSquares(){
        Map<Integer,Square> squares = new HashMap<>();
        squares.put(1,getOne());
        squares.put(2,getTwo());
        squares.put(3,getThree());
        squares.put(4,getFour());
        return squares;
    }
}

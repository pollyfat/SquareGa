package com.pollyfat.squarega.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

/**
 * Created by bugre on 2016/4/5.
 */
public class DotView extends ImageView {

    Paint mPaint;
    Canvas mCanvas;
    int mX;
    int mY;
    boolean clickable=false;
    boolean selected=false;

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isClickable() {
        return clickable;
    }

    @Override
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
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

    public DotView(Context context) {
        super(context);
        mPaint = new Paint();
        mCanvas = new Canvas();
        mPaint.setColor(0xff333);
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int mPosX = (int) event.getX();
        int mPosY = (int) event.getY();
        int[] location = new int[2];
        switch (action) {
            // 触摸按下的事件
            case MotionEvent.ACTION_DOWN:
                this.getLocationInWindow(location);
                break;
            // 触摸移动的事件
            case MotionEvent.ACTION_MOVE:
                mCanvas.drawLine(location[0], location[1], mPosX, mPosY, mPaint);
                invalidate();
                break;
            // 触摸抬起的事件
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }*/
}

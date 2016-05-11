package com.pollyfat.squarega.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pollyfat.squarega.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bugre on 2016/4/5.
 */
public class DotView extends ImageView {

    int mX;
    int mY;
    boolean mClickable = false;
    boolean mSelected = false;
    static List<DotView> dots = new ArrayList<>();

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
    private void findConnDots() {
        int dotCount = dots.size();
        //设置上下左右可点击
        for (int i = 0; i < dotCount; i++) {
            int row, column;
            DotView d = (DotView) dots.get(i);
            row = d.getmX();
            column = d.getmY();
            if (row == this.getmX()) {
                if (column == this.getmY() + 1 || column == this.getmY() - 1) {
                    d.setClickable(true);
                    d.setImageResource(R.drawable.dot);
                }
            }
            if (column == this.getmY()) {
                if (row == this.getmX() + 1 || row == this.getmX() - 1) {
                    d.setClickable(true);
                    d.setImageResource(R.drawable.dot);
                }
            }
        }
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            // 触摸按下的事件
//            case MotionEvent.ACTION_DOWN:
//
//                break;
//            // 触摸移动的事件
//            case MotionEvent.ACTION_MOVE:
//                invalidate();
//                break;
//            // 触摸抬起的事件
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return true;
//    }
}

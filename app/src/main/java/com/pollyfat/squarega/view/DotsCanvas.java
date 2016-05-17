package com.pollyfat.squarega.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/5/17.
 */
public class DotsCanvas extends LinearLayout {

    static List<Point> points = new ArrayList<>();

    public DotsCanvas(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public DotsCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public DotsCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        for (Point p :
                points) {
            canvas.drawLine(p.getStartX(),p.getStartY(),p.getStopX(),p.getStopY(),paint);
        }
    }

    public void addPoint(Point point){
        points.add(point);
    }

}
class Point{
    float startX,startY,stopX,stopY;

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getStopX() {
        return stopX;
    }

    public float getStopY() {
        return stopY;
    }

    public Point(float startX, float startY, float stopX, float stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }
}
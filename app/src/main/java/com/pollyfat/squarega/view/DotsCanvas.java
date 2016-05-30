package com.pollyfat.squarega.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.activity.StartActivity;
import com.pollyfat.squarega.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by polly on 2016/5/17.
 *
 */
public class DotsCanvas extends LinearLayout {

    static List<PointPair> pointPairs = new ArrayList<>();
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
        drawLine(canvas);
        drawFlag(canvas);
    }

    public void addPointPair(float startX, float startY, float stopX, float stopY) {
        pointPairs.add(new PointPair(startX, startY, stopX, stopY));
    }

    public void addPoint(float coordX, float coordY,Player player) {
        points.add(new Point(coordX,coordY,player));
    }
    public void drawFlag(Canvas canvas) {
        Resources res = getResources();
        Bitmap bitmap;
        for (Point p :
                points) {
//            if (p.player.equals(StartActivity.player1)) {
                bitmap = BitmapFactory.decodeResource(res, R.drawable.flag);
//            } else{
//                bitmap = BitmapFactory.decodeResource(res, R.drawable.two);
//            }
            canvas.drawBitmap(bitmap,p.coordX + bitmap.getWidth() / 2, p.coordY + bitmap.getHeight() / 2,new Paint());
        }
    }

    public void drawLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        for (PointPair p :
                pointPairs) {
            canvas.drawLine(p.getStartX(), p.getStartY(), p.getStopX(), p.getStopY(), paint);
        }
    }

    public class PointPair {
        float startX, startY, stopX, stopY;

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

        public PointPair(float startX, float startY, float stopX, float stopY) {
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
        }
    }

    public class Point{
        float coordX, coordY;
        Player player;

        public Point(float coordX, float coordY,Player player) {
            this.coordX = coordX;
            this.coordY = coordY;
            this.player = player;
        }
    }
}

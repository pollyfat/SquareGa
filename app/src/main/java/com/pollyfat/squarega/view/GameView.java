package com.pollyfat.squarega.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.pollyfat.squarega.R;

/**
 * Created by bugre on 2016/3/19.
 */
public class GameView extends View {
    Bitmap bitmap;
    Paint paint;
    Matrix m1;
    public GameView(Context context) {
        super(context);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.item);
        paint = new Paint();
        m1 = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasW = canvas.getWidth();
        int canvasH = canvas.getHeight();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(0xff87CDBF);
        canvas.drawRect(0, 0, canvasW, canvasH * 0.15f, paint);
        canvas.drawRect(0, canvasH * 0.85f, canvasW, canvasH, paint);
        canvas.save();
        m1.setTranslate(500, 0);
        canvas.drawBitmap(bitmap, m1, paint);
        canvas.restore();
        canvas.save();
    }
}

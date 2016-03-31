package com.pollyfat.squarega.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;

import com.pollyfat.squarega.R;

/**
 * Created by bugre on 2016/3/12.
 */
public class MenuView extends View {

    int itemX = 0, itemY = 0;
    public MenuView(Context context) {
        super(context);
        setFocusable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.item), itemX, itemY, new Paint());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                itemY -= 10;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                itemY += 10;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                itemX -= 10;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                itemX += 10;
                break;
        }
        postInvalidate(); //通知系统重绘View

        return super.onKeyDown(keyCode, event);
    }
}

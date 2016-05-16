package com.pollyfat.squarega.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.Square;
import com.pollyfat.squarega.listener.DotModel;
import com.pollyfat.squarega.view.DotView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bugre on 2016/3/18.
 */
@EActivity
public class StartActivity extends Activity implements DotModel.DrawLineCallback{

    @Extra
    String rival;//对手
    @Extra
    int level;//难度

    Player player1,player2;
    Canvas mCanvas;
    LinearLayout root;
    static List<DotView> dots=new ArrayList<>();
    static List<Square> squares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        root = (LinearLayout) findViewById(R.id.dots);
        mCanvas = new Canvas();
        initGameView();
        initSquares();
        connDotAndSquare();
    }

    private void initSquares() {
        for (int i=0;i<level-1;i++) {
            for (int j=0;j<level-1;j++) {
                Square square = new Square();
                square.setmX(i);
                square.setmY(j);
                squares.add(square);
            }
        }
    }

    /**
     * 初始化游戏界面的圆点
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void initGameView() {
        for (int i = 0; i < level; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < level; j++) {
                LinearLayout.LayoutParams dotsParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                );
                dotsParams.gravity = Gravity.CENTER;
                DotView dot = new DotView(StartActivity.this);
                dot.setmX(i);
                dot.setmY(j);
                dot.setImageResource(R.drawable.dot_normal);
                dot.setOnClickListener(new DotModel(this));
                dots.add(dot);
                linearLayout.addView(dot, dotsParams);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            );
            params.gravity = Gravity.CENTER;
            root.addView(linearLayout, params);
        }
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        mCanvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_again:
                //重来
                break;
            case R.id.start_back:
                //back to choose level
                break;
        }
    }

    Map<Integer,Square> findSquare(int x, int y) {
        Map<Integer, Square> squareMap = new HashMap<>();
        for (Square s :
                squares) {
            if (s.getmX() == x) {
                if (s.getmY() == y) {
                    squareMap.put(2, s);
                } else if (s.getmY() == y - 1) {
                    squareMap.put(3, s);
                }
            } else if (s.getmX() == x - 1) {
                if (s.getmY() == y ) {
                    squareMap.put(1, s);
                } else if (s.getmY()== y - 1) {
                    squareMap.put(4, s);
                }
            }
        }
        return squareMap;
    }

    void connDotAndSquare(){
        for (DotView d :
                dots) {
            Map<Integer,Square> dSquare = findSquare(d.getmX(), d.getmY());
            d.setOne(dSquare.get(1));
            d.setTwo(dSquare.get(2));
            d.setThree(dSquare.get(3));
            d.setFour(dSquare.get(4));
        }
    }
}

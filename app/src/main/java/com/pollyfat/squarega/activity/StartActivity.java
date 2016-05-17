package com.pollyfat.squarega.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.Square;
import com.pollyfat.squarega.listener.DotModel;
import com.pollyfat.squarega.view.DotView;
import com.pollyfat.squarega.view.DotsCanvas;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by polly on 2016/3/18.
 *
 * 游戏界面
 *
 */
@EActivity
public class StartActivity extends Activity {

    public static int SURPLUS;//坐标偏差值

    @Extra
    String rival;//对手（电脑/人）
    @Extra
    int level;//难度

    Player player1, player2;
    DotsCanvas root;
    static List<DotView> dots = new ArrayList<>();
    static List<Square> squares = new ArrayList<>();
    DotModel dotModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        root = (DotsCanvas) findViewById(R.id.dots);
        dotModel = new DotModel(player1, player2, root);
        initGameView();
        initSquares();
        connDotAndSquare();
        setSurplus();
    }

    /**
    * onDraw时获取的坐标包含状态栏和顶部Layout，获取这部分偏差值以矫正坐标
    */
    private void setSurplus() {
        final RelativeLayout surplus = (RelativeLayout) findViewById(R.id.start_surplus);
        ViewTreeObserver vto = surplus.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    int result = getResources().getDimensionPixelSize(resourceId);
                    SURPLUS = surplus.getHeight()+result;
                }
                ViewTreeObserver obs = surplus.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }

        });

    }

    /**
     * 初始化方块
     */
    private void initSquares() {
        for (int i = 0; i < level - 1; i++) {
            for (int j = 0; j < level - 1; j++) {
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
                dot.setOnClickListener(dotModel);
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


    void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_again:
                //重来
                break;
            case R.id.start_back:
                finish();
                break;
        }
    }

    /**
     * 找出每个点对应的方块
     * @param x 点的x
     * @param y 点的y
     * @return 点的方块
     */
    Map<Integer, Square> findSquare(int x, int y) {
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
                if (s.getmY() == y) {
                    squareMap.put(1, s);
                } else if (s.getmY() == y - 1) {
                    squareMap.put(4, s);
                }
            }
        }
        return squareMap;
    }

    /**
     * 将点和对应象限的方块连接
     */
    void connDotAndSquare() {
        for (DotView d :
                dots) {
            Map<Integer, Square> dSquare = findSquare(d.getmX(), d.getmY());
            d.setOne(dSquare.get(1));
            d.setTwo(dSquare.get(2));
            d.setThree(dSquare.get(3));
            d.setFour(dSquare.get(4));
        }
    }
}

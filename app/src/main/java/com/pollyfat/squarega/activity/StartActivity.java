package com.pollyfat.squarega.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
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
import com.pollyfat.squarega.model.DotModel;
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
 * <p/>
 * 游戏界面
 */
@EActivity
public class StartActivity extends Activity {

    public static int SURPLUS;//坐标偏差值

    @Extra
    String rival;//对手（电脑/人）
    @Extra
    int level;//难度
    @Extra
    public Player player1;
    @Extra
    public Player player2;

    DotsCanvas root;
    DotView[][] dotViews;
    Square[][] squares;
    DotModel dotModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        root = (DotsCanvas) findViewById(R.id.dots);
        dotModel = new DotModel(player1, player2, root, this);
        dotViews = new DotView[level][level];
        squares = new Square[level - 1][level - 1];
        initGameView();
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
                    SURPLUS = surplus.getHeight() + result;
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
                final DotView dot = new DotView(StartActivity.this);
                dot.setmX(j);
                dot.setmY(i);
                dot.setImageResource(R.drawable.dot_normal);
                dot.setOnClickListener(dotModel);

                dot.post(new Runnable() {
                    @Override
                    public void run() {
                        Rect rect = new Rect();
                        dot.getGlobalVisibleRect(rect);
                        dot.setCoordX(rect.exactCenterX());
                        dot.setCoordY(rect.exactCenterY() - StartActivity.SURPLUS - dot.getHeight() / 2);
                        if (dot.getmX() == level - 1 && dot.getmY() == level - 1) {
                            //界面绘制完毕后初始化方块，并向坐标赋值
                            for (int i = 0; i < level - 1; i++) {
                                for (int j = 0; j < level - 1; j++) {
                                    Square square = new Square();
                                    square.setmX(j);
                                    square.setmY(i);
                                    square.setCoordX((dotViews[i][j + 1].getCoordX() - dotViews[i][j].getCoordX()) / 2 + dotViews[i][j].getCoordX());
                                    square.setCoordY((dotViews[i + 1][j].getCoordY() - dotViews[i][j].getCoordY()) / 2 + dotViews[i][j].getCoordY());
                                    squares[j][i] = square;
                                }
                            }
                            connDotAndSquare();
                        }
                    }
                });
                dotViews[j][i] = dot;
                linearLayout.addView(dot, dotsParams);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
            );
            params.gravity = Gravity.CENTER;
            root.addView(linearLayout, params);
        }
    }

    /**
     * 将点和对应象限的方块连接
     */
    void connDotAndSquare() {
        for (DotView[] dots :
                dotViews) {
            for (DotView d :
                    dots) {
                try {
                    d.setOne(squares[d.getmX()][d.getmY() - 1]);
                } catch (IndexOutOfBoundsException e) {

                }
                try {
                    d.setTwo(squares[d.getmX()][d.getmY()]);
                } catch (IndexOutOfBoundsException e) {

                }
                try {
                    d.setThree(squares[d.getmX() - 1][d.getmY()]);
                } catch (IndexOutOfBoundsException e) {

                }
                try {
                    d.setFour(squares[d.getmX() - 1][d.getmY() - 1]);
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }
    }
}

package com.pollyfat.squarega.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.Square;
import com.pollyfat.squarega.model.ComModel;
import com.pollyfat.squarega.model.DotModel;
import com.pollyfat.squarega.util.Util;
import com.pollyfat.squarega.view.DotView;
import com.pollyfat.squarega.view.DotsCanvas;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by polly on 2016/3/18.
 * 游戏界面
 */
@EActivity
public class StartActivity extends Activity {

    public static int SURPLUS;//坐标偏差值

    @Extra
    String rival;//对手（电脑/人）
    @Extra
    public static int level;//难度
    @Extra
    public static Player playerOne;
    @Extra
    public static Player playerTwo;

    static DotsCanvas root;
    public static DotView[][] dotViews;
    public static Square[][] squares;
    static DotModel dotModel;
    static ComModel comModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        root = (DotsCanvas) findViewById(R.id.dots);
        dotModel = new DotModel(root, StartActivity.this);
        comModel = new ComModel(root, StartActivity.this);
        dotViews = new DotView[level][level];
        squares = new Square[level - 1][level - 1];
        initView();
        initGameView();
        setSurplus();
    }

    private void initView() {
        ((ImageView) findViewById(R.id.player_one_avatar)).setImageResource(Util.getDrawableResourceByName(playerOne.getAvatar(), this));
        ((ImageView) findViewById(R.id.player_two_avatar)).setImageResource(Util.getDrawableResourceByName(playerTwo.getAvatar(), this));
        ((TextView) findViewById(R.id.game_player_one)).setText(playerOne.getName());
        ((TextView) findViewById(R.id.game_player_two)).setText(playerTwo.getName());
    }

    @ViewById(R.id.score_one)
    static
    TextView scoreOne;
    @ViewById(R.id.score_two)
    static
    TextView scoreTwo;

    public static void setScore() {
        scoreOne.setText("得分："+playerOne.getWinSquare());
        scoreTwo.setText("得分："+playerTwo.getWinSquare());
    }

    /**
     * onDraw时获取的坐标包含状态栏和顶部Layout，获取这部分偏差值以矫正坐标
     */
    private void setSurplus() {
        final RelativeLayout surplus = (RelativeLayout) findViewById(R.id.head_zone);
        LinearLayout view = (LinearLayout) root.getChildAt(0);
        final DotView d = (DotView) view.getChildAt(0);
        ViewTreeObserver vto = surplus.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    int result = getResources().getDimensionPixelSize(resourceId);
                    SURPLUS = surplus.getHeight() - d.getHeight() / 2 + result;
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
                switch (level) {
                    case ChooseLevelActivity.LEVEL_EASY:
                        dot.setImageResource(R.drawable.dot_easy);
                        break;
                    case ChooseLevelActivity.LEVEL_NORMAL:
                        dot.setImageResource(R.drawable.dot_normal);
                        break;
                    case ChooseLevelActivity.LEVEL_HARD:
                        dot.setImageResource(R.drawable.dot_hard);
                        break;
                }
                if (playerTwo.getName().equals("电脑君"))
                    dot.setOnClickListener(comModel);
                else
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
                                    square.setCoordY((dotViews[i + 1][j + 1].getCoordY() - dotViews[i][j].getCoordY()) / 2 + dotViews[i][j].getCoordY());
                                    square.setCoordX((dotViews[i + 1][j + 1].getCoordX() - dotViews[i][j].getCoordX()) / 2 + dotViews[i][j].getCoordX());
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

    @Click(R.id.back)
    void back() {
        Intent intent = new Intent(this, MainActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        resetData();
    }

    @Click(R.id.begin_again)
    void beginAgain() {
        resetData();
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(StartActivity.this, MainActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public static void resetData() {
        root.clearLine();
        dotModel.resetLineCount();
        comModel.resetLineCount();
        playerOne.setWinSquare(0);
        playerTwo.setWinSquare(0);
        setScore();
        for (DotView[] dotArray :
                dotViews) {
            for (DotView dot :
                    dotArray) {
                dot.resetConn();
            }
        }
        for (Square[] squareArray :
                squares) {
            for (Square square :
                    squareArray) {
                square.resetLine();
            }
        }
    }
}

package com.pollyfat.squarega.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pollyfat.squarega.R;
import com.pollyfat.squarega.activity.ChooseLevelActivity;
import com.pollyfat.squarega.activity.GameWinDialog_;
import com.pollyfat.squarega.activity.RankingListActivity;
import com.pollyfat.squarega.activity.StartActivity;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.RecordItem;
import com.pollyfat.squarega.entity.Square;
import com.pollyfat.squarega.view.DotView;
import com.pollyfat.squarega.view.DotsCanvas;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by polly on 2016/6/2.
 * 对战计算机逻辑模型
 */
public class ComModel implements View.OnClickListener {
    static DotView dotStart;
    Context context;
    int level;
    static Player playerNow = StartActivity.playerOne;
    int lineCount = 0;
    int lineNum;
    DotsCanvas dotsCanvas;
    List<DotView> connDots = new ArrayList<>();
    List<Square> before = new ArrayList<>();

    @Override
    public void onClick(View v) {
        DotView d = (DotView) v;
        if (d.ismClickable()) {
            //如果为可点击状态，说明此点周围的点已被点击过，等待连接。
            //将连接点的坐标加入canvas的数组中，遍历数组绘图
            initBeforeList(dotStart);
            setSquareLine(StartActivity.playerOne, d, dotStart);
            changeDotStateToFalse();
            if (!findNewComp(dotStart, playerNow)) {
                comTurn();
            }
            isGameEnd();
        } else {
            changeDotStateToTrue(d);
        }
    }

    private void initBeforeList(DotView dotStart) {
        before.clear();
        for (Square s : dotStart.getSqares()) {
            try {
                if (s == null) {
                    before.add(null);
                } else {
                    before.add(s.clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    private void comTurn() {
        Square[][] squares = StartActivity.squares;
        DotView dotStart = null;
        for (int i = 0; i < StartActivity.level - 1; i++) {
            for (int j = 0; j < StartActivity.level - 1; j++) {
                if (squares[i][j] != null && squares[i][j].getBorderCount() == 3) {
                    if (!squares[i][j].isTop()) {
                        dotStart = StartActivity.dotViews[i][j];
                        initBeforeList(dotStart);
                        setSquareLine(StartActivity.playerTwo, StartActivity.dotViews[i][j], StartActivity.dotViews[i + 1][j]);
                    } else if (!squares[i][j].isBottom()) {
                        dotStart = StartActivity.dotViews[i][j + 1];
                        initBeforeList(dotStart);
                        setSquareLine(StartActivity.playerTwo, StartActivity.dotViews[i][j + 1], StartActivity.dotViews[i + 1][j + 1]);
                    } else if (!squares[i][j].isLeft()) {
                        dotStart = StartActivity.dotViews[i][j];
                        initBeforeList(dotStart);
                        setSquareLine(StartActivity.playerTwo, StartActivity.dotViews[i][j], StartActivity.dotViews[i][j + 1]);
                    } else if (!squares[i][j].isRight()) {
                        dotStart = StartActivity.dotViews[i + 1][j];
                        initBeforeList(dotStart);
                        setSquareLine(StartActivity.playerTwo, StartActivity.dotViews[i + 1][j], StartActivity.dotViews[i + 1][j + 1]);
                    }
                    if (findNewComp(dotStart, StartActivity.playerTwo)) {
                        comTurn();
                    }
                    return;
                }
            }
        }
        boolean unConn = true;
        while (unConn) {
            int i = (int) (Math.random() * level);
            int j = (int) (Math.random() * level);
            DotView dot = StartActivity.dotViews[i][j];
            initConnDots(dot);
            int arrow = (int) (Math.random() * (connDots.size() - 1));
            if (connDots.size() != 0 && connDots.get(arrow) != null) {
                initBeforeList(connDots.get(arrow));
                setSquareLine(StartActivity.playerTwo, connDots.get(arrow), dot);
                unConn = false;
                if (findNewComp(connDots.get(arrow), StartActivity.playerTwo)) {
                    comTurn();
                }
            }
        }
    }

    /**
     * 更改点的状态
     *
     * @param d 起始点
     */
    private void changeDotStateToTrue(DotView d) {
        //将周围的点设置为可连接状态
        dotStart = d;
        if (!connDots.isEmpty()) {
            changeDotStateToFalse();
        }
        initConnDots(d);

        for (final DotView dot :
                connDots) {
            dot.setImageResource(R.drawable.dot_one_anim);
            AnimationDrawable anim = (AnimationDrawable) dot.getDrawable();
            anim.start();
            dot.setmClickable(true);
        }
    }

    private void initConnDots(DotView d) {
        connDots.clear();
        if (!d.isUp()) {
            //上面的点未连接
            try {
                connDots.add(StartActivity.dotViews[d.getmX()][d.getmY() - 1]);
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        if (!d.isDown()) {
            //上面的点未连接
            try {
                connDots.add(StartActivity.dotViews[d.getmX()][d.getmY() + 1]);
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        if (!d.isLeft()) {
            //上面的点未连接
            try {
                connDots.add(StartActivity.dotViews[d.getmX() - 1][d.getmY()]);
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        if (!d.isRight()) {
            //上面的点未连接
            try {
                connDots.add(StartActivity.dotViews[d.getmX() + 1][d.getmY()]);
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }

    /**
     * 将可连接的点恢复正常状态
     */
    private void changeDotStateToFalse() {
        for (DotView dot :
                connDots) {
            dot.setmClickable(false);
            switch (level + 1) {
                case ChooseLevelActivity.LEVEL_EASY:
                    dot.setImageResource(R.drawable.dot_easy);
                    break;
                case ChooseLevelActivity.LEVEL_NORMAL:
                    dot.setImageResource(R.drawable.dot_normal);
                    break;
                case ChooseLevelActivity.LEVEL_HARD:
                    dot.setImageResource(R.drawable.dot_normal);
                    break;
            }
            dot.clearAnimation();
        }
    }

    /**
     * 记录连接后方块的状态
     */
    private void setSquareLine(Player player, DotView d, DotView dotStart) {
        int x = d.getmX() - dotStart.getmX();
        int y = d.getmY() - dotStart.getmY();
        if (x == 0) {
            if (y < 0) {
                //与上面的点连接
                dotStart.setUp(true);
                d.setDown(true);
                if (dotStart.getOne() != null)
                    dotStart.getOne().setLeft(true);
                if (dotStart.getFour() != null)
                    dotStart.getFour().setRight(true);
            } else {
                //与下面的点连接
                dotStart.setDown(true);
                d.setUp(true);
                if (dotStart.getTwo() != null)
                    dotStart.getTwo().setLeft(true);
                if (dotStart.getThree() != null)
                    dotStart.getThree().setRight(true);
            }
        } else if (x < 0) {
            //与左边的点连接
            dotStart.setLeft(true);
            d.setRight(true);
            if (dotStart.getFour() != null)
                dotStart.getFour().setBottom(true);
            if (dotStart.getThree() != null)
                dotStart.getThree().setTop(true);
        } else {
            //与右边的点连接
            dotStart.setRight(true);
            d.setLeft(true);
            if (dotStart.getOne() != null)
                dotStart.getOne().setBottom(true);
            if (dotStart.getTwo() != null)
                dotStart.getTwo().setTop(true);
        }
        lineCount++;
        dotsCanvas.addPointPair(player, dotStart.getCoordX(), dotStart.getCoordY(), d.getCoordX(), d.getCoordY());
        dotsCanvas.invalidate();
        setSquareCom(dotStart);
    }

    /**
     * 遍历发生了点击链接的点周围的方块，查看是否有方块被围成
     *
     * @param dot 发生点击连接的点(起始点）
     */
    private void setSquareCom(DotView dot) {
        List<Square> square = dot.getSqares();
        for (Square s :
                square) {
            if (s != null) {
                if (s.isTop() && s.isBottom() && s.isLeft() && s.isRight()) {
                    s.setComplete(true);
                }
            }
        }
    }

    /**
     * 构造函数
     *
     * @param dotsCanvas dotView的父容器，执行画线操作
     */
    public ComModel(DotsCanvas dotsCanvas, Context context) {
        this.dotsCanvas = dotsCanvas;
        this.context = context;
        this.level = StartActivity.level - 1;
        lineNum = level * 2 * (level + 1);
    }

    public void isGameEnd() {
        if (lineCount == lineNum) {
            SharedPreferences sp = context.getSharedPreferences(RankingListActivity.RANKING_LIST, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            RecordItem recordItem = new RecordItem();
            recordItem.setPlayerOne(StartActivity.playerOne);
            recordItem.setPlayerTwo(StartActivity.playerTwo);
            recordItem.setLevel(StartActivity.level);
            recordItem.setFirstWin(StartActivity.playerOne.getWinSquare() > StartActivity.playerTwo.getWinSquare());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("zh-Hant-CN"));
            String date = sdf.format(new Date());
            String[] dt = date.split(" ");
            recordItem.setDate(dt[0]);
            recordItem.setTime(dt[1]);
            recordItem.setLoserScore(StartActivity.playerOne.getWinSquare());
            recordItem.setLoserScore(StartActivity.playerTwo.getWinSquare());

            Gson gson = new Gson();
            String s = sp.getString(RankingListActivity.RANKING_LIST, "");
            Type type = new TypeToken<List<RecordItem>>() {
            }.getType();
            List<RecordItem> records = gson.fromJson(s, type);
            if (records == null) {
                records = new ArrayList<>();
            }
            records.add(recordItem);

            editor.putString(RankingListActivity.RANKING_LIST, new Gson().toJson(records));
            editor.apply();

            Intent intent = new Intent(context, GameWinDialog_.class);
            if ((StartActivity.playerOne.getWinSquare() > StartActivity.playerTwo.getWinSquare())) {
                //人赢了
                intent.putExtra("is_win", "true");
            }else if ((StartActivity.playerOne.getWinSquare() == StartActivity.playerTwo.getWinSquare())){
                intent.putExtra("is_win", "达成平局啦~");
            }else {
                intent.putExtra("is_win", "false");
            }
            context.startActivity(intent);
        }
    }

    /**
     * @param dot    startDot
     * @param player the player who get the square
     * @return
     */
    private boolean findNewComp(DotView dot, Player player) {
        List<Square> squares = dot.getSqares();
        boolean flag = false;
        for (int i = 0; i < before.size(); i++) {
            if (before.get(i) != null) {
                if (!(before.get(i).isComplete() == squares.get(i).isComplete())) {
                    dotsCanvas.addPoint(squares.get(i).getCoordX(), squares.get(i).getCoordY(), player);
                    dotsCanvas.invalidate();
                    player.setWinSquare(player.getWinSquare() + 1);
                    flag = true;
                }
            }
        }
        StartActivity.setScore();
        return flag;
    }

    public void resetLineCount() {
        lineCount = 0;
    }
}

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
 * Created by polly on 2016/5/11.
 * 游戏逻辑模型
 */

public class DotModel implements View.OnClickListener {

    static DotView dotStart;
    Context context;
    static Player playerNow = StartActivity.playerOne;
    int level;
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
            lineCount++;
            dotsCanvas.addPointPair(playerNow, dotStart.getCoordX(), dotStart.getCoordY(), d.getCoordX(), d.getCoordY());
            dotsCanvas.invalidate();
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
            setSquareLine(d, dotStart);
            changeDotStateToFalse();
            if (!findNewComp(dotStart, playerNow)) {
                //如果未完成方块，则交换焦点玩家
                if (playerNow == StartActivity.playerOne) {
                    playerNow = StartActivity.playerTwo;
                } else {
                    playerNow = StartActivity.playerOne;
                }
            }
            isGameEnd();
        } else {
            changeDotStateToTrue(d);
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
            if (playerNow == StartActivity.playerOne) {
                dot.setImageResource(R.drawable.dot_one_anim);
            }else {
                dot.setImageResource(R.drawable.dot_two_anim);
            }
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
    private void setSquareLine(DotView d, DotView dotStart) {
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
    public DotModel(DotsCanvas dotsCanvas, Context context) {
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
                intent.putExtra("winName", StartActivity.playerOne.getName()+"赢啦！");
            }else if ((StartActivity.playerOne.getWinSquare() == StartActivity.playerTwo.getWinSquare())){
                intent.putExtra("winName", "达成平局啦~");
            }else {
                intent.putExtra("winName", StartActivity.playerTwo.getName()+"赢啦！");
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
                    StartActivity.setScore();
                    flag = true;
                }
            }
        }
        return flag;
    }

    public void resetLineCount(){
        lineCount = 0;
    }
}

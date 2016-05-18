package com.pollyfat.squarega.model;

import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.Square;
import com.pollyfat.squarega.view.DotView;
import com.pollyfat.squarega.view.DotsCanvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bugre on 2016/5/11.
 */
public class DotModel implements View.OnClickListener {

    static DotView dotStart;
    Context context;
    static List<DotView> connDots = new ArrayList<>();
    Player player1, player2;
    static Player playerNow;
    static int level;
    DotsCanvas dotsCanvas;

    @Override
    public void onClick(View v) {
        DotView d = (DotView) v;
        if (d.ismClickable()) {
            //如果为可点击状态，说明此点周围的点已被点击过，等待连接。
            //将连接点的坐标加入canvas的数组中，遍历数组绘图
            dotsCanvas.addPointPair(dotStart.getCoordX(), dotStart.getCoordY(), d.getCoordX(), d.getCoordY());
            dotsCanvas.invalidate();
            setSquareLine(d, dotStart);
            changeDotStateToFalse();
            if (!findCompleteSquare(dotStart, playerNow)) {
                //如果未完成方块，则交换焦点玩家
                if (playerNow == player1) {
                    playerNow = player2;
                } else {
                    playerNow = player1;
                }
            }
//            isGameEnd();
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
        connDots = d.findConnDots();
        Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.dot_selected_anim_swell);
        anim1.setDuration(1000);
        anim1.setInterpolator(context, android.support.v7.appcompat.R.anim.abc_popup_enter);
        for (final DotView dot :
                connDots) {
            dot.setmClickable(true);
            dot.setImageResource(R.drawable.dot);
            dot.startAnimation(anim1);
        }
    }

    /**
     * 将可连接的点恢复正常状态
     */
    private void changeDotStateToFalse() {
        for (DotView dot :
                connDots) {
            dot.setmClickable(false);
            dot.setImageResource(R.drawable.dot_normal);
            dot.clearAnimation();
        }
    }

    /**
     * 记录连接后方块的状态
     */
    private void setSquareLine(DotView d1, DotView d2) {
        int x = d2.getmX() - d1.getmX();
        int y = d2.getmY() - d1.getmY();
        if (x == 0) {
            if (y < 0) {
                //与上面的点连接
                d2.setUp(true);
                if (d2.getOne() != null)
                    d2.getOne().setLeft(true);
                if (d2.getFour() != null)
                    d2.getFour().setRight(true);
            } else {
                //与下面的点连接
                d2.setDown(true);
                if (d2.getTwo() != null)
                    d2.getTwo().setLeft(true);
                if (d2.getThree() != null)
                    d2.getThree().setRight(true);
            }
        } else if (x < 0) {
            //与左边的点连接
            d2.setLeft(true);
            if (d2.getFour() != null)
                d2.getFour().setBottom(true);
            if (d2.getThree() != null)
                d2.getThree().setTop(true);
        } else {
            //与右边的点连接
            d2.setRight(true);
            if (d2.getOne() != null)
                d2.getOne().setBottom(true);
            if (d2.getTwo() != null)
                d2.getTwo().setTop(true);
        }
    }

    /**
     * 遍历发生了点击链接的点周围的方块，查看是否有方块被围成
     *
     * @param dot    发生点击连接的点(起始点）
     * @param player 操作的玩家
     */
    private boolean findCompleteSquare(DotView dot, Player player) {
        List<Square> squares = new ArrayList<>();
        squares.add(dot.getOne());
        squares.add(dot.getTwo());
        squares.add(dot.getThree());
        squares.add(dot.getFour());
        for (Square s :
                squares) {
            if (s != null) {
                if (s.isTop() && s.isBottom() && s.isLeft() && s.isRight()) {
                    dotsCanvas.addPoint(s.getCoordX(), s.getCoordY(), player);
                    dotsCanvas.invalidate();
                    s.setOwner(player);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 构造函数
     *
     * @param player1
     * @param player2
     * @param dotsCanvas dotView的父容器，执行画线操作
     */
    public DotModel(Player player1, Player player2, DotsCanvas dotsCanvas, Context context) {
        this.player1 = player1;
        this.player2 = player2;
        this.dotsCanvas = dotsCanvas;
        this.context = context;
    }

//        if (player1.getWinSquare() > level * level / 2) {
//        }
//        if (player2.getWinSquare() > level * level / 2) {
//        }

}

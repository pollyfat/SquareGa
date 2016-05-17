package com.pollyfat.squarega.listener;

import android.graphics.Rect;
import android.view.View;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.activity.StartActivity;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.Square;
import com.pollyfat.squarega.util.DrawSomething;
import com.pollyfat.squarega.view.DotView;
import com.pollyfat.squarega.view.DotsCanvas;
import com.pollyfat.squarega.view.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bugre on 2016/5/11.
 */
public class DotModel implements View.OnClickListener {

    static DotView dotStart;
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
            if (playerNow == player1) {
                playerNow = player2;
            } else {
                playerNow = player1;
            }
            float startX,startY,stopX,stopY;
            Rect rect = new Rect();
            d.getGlobalVisibleRect(rect);
            startX = rect.exactCenterX();
            startY = rect.exactCenterY()- StartActivity.SURPLUS;
            dotStart.getGlobalVisibleRect(rect);
            stopX = rect.exactCenterX();
            stopY = rect.exactCenterY()-StartActivity.SURPLUS;
            dotsCanvas.addPoint(new Point(startX,startY,stopX,stopY));
            dotsCanvas.invalidate();
            setSquareLine(d, dotStart);
            findCompleteSquare(dotStart, playerNow);
//            isGameEnd();
        } else {
            //不可连接状态，则将周围的点设置为可连接状态
            dotStart = d;
            if (!connDots.isEmpty()) {
                for (DotView dot :
                        connDots) {
                    dot.setmClickable(false);
                    dot.setImageResource(R.drawable.dot_normal);
                }
            }
            connDots = d.findConnDots();
            for (DotView dot :
                    connDots) {
                dot.setmClickable(true);
                dot.setImageResource(R.drawable.dot);
            }
        }
    }

    /**
     * 记录连接后方块的状态
     */
    private void setSquareLine(DotView d1, DotView d2) {
        int x = d1.getmX() - d2.getmX();
        int y = d1.getmY() - d2.getmY();
        if (x == 0) {
            if (y < 0) {
                //与上面的点连接
                if (d2.getOne() != null)
                    d2.getOne().setLeft(true);
                if (d2.getFour() != null)
                    d2.getFour().setRight(true);
            } else {
                //与下面的点连接
                if (d2.getTwo() != null)
                    d2.getTwo().setLeft(true);
                if (d2.getThree() != null)
                    d2.getThree().setRight(true);
            }
        } else if (x < 0) {
            //与左边的点连接
            if (d2.getFour() != null)
                d2.getFour().setBottom(true);
            if (d2.getThree() != null)
                d2.getThree().setTop(true);
        } else {
            //与右边的点连接
            if (d2.getOne() != null)
                d2.getOne().setBottom(true);
            if (d2.getTwo() != null)
                d2.getTwo().setTop(true);
        }
    }

    /**
     * 遍历发生了点击链接的点周围的方块，查看是否有方块被围成
     * @param dot 发生点击连接的点
     * @param player 操作的玩家
     */
    private void findCompleteSquare(DotView dot, Player player) {
        List<Square> squares = new ArrayList<>();
        squares.add(dot.getOne());
        squares.add(dot.getTwo());
        squares.add(dot.getThree());
        squares.add(dot.getFour());
        for (Square s :
                squares) {
            if (s != null) {
                if (s.isTop() && s.isBottom() && s.isLeft() && s.isRight()) {
                    DrawSomething.DrawFlag();
                    s.setOwner(player);
                }
            }
        }
    }

    /**
     * 构造函数
     * @param player1
     * @param player2
     * @param dotsCanvas dotView的父容器，执行画线操作
     */
    public DotModel(Player player1, Player player2, DotsCanvas dotsCanvas) {
        this.player1 = player1;
        this.player2 = player2;
        this.dotsCanvas = dotsCanvas;
    }

//        if (player1.getWinSquare() > level * level / 2) {
//        }
//        if (player2.getWinSquare() > level * level / 2) {
//        }

}

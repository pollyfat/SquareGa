package com.pollyfat.squarega.listener;

import android.view.View;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.Square;
import com.pollyfat.squarega.util.DrawSomething;
import com.pollyfat.squarega.view.DotView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bugre on 2016/5/11.
 */
public class DotModel implements View.OnClickListener {

    DrawLineCallback drawLine;
    static DotView dotStart;
    static List<DotView> connDots = new ArrayList<>();
    Player player1, player2;
    static Player playerNow;
    static int level;

    public DotModel(DrawLineCallback drawLine) {
        this.drawLine = drawLine;
    }

    @Override
    public void onClick(View v) {
        DotView d = (DotView) v;
        if (d.ismClickable()) {
            if (playerNow == player1) {
                playerNow = player2;
            } else {
                playerNow = player1;
            }
            int[] locationD = new int[2];
            int[] locationS = new int[2];
            d.getLocationInWindow(locationD);
            dotStart.getLocationInWindow(locationS);
            drawLine.drawLine(locationD[0], locationS[0], locationD[1], locationS[1]);
            setSquareLine(d, dotStart);
            findCompleteSquare(dotStart, playerNow);
            isGameEnd();
        } else {
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

    private void isGameEnd() {
//        if (player1.getWinSquare() > level * level / 2) {
//        }
//        if (player2.getWinSquare() > level * level / 2) {
//        }
    }

    public interface DrawLineCallback {
        void drawLine(float startX, float startY, float stopX, float stopY);
    }
}

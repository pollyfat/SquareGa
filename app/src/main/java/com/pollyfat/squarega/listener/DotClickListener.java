package com.pollyfat.squarega.listener;

import android.view.View;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.util.DrawLine;
import com.pollyfat.squarega.view.DotView;

import java.util.List;

/**
 * Created by bugre on 2016/5/11.
 */
public class DotClickListener implements View.OnClickListener {


    static DotView dotStart;

    @Override
    public void onClick(View v) {
        DotView d = (DotView) v;
        if (d.ismClickable()) {
            DrawLine.connectDots(d,dotStart);
        }else {
            dotStart = d;
            d.findConnDots();
        }
    }
}

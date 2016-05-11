package com.pollyfat.squarega.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.view.DotView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bugre on 2016/3/18.
 */
@EActivity
public class StartActivity extends Activity implements View.OnClickListener {

    @Extra
    String rival;//对手
    @Extra
    String level;//难度

    LinearLayout root;
    List dots=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        root = (LinearLayout) findViewById(R.id.dots);
        initGameView();
    }

    /**
     * 初始化游戏界面的圆点
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void initGameView() {
        int le = Integer.parseInt(level);
        for (int i = 0; i < le; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < le; j++) {
                LinearLayout.LayoutParams dotsParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f
                );
                dotsParams.gravity = Gravity.CENTER;
                DotView dot = new DotView(StartActivity.this);
                dot.setmX(i);
                dot.setmY(j);
                dot.setImageResource(R.drawable.dot_normal);
                dot.setOnClickListener(this);
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

    /**
     * 圆点的点击监听
     */
    @Override
    public void onClick(View v) {

    }
}

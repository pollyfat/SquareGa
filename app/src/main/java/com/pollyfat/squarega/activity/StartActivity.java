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

/**
 * Created by bugre on 2016/3/18.
 */
@EActivity
public class StartActivity extends Activity {

    @Extra
    String rival;
    @Extra
    String level;

    LinearLayout root;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        root = (LinearLayout) findViewById(R.id.dots);
        initGameView();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void initGameView() {
        int le = Integer.parseInt(level);
        for (int i = 0; i < le; i++) {
            linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < le; j++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f
                );
                params.gravity = Gravity.CENTER;
                DotView dot = new DotView(this);
                dot.setmX(i);
                dot.setmY(j);
                dot.setImageResource(R.drawable.dot_normal);
                dot.setOnClickListener(dotListener);
                linearLayout.addView(dot);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f
            );
            params.gravity = Gravity.CENTER;
            root.addView(linearLayout,params);
        }
    }

    /**
     * 圆点的点击监听
     */
    View.OnClickListener dotListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(StartActivity.this,"click",Toast.LENGTH_LONG).show();
            if (((DotView) v).isClickable()) {

            }
            int childCount=root.getChildCount();
            //设置上下左右可点击
            for (int i=0;i<childCount;i++) {
                int row,column;
                DotView d = (DotView) root.getChildAt(i);
                row=d.getmX();
                column=d.getmY();
                if (row == ((DotView) v).getmX()) {
                    if (column == (((DotView) v).getmY())+1||column == (((DotView) v).getmY())-1) {
                        d.setClickable(true);
                        d.setImageResource(R.drawable.dot);
                    }
                }
                if (column == ((DotView) v).getmY()) {
                    if (row == ((DotView) v).getmX() + 1 || row == ((DotView) v).getmX() + 1) {
                        d.setClickable(true);
                        d.setImageResource(R.drawable.dot);
                    }
                }
            }
        }
    };

}

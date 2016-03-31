package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.pollyfat.squarega.view.GameView;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

/**
 * Created by bugre on 2016/3/18.
 */
@EActivity
public class StartActivity extends Activity {

    @Extra
    String rival;
    @Extra
    String level;

    @AfterExtras
    public void initGame() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

    }
}

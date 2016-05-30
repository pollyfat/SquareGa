package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pollyfat.squarega.R;

import org.androidannotations.annotations.EActivity;

@EActivity
public class GameWinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);
    }
}

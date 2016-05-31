package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pollyfat.squarega.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity
public class GameWinDialog extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);
    }

    @Click(R.id.begin_again)
    void beginAgain(){

    }

    @Click(R.id.home)
    void goHome(){
        Intent intent = new Intent(GameWinDialog.this, MainActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Click(R.id.share)
    void share(){

    }

}

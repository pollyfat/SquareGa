package com.pollyfat.squarega.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.pollyfat.squarega.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_setting)
public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click(R.id.setting_music)
    public void taggleMusic(View view) {

    }

    @Click(R.id.setting_about)
    public void showAbout(View view){

    }
}

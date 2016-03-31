package com.pollyfat.squarega;

import android.app.Activity;
import android.content.Intent;
import android.view.Window;

import com.pollyfat.squarega.activity.ChooseActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Click(R.id.main_play)
    void playWasClicked() {
        Intent intent = new Intent(MainActivity.this, ChooseActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.main_gold)
    void goldWasClicked(){

    }

    @Click(R.id.main_setting)
    void settingWasClicked(){

    }

    @Override
    protected void onPause() {
        //程序被打断时执行
        super.onPause();
    }
}

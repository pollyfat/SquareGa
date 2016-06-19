package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.service.SoundService;
import com.pollyfat.squarega.util.SoundUtil;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;

@WindowFeature({Window.FEATURE_NO_TITLE})
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
//    private Intent intent = new Intent(MainActivity.this, SoundService_.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoundUtil.init(this);
//        startService(intent);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        stopService(intent);
//    }

    @Click(R.id.main_play)
    void playWasClicked() {
        Intent intent = new Intent(MainActivity.this, ChoosePlayersActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.main_gold)
    void goldWasClicked(){
        Intent intent = new Intent(MainActivity.this, RankingListActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.main_setting)
    void settingWasClicked(){
        Intent intent = new Intent(MainActivity.this, SettingActivity_.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        ExitApp();
    }

    private long exitTime = 0;

    public void ExitApp()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else
        {
            this.finish();
        }

    }

}

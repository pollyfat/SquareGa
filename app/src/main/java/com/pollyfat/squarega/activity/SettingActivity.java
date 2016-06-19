package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.util.SoundUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_setting)
public class SettingActivity extends Activity {

    @ViewById(R.id.setting_music)
    ImageButton music;
    @ViewById(R.id.setting_about)
    ImageButton aboutBtn;
    @ViewById(R.id.setting_about_text)
    ImageView bg_dot;
    @ViewById(R.id.about)
    TextView about;
    @ViewById(R.id.scroll_texts)
    ScrollView texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @AfterViews
    public void setMusicBtn(){
        if (SoundUtil.isMusicSt()) {
            music.setBackgroundResource(R.drawable.turn_on_bgmusic);
        }else {
            music.setBackgroundResource(R.drawable.turn_off_bgmusic);
        }
    }
    @Click(R.id.back)
    public void finishThisActivity() {
        this.finish();
    }


    @Click(R.id.setting_music)
    public void toggleMusic() {
        if (SoundUtil.isMusicSt()) {
            music.setBackgroundResource(R.drawable.turn_off_bgmusic);
            SoundUtil.musicToggle();
        } else {
            music.setBackgroundResource(R.drawable.turn_on_bgmusic);
            SoundUtil.musicToggle();
        }
    }

    boolean isShowing = false;

    @Click(R.id.setting_about)
    public void showAbout() {
        if (!isShowing) {
            aboutBtn.setVisibility(View.INVISIBLE);
            bg_dot.setVisibility(View.VISIBLE);
            about.setVisibility(View.VISIBLE);
            texts.setVisibility(View.VISIBLE);
            isShowing = true;
        } else {
            aboutBtn.setVisibility(View.VISIBLE);
            bg_dot.setVisibility(View.INVISIBLE);
            about.setVisibility(View.INVISIBLE);
            texts.setVisibility(View.INVISIBLE);
            isShowing = false;
        }
    }
}

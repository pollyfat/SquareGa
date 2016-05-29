package com.pollyfat.squarega;

import android.app.Application;

import com.pollyfat.squarega.util.SoundUtil;


/**
 * Created by android on 2016/5/26.
 */
public class GameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SoundUtil.init(this);
        SoundUtil.startMusic();
    }
}

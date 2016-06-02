package com.pollyfat.squarega;

import android.app.Application;


import org.androidannotations.annotations.EApplication;


/**
 * Created by polly on 2016/5/26.
 *
 */
@EApplication
public class GameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        SoundUtil.init(this);
//        SoundUtil.startMusic();
    }
}

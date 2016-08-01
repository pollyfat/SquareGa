package com.pollyfat.squarega;

import android.app.Application;


import com.squareup.leakcanary.LeakCanary;

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
        LeakCanary.install(this);
//        SoundUtil.init(this);
//        SoundUtil.startMusic();
    }
}

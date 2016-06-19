package com.pollyfat.squarega.util;

/**
 * Created by polly on 2016/5/27.
 *
 */

import android.content.Context;
import android.media.MediaPlayer;

import com.pollyfat.squarega.R;

/**
 * 声音控制类
 */
public class SoundUtil {

    private static MediaPlayer music;

    private static boolean musicSt = true; //音乐开关
    private static Context context;

    /**
     * 初始化方法
     *
     * @param c
     */
    public static void init(Context c) {
        context = c;
        initMusic();
    }

    //初始化音乐播放器
    private static void initMusic() {
        music = MediaPlayer.create(context, R.raw.music_two);
        music.setLooping(true);
        music.start();
    }


    /**
     * 获得音乐开关状态
     *
     * @return
     */
    public static boolean isMusicSt() {
        return musicSt;
    }

    public static void musicToggle() {
        if (musicSt) {
            musicSt = false;
            music.pause();
        } else {
            musicSt = true;
            music.start();
        }
    }

}

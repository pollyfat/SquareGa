package com.pollyfat.squarega.util;

/**
 * Created by polly on 2016/5/27.
 *
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.pollyfat.squarega.R;

/**
 * 声音控制类
 */
public class SoundUtil {

    private static MediaPlayer music;
    private static SoundPool soundPool;

    private static boolean musicSt = true; //音乐开关
    //    private static boolean soundSt = true; //音效开关
    private static Context context;

    private static final int[] musicId = {R.raw.music_two};
    private static Map<Integer, Integer> soundMap; //音效资源id与加载过后的音源id的映射关系表

    /**
     * 初始化方法
     *
     * @param c
     */
    public static void init(Context c) {
        context = c;

        initMusic();

//        initSound();
    }

    //初始化音效播放器
    private static void initSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
        soundMap = new HashMap<>();
        soundMap.put(R.raw.bg_music, soundPool.load(context, R.raw.bg_music, 1));
    }

    //初始化音乐播放器
    private static void initMusic() {
//        int r = new Random().nextInt(musicId.length);
        music = MediaPlayer.create(context, R.raw.music_two);
        music.setLooping(true);
    }

//    /**
//     * 播放音效
//     * @param resId 音效资源id
//     */
//    public static void playSound(int resId)
//    {
//        if(soundSt == false)
//            return;
//
//        Integer soundId = soundMap.get(resId);
//        if(soundId != null)
//            soundPool.play(soundId, 1, 1, 1, 0, 1);
//    }

    /**
     * 暂停音乐
     */
    public static void pauseMusic() {
        if (music.isPlaying()) {
            music.pause();
        }
    }

    /**
     * 播放音乐
     */
    public static void startMusic() {
        music.start();
    }

    /**
     * 切换一首音乐并播放
     */
    public static void changeAndPlayMusic() {
        if (music != null)
            music.release();
        initMusic();
        startMusic();
    }

    /**
     * 获得音乐开关状态
     *
     * @return
     */
    public static boolean isMusicSt() {
        return musicSt;
    }
//
//    /**
//     * 设置音乐开关
//     * @param musicSt
//     */
//    public static void setMusicSt(boolean musicSt) {
//        SoundUtil.musicSt = musicSt;
//        if(musicSt)
//            music.start();
//        else
//            music.stop();
//    }

    public static void musicToggle() {
        if (musicSt) {
            musicSt = false;
            music.pause();
        } else {
            musicSt = true;
            music.start();
        }
    }

//    /**
//     * 获得音效开关状态
//     * @return
//     */
//    public static boolean isSoundSt() {
//        return soundSt;
//    }
//
//    /**
//     * 设置音效开关
//     * @param soundSt
//     */
//    public static void setSoundSt(boolean soundSt) {
//        SoundUtil.soundSt = soundSt;
//    }

//    /**
//     * 发出‘邦’的声音
//     */
//    public static void boom()
//    {
//        playSound(R.raw.itemboom);
//    }
}
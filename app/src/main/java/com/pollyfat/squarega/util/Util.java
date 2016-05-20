package com.pollyfat.squarega.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by android on 2016/5/20.
 */
public class Util  {

    //通过字符串获取资源
//    public static int getStringResourceByName(String aString, Context context) {
//        String packageName = context.getPackageName();
//        int resId = context.getResources().getIdentifier(aString, "string", packageName);
//        return resId;
//    }

    public static int getDrawableResourceByName(String aString, Context context) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(aString, "drawable", packageName);
        return resId;
    }


    public static ArrayList<?> getListObject(SharedPreferences preferences,String key, Class<?> mClass){
    	Gson gson = new Gson();
		// TODO: 2016/5/20  java.lang.ClassCastException: java.util.Arrays$ArrayList cannot be cast to java.util.ArrayList
		ArrayList<String> objStrings = (ArrayList<String>) Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚"));
    	ArrayList<Object> objects =  new ArrayList<>();
    	for(String jObjString : objStrings){
    		Object value  = gson.fromJson(jObjString,  mClass);
    		objects.add(value);
    	}
    	return objects;
    }
}

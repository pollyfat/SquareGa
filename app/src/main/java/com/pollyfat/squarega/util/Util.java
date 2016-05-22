package com.pollyfat.squarega.util;

import android.content.Context;

/**
 * Created by android on 2016/5/20.
 */
public class Util  {

    public static int getDrawableResourceByName(String aString, Context context) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(aString, "drawable", packageName);
        return resId;
    }
}

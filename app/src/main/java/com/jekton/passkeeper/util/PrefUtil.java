package com.jekton.passkeeper.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @author Jekton
 */

public abstract class PrefUtil {

    private static final String PREF = "default_pref";

    private PrefUtil() {
    }


    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }


    private static SharedPreferences.Editor getEditor(Context context) {
        return getPreference(context).edit();
    }


    public static boolean getBoolean(Context context, String key, boolean defaultVal) {
        SharedPreferences preferences = getPreference(context);
        return preferences.getBoolean(key, defaultVal);
    }


    public static void setBoolean(Context context, String key, boolean val) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, val);
        editor.apply();
    }
}

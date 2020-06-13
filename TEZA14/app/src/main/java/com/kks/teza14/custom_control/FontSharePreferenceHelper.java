package com.kks.teza14.custom_control;

import android.content.Context;
import android.content.SharedPreferences;

public class FontSharePreferenceHelper {

    private static String FONT_KEY = "font_name";
    private static String LANGUAGE_KEY = "language_name";

    private SharedPreferences sharedPreference;

    public FontSharePreferenceHelper(Context context) {
        String SHARE_PREFRENCE = "jcgvandroid.font";
        sharedPreference = context.getSharedPreferences(SHARE_PREFRENCE, Context.MODE_PRIVATE);
    }

    //Zg or MM3
    public boolean isFontSetup() {
        return sharedPreference.contains(FONT_KEY);
    }
    public String getFont() {
        return sharedPreference.getString(FONT_KEY, "");
    }

    public void setFont(String language) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(FONT_KEY, language);
        editor.apply();
    }

    public String getLanguage()
    {
        return sharedPreference.getString(LANGUAGE_KEY, "");
    }

    public void setLanguage(String language)
    {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(LANGUAGE_KEY, language);
        editor.apply();
    }

    public boolean isLanguageFontSetup() {
        if(sharedPreference.contains(FONT_KEY) && sharedPreference.contains(LANGUAGE_KEY))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}

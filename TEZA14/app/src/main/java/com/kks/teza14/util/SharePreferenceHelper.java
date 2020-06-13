package com.kks.teza14.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharePreferenceHelper {

    private SharedPreferences sharedPreference;

    private static String SHARE_PREFRENCE = "caremetele";

    private static String USER_ID_KEY = "USER_ID_KEY";
    private static String USER_NAME_KEY = "USER_NAME_KEY";
    private static String USER_PHONE_KEY = "USER_PHONE_KEY";
    private static String USER_GENDER_KEY = "USER_GENDER_KEY";
    private static String USER_AGE_KEY = "USER_AGE_KEY";
    private static String USER_APP_ID_KEY = "USER_APP_ID_KEY";
    private static String APP_LANGUAGE_KEY = "APP_LANGUAGE_KEY";
    private static String USER_FCM_TOKEN = "USER_FCM_TOKEN";
    private static String USER_PROFILE_URL = "USER_PROFILE_URL";

    private static String ADDRESS_LAT = "ADDRESS_LAT";
    private static String ADDRESS_LNG = "ADDRESS_LNG";
    private static String ADDRESS = "ADDRESS";
    private static String ADDRESS_STATE = "ADDRESS_STATE";
    private static String ADDRESS_TOWNSHIP = "ADDRESS_TOWNSHIP";
    private static String ADDRESS_WARD = "ADDRESS_WARD";

    private static String NOTIFICATION_ON_OFF = "NOTIFICATION_ON_OFF";

    @Inject
    public SharePreferenceHelper(Context context) {
        sharedPreference = context.getSharedPreferences(SHARE_PREFRENCE, Context.MODE_PRIVATE);
    }

    //***** Member Login *****//
    public void setLogin(int userId, String userAppId, String userName, String userFcmToken, String profileUrl) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putInt(USER_ID_KEY,userId);
        editor.putString(USER_NAME_KEY, userName);
        editor.putString(USER_APP_ID_KEY, userAppId);
        editor.putString(USER_FCM_TOKEN, userFcmToken);
        editor.putString(USER_PROFILE_URL, profileUrl);
        editor.commit();
    }

    public void logoutSharePreference() {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.clear();
        editor.commit();
    }

    public void setAddress(String lat, String lng, String address, String state, String township, String ward) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(ADDRESS_LAT, lat);
        editor.putString(ADDRESS_LNG, lng);
        editor.putString(ADDRESS, address);
        editor.putString(ADDRESS_STATE, state);
        editor.putString(ADDRESS_TOWNSHIP, township);
        editor.putString(ADDRESS_WARD, ward);
        editor.commit();
    }

    public void setAppLanguage(String language) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(APP_LANGUAGE_KEY, language);
        editor.commit();
    }

    public String getUserProfileUrl() {
        return sharedPreference.getString(USER_PROFILE_URL,"");
    }

    public String getUserFcmToken() {
        return sharedPreference.getString(USER_FCM_TOKEN,"");
    }

    public String getAddress() {
    	return sharedPreference.getString(ADDRESS,"") + " "
				+ sharedPreference.getString(ADDRESS_WARD,"") + " "
				+ sharedPreference.getString(ADDRESS_TOWNSHIP,"") + " "
				+ sharedPreference.getString(ADDRESS_STATE,"");

	}

	public boolean getNotificationOnOff() {
        return sharedPreference.getBoolean(NOTIFICATION_ON_OFF,true);
    }

    public void setNotificationOnOff(boolean status) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(NOTIFICATION_ON_OFF,status);
        editor.commit();
    }

    public String getUserLat() {
        return sharedPreference.getString(ADDRESS_LAT,"");
    }

    public String getUserLng() {
        return sharedPreference.getString(ADDRESS_LNG,"");
    }

	private String stateCode;
	private String townshipCode;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	public String getUserAddress() {
        return sharedPreference.getString(ADDRESS,"");
    }

    public String getUserWard() {
        return sharedPreference.getString(ADDRESS_WARD,"");
    }

    public String getUserTownship() {
        return sharedPreference.getString(ADDRESS_TOWNSHIP,"");
    }

    public String getUserState() {
        return sharedPreference.getString(ADDRESS_STATE,"");
    }


    public String getAppLanguage() {
        return sharedPreference.getString(APP_LANGUAGE_KEY, "en");
    }

    public int getUserId() {
        return sharedPreference.getInt(USER_ID_KEY, 0);
    }

    public String getUserName() {
        return sharedPreference.getString(USER_NAME_KEY, "");
    }

    public String getUserPhone() {
        return sharedPreference.getString(USER_PHONE_KEY, "");
    }

    public String getUserGender() {
        return sharedPreference.getString(USER_GENDER_KEY, "");
    }

    public String getUserAge() {
        return sharedPreference.getString(USER_AGE_KEY, "");
    }

    public String getUserAppId() {
        return sharedPreference.getString(USER_APP_ID_KEY, "");
    }

    public boolean isLogin() {
        return sharedPreference.contains(USER_ID_KEY);
    }
    //***** Member Login *****//


}

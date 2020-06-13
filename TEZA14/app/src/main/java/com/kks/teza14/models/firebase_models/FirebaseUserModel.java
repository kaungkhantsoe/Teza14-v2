package com.kks.teza14.models.firebase_models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaungkhantsoe on 29/05/2020.
 **/

@IgnoreExtraProperties
public class FirebaseUserModel {

    public String userId;
    public boolean isOnline;

    public FirebaseUserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public FirebaseUserModel(String userId, boolean isOnline) {
        this.userId = userId;
        this.isOnline = isOnline;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("isOnline", isOnline);

        return result;
    }
}

package com.kks.teza14.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.kks.teza14.common.Pageable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaungkhantsoe on 31/05/2020.
 **/

@IgnoreExtraProperties
public class LoginModel implements Pageable, Serializable {

    public String uid;
    public String profileUrl;
    public String name;
    public boolean approved;
    public String dbKey;
    public String email;
    public int position;
    public String approvedBy;

    public LoginModel() {
        // Default constructor required for calls to DataSnapshot.getValue(MemberModel.class)
    }

    public LoginModel(String uid, String profileUrl, String name, boolean approved, String dbKey, String email, int position, String approvedBy) {
        this.uid = uid;
        this.profileUrl = profileUrl;
        this.name = name;
        this.approved = approved;
        this.dbKey = dbKey;
        this.email = email;
        this.position = position;
        this.approvedBy = approvedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("profileUrl", profileUrl);
        result.put("approved", approved);
        result.put("dbKey", dbKey);
        result.put("email", email);
        result.put("position",position);
        result.put("approvedBy",approvedBy);
        return result;
    }
}
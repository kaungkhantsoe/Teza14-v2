package com.kks.teza14.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.encoders.annotations.Encodable;
import com.kks.teza14.common.Pageable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
@IgnoreExtraProperties
public class MemberModel implements Pageable, Serializable {

    public int id;
    public String name;
    public String commissionNumber;
    public String ownNumber;
    public String cadetNumber;
    public String spouseName;
    public String family;
    public String address;
    public String job;
    public String phone;
    public String note;
    public boolean deceased;
    public String familyUrl;
    public String userUrl;

    public int position;

    public MemberModel() {
        // Default constructor required for calls to DataSnapshot.getValue(MemberModel.class)
    }

    public MemberModel(int id, String name, String commissionNumber, String ownNumber, String cadetNumber, String spouseName, String family, String address, String job, String phone, String note, boolean deceased, String familyUrl, String userUrl) {
        this.id = id;
        this.name = name;
        this.commissionNumber = commissionNumber;
        this.ownNumber = ownNumber;
        this.cadetNumber = cadetNumber;
        this.spouseName = spouseName;
        this.family = family;
        this.address = address;
        this.job = job;
        this.phone = phone;
        this.note = note;
        this.deceased = deceased;
        this.familyUrl = familyUrl;
        this.userUrl = userUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommissionNumber() {
        return commissionNumber;
    }

    public void setCommissionNumber(String commissionNumber) {
        this.commissionNumber = commissionNumber;
    }

    public String getOwnNumber() {
        return ownNumber;
    }

    public void setOwnNumber(String ownNumber) {
        this.ownNumber = ownNumber;
    }

    public String getCadetNumber() {
        return cadetNumber;
    }

    public void setCadetNumber(String cadetNumber) {
        this.cadetNumber = cadetNumber;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDeceased() {
        return deceased;
    }

    public void setDeceased(boolean deceased) {
        this.deceased = deceased;
    }

    public String getFamilyUrl() {
        return familyUrl;
    }

    public void setFamilyUrl(String familyUrl) {
        this.familyUrl = familyUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("commissionNumber", commissionNumber);
        result.put("ownNumber", ownNumber);
        result.put("cadetNumber", cadetNumber);
        result.put("spouseName", spouseName);
        result.put("family", family);
        result.put("address", address);
        result.put("job", job);
        result.put("phone", phone);
        result.put("note", note);
        result.put("deceased", deceased);
        result.put("familyUrl", familyUrl);
        result.put("userUrl", userUrl);
        return result;
    }
}

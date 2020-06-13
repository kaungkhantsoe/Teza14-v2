package com.kks.teza14.models;

import com.kks.teza14.common.Pageable;

import java.io.Serializable;

/**
 * Created by kaungkhantsoe on 31/05/2020.
 **/
public class DummyPhoneModel implements Pageable, Serializable {
    String phoneNumber;

    public DummyPhoneModel(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

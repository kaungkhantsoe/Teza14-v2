package com.kks.teza14.ui.member_detail;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.models.MemberModel;

import javax.inject.Inject;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
public class MemberDetailViewModel extends ViewModel {

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Inject
    public MemberDetailViewModel() {

    }
}

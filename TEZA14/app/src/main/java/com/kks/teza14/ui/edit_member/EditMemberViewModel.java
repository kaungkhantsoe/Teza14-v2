package com.kks.teza14.ui.edit_member;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.models.MemberModel;

import javax.inject.Inject;

/**
 * Created by kaungkhantsoe on 31/05/2020.
 **/
public class EditMemberViewModel extends ViewModel {

    private MemberModel memberModel;
    private int position;

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Inject
    public EditMemberViewModel() {

    }
}

package com.kks.teza14.ui.pending_member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by kaungkhantsoe on 01/06/2020.
 **/
public class PendingMemberActivity extends BaseActivity {

    public static Intent getPendingMemberIntent(Context context) {
        Intent intent = new Intent(context, PendingMemberActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_pending_member;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

    }
}

package com.kks.teza14.ui.main.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseFragment;
import com.kks.teza14.custom_control.MyanBoldTextView;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.LoginModel;
import com.kks.teza14.ui.login.LoginActivity;
import com.kks.teza14.ui.member_detail.MemberDetailActivity;
import com.kks.teza14.ui.setup_profile_dialog.SetupProfileDialogActivity;
import com.kks.teza14.util.SharePreferenceHelper;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.NAME_PENDING;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.cv_profile)
    CardView cvProfile;

    @BindView(R.id.cv_pending)
    CardView cvPending;

    @BindView(R.id.cv_about)
    CardView cvAbout;

    @BindView(R.id.cv_logout)
    CardView cvLogout;

    @BindView(R.id.btv_count)
    MyanBoldTextView btvCount;

    @BindView(R.id.cv_count)
    CardView cvCount;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.tv_name)
    MyanTextView tvName;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @Inject
    @Named(NAME_PENDING)
    DatabaseReference ref;

    private LoginModel model;

    private int pendingCount = 0;

    private ValueEventListener valueEventListener;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        init();
    }

    private void init() {
        requestManager.load(sharePreferenceHelper.getUserProfileUrl())
                .into(ivProfile);

        tvName.setMyanmarText(sharePreferenceHelper.getUserName());

        cvProfile.setOnClickListener(this);
        cvPending.setOnClickListener(this);
        cvAbout.setOnClickListener(this);
        cvLogout.setOnClickListener(this);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LoginModel loginModel = snapshot.getValue(LoginModel.class);
                    if (!loginModel.getApproved()) {
                        pendingCount++;
                    }

                    if (loginModel.getUid().equals(sharePreferenceHelper.getUserAppId())) {
                        model = loginModel;
                    }
                }

                if (pendingCount == 0) {
                    cvCount.setVisibility(View.GONE);
                } else {
                    cvCount.setVisibility(View.VISIBLE);
                    btvCount.setMyanmarText(String.valueOf(btvCount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        getMember();

    }

    private void getMember() {

        ref.addValueEventListener(valueEventListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        ref.removeEventListener(valueEventListener);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cv_profile:

                if (model.getPosition() != -1)
                    startActivity(MemberDetailActivity.getMemberDetailActivity(mContext, model.getPosition()));
                else
                    startActivity(SetupProfileDialogActivity.getSetupProfileDialogIntent(mContext));

                break;

            case R.id.cv_pending:
                break;

            case R.id.cv_about:
                break;

            case R.id.cv_logout:
                logout();
                break;

            default:
                break;
        }
    }

    private void logout() {
        new AlertDialog.Builder(mContext)
                .setMessage("Are you sure ?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        (dialog, whichButton) -> {

                            sharePreferenceHelper.logoutSharePreference();
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        })
                .setNegativeButton("Cancel",
                        (dialog, whichButton) -> dialog.dismiss()).show();
    }
}

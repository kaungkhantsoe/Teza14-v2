package com.kks.teza14.ui.member_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.ui.edit_member.EditMemberActivity;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.*;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
public class MemberDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_user)
    ImageView ivUser;

    @BindView(R.id.iv_family)
    ImageView ivFamily;

    @BindView(R.id.tv_name)
    MyanTextView tvName;

    @BindView(R.id.tv_commission)
    MyanTextView tvCommission;

    @BindView(R.id.tv_own)
    MyanTextView tvOwn;

    @BindView(R.id.tv_cadet)
    MyanTextView tvCadet;

    @BindView(R.id.tv_spouse)
    MyanTextView tvSpouse;

    @BindView(R.id.tv_kids)
    MyanTextView tvKids;

    @BindView(R.id.tv_address)
    MyanTextView tvAddress;

    @BindView(R.id.tv_job)
    MyanTextView tvJob;

    @BindView(R.id.tv_phone)
    MyanTextView tvPhone;

    @BindView(R.id.tv_note)
    MyanTextView tvNote;

    @BindView(R.id.cv_edit)
    CardView cvEdit;

    @BindView(R.id.ll_call)
    LinearLayout llCall;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    @Named(NAME_MEMBER)
    DatabaseReference userRef;

    private MemberDetailViewModel viewModel;

    public static final String IE_POSITION = "position";

    private MemberModel detailModel;

    public static Intent getMemberDetailActivity(Context context, int position) {
        Intent intent = new Intent(context, MemberDetailActivity.class);
        intent.putExtra(IE_POSITION, position);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_member_detail;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbarColored(true);
        setupToolbarText(getString(R.string.member_detail));

        init();

    }

    private static final String TAG = "MemberDetailActivity";

    private void init() {
        viewModel = new ViewModelProvider(this, providerFactory).get(MemberDetailViewModel.class);

        Log.e(TAG, "init: " + getIntent().getIntExtra(IE_POSITION, 0) );
        viewModel.setPosition(getIntent().getIntExtra(IE_POSITION, 0));

        swipeRefreshLayout.setOnRefreshListener(this);

        getMemberDetail();
    }

    private void getMemberDetail() {

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int postion = 0;
                for (DataSnapshot snap : snapshot.getChildren()) {

                    Log.e(TAG, "onDataChange: " + postion + " " + viewModel.getPosition() );
                    if (postion == viewModel.getPosition()) {
                        try {
                            detailModel = snap.getValue(MemberModel.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    postion++;

                }

                if (detailModel != null && !MemberDetailActivity.this.isFinishing())
                    loadMemberDetail(detailModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadMemberDetail(MemberModel model) {

        if (familyPhotoArray.get(viewModel.getPosition()) != null) {
            Glide.with(this)
                    .load(familyPhotoArray.get(viewModel.getPosition()))
                    .error(R.drawable.placeholder)
                    .into(ivFamily);
        }

        if (memberPhotoArray.get(viewModel.getPosition()) != null) {
            requestManager.load(memberPhotoArray.get(viewModel.getPosition()))
                    .into(ivUser);
        } else {
            requestManager.load(R.drawable.placeholder)
                    .into(ivUser);
        }

        tvName.setMyanmarText(model.getName());
        tvCommission.setMyanmarText(model.getCommissionNumber());
        tvOwn.setMyanmarText(model.getOwnNumber());
        tvCadet.setMyanmarText(model.getCadetNumber());
        tvSpouse.setMyanmarText(model.getSpouseName());
        tvKids.setMyanmarText(model.getFamily());
        tvJob.setMyanmarText(model.getJob());
        tvPhone.setMyanmarText(model.getPhone());
        tvNote.setMyanmarText(model.getNote());
        tvAddress.setMyanmarText(model.getAddress());

        cvEdit.setOnClickListener(v -> startActivityForResult(EditMemberActivity.getEditMemberIntent(this, model, viewModel.getPosition()), 1));

        if (model.getPhone() != null)
            llCall.setOnClickListener(v -> showPhonePopup(MemberDetailActivity.this, model.getPhone()));

    }

    private void showPhonePopup(final Context context, String phone) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setIcon(R.drawable.ic_phone_black);
        builderSingle.setTitle("Select phone no.");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);

        String[] phones = phone.split(",");

        for (String s : phones) {
            arrayAdapter.add(s.trim());
        }


        builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {

            dialog.dismiss();

            String phoneNo = arrayAdapter.getItem(which);

            Intent intent;
            if (phoneNo != null) {
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo.trim()));
                context.startActivity(intent);
            }


        });
        builderSingle.show();

    }

    @Override
    public void onRefresh() {
        getMemberDetail();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
            getMemberDetail();
    }
}

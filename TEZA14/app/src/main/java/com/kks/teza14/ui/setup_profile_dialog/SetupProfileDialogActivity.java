package com.kks.teza14.ui.setup_profile_dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.custom_control.MyanTextView;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.ui.member_detail.MemberDetailActivity;
import com.kks.teza14.util.SharePreferenceHelper;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.NAME_MEMBER;
import static com.kks.teza14.util.AppConstants.NAME_PENDING;

/**
 * Created by kaungkhantsoe on 03/06/2020.
 **/
public class SetupProfileDialogActivity extends BaseActivity {

    private static final String TAG = "SetupProfileDialogActiv";

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.btn_no)
    MyanTextView btnNo;

    @BindView(R.id.btn_yes)
    MyanTextView btnYes;

    @Inject
    RequestManager requestManager;

    @Inject
    @Named(NAME_MEMBER)
    DatabaseReference memberRef;

    @Inject
    @Named(NAME_PENDING)
    DatabaseReference pendingRef;

    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @Inject
    ViewModelProviderFactory providerFactory;

    private GravitySnapHelper snapHelper;

    private SetupProfileAdapter adapter;

    private ValueEventListener valueEventListener;

    private SetupProfileViewModel viewModel;

    public static Intent getSetupProfileDialogIntent(Context context) {
        return new Intent(context, SetupProfileDialogActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_setup_profile_dialog;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        init();
    }

    private void init() {

        viewModel = new ViewModelProvider(this, providerFactory).get(SetupProfileViewModel.class);

        adapter = new SetupProfileAdapter(requestManager);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    try {
                        adapter.add(snap.getValue(MemberModel.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        btnNo.setOnClickListener(v -> finish());

        btnYes.setOnClickListener(v -> {
            if (snapHelper.getCurrentSnappedPosition() == -1) {
                startActivity(MemberDetailActivity.getMemberDetailActivity(SetupProfileDialogActivity.this, adapter.getItemCount() - 1));
                pendingRef.child(sharePreferenceHelper.getUserAppId()).child("position").setValue(adapter.getItemCount() - 1);
            } else {
                startActivity(MemberDetailActivity.getMemberDetailActivity(SetupProfileDialogActivity.this, snapHelper.getCurrentSnappedPosition()));
                pendingRef.child(sharePreferenceHelper.getUserAppId()).child("position").setValue(snapHelper.getCurrentSnappedPosition());
            }

        });

        setupRecycler();
    }

    private void setupRecycler() {
        snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);
        snapHelper.setMaxFlingSizeFraction(1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        getMembers();
    }

    private void getMembers() {

        adapter.clear();

        memberRef.addValueEventListener(valueEventListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        memberRef.removeEventListener(valueEventListener);
    }
}

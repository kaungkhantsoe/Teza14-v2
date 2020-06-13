package com.kks.teza14.ui.main.home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.RequestManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseFragment;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static androidx.recyclerview.widget.RecyclerView.*;
import static com.kks.teza14.util.AppConstants.NAME_MEMBER;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Inject
    @Named(NAME_MEMBER)
    DatabaseReference memberRef;

    private HomeAdapter adapter;
    private HomeViewModel viewModel;

    private ValueEventListener valueEventListener;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this, providerFactory).get(HomeViewModel.class);

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
                Log.w(TAG, "Listener was cancelled");
            }
        };

        swipeRefreshLayout.setOnRefreshListener(this);
        setupRecycler();
    }

    private void setupRecycler() {
        adapter = new HomeAdapter(requestManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        setupFirebaseDatabase();
    }

    private void setupFirebaseDatabase() {

//        memberRef.keepSynced(true);

        getUsers();

    }

    private void getUsers() {
        adapter.clear();

        memberRef.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        memberRef.removeEventListener(valueEventListener);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        getUsers();
    }
}

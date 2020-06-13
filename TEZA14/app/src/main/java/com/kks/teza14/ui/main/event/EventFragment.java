package com.kks.teza14.ui.main.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.RequestManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseFragment;
import com.kks.teza14.common.Pageable;
import com.kks.teza14.models.PostModel;
import com.kks.teza14.ui.add_event.AddEventActivity;
import com.kks.teza14.ui.main.MainViewModel;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.NAME_POSTS;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
public class EventFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, EventDelegate {

    private static final String TAG = "EventFragment";

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    @Named(NAME_POSTS)
    DatabaseReference reference;

    @Inject
    RequestManager requestManager;

    private EventAdapter adapter;

    private ValueEventListener valueEventListener;

    private MainViewModel mainViewModel;

//    private CustomScrollLinearLayoutManager layoutManager;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_event;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    private void init() {

        adapter = new EventAdapter(requestManager, this);

        swipeRefreshLayout.setOnRefreshListener(this);

        /*
         *  Get delegate from MainViewModel.
         *
         * ****** NOTE *******
         * requireActvity() is required if the viewMolde owner is not the same.
         * */
        mainViewModel = new ViewModelProvider(requireActivity(), providerFactory).get(MainViewModel.class);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Pageable> models = new ArrayList<>();

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    models.add(snap.getValue(PostModel.class));
                }

                if (adapter.getItemCount() == 0) {
                    adapter.add(models);
                } else if (adapter.getItemCount() < models.size()) { // item added
                    for (int i = adapter.getItemCount(); i < models.size(); i++) {
                        adapter.add(models.get(i));
                    }
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                } else if (adapter.getItemCount() > models.size()) { // item deleted

                    if (models.size() != 0)
                        for (int i = 0; i < adapter.getItemCount(); i++) {
                            for (int j = 0; j < models.size(); j++) {
                                if (((PostModel)adapter.getItemsList().get(i)).getId().equals( // exit from loop if item is found
                                        ((PostModel)models.get(j)).getId())) {
                                    break;
                                } else if (j == models.size()-1) { // delete item if not found
                                    adapter.remove(i);
                                }
                            }
                        }
                    else
                        adapter.clear();

                } else if (models.size() != 0 && adapter.getItemCount() == models.size()) { // item update
                    for (int i = 0; i < models.size(); i++) {
                        if (!((PostModel)models.get(i)).equals((PostModel)adapter.getItemsList().get(i))) {
                            adapter.update(models.get(i),i);
                            recyclerView.smoothScrollToPosition(i);
                        }
                    }
                }

                if (models.size() > 40) {
                    for (int i = 0; i < models.size() - 40; i++) {
                        reference.child(((PostModel)models.get(i)).getId()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        setupRecycler();
    }

    private void setupRecycler() {

//        layoutManager = new CustomScrollLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        /**
         *  To hide bottom nav on scroll
         * */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    // Scrolling up
                    mainViewModel.getMainDelegate().showOrHideFab(false);
                } else {
                    // Scrolling down
                    mainViewModel.getMainDelegate().showOrHideFab(true);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });

        getPosts();

    }

//    private boolean getRecyclerScrollableState() {
//        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        RecyclerView.Adapter adapter = recyclerView.getAdapter();
//        if (layoutManager == null || adapter == null)
//            return false;
//
//        return layoutManager.findLastCompletelyVisibleItemPosition() < adapter.getItemCount() - 1;
//    }

    /**
     * Call this method if recycler is nested and use custom layout manager
     */
//    private void checkRecyclerForScrollability() {
//
//        if (getRecyclerScrollableState()) {
//
//            layoutManager.setScrollEnabled(true);
//            recyclerView.setLayoutManager(layoutManager);
//
//            /**
//             *  To hide bottom nav on scroll
//             * */
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    if (dy > 0) {
//                        // Scrolling up
//                        mainViewModel.getMainDelegate().showOrHideBottomNavigation(false);
//                    } else {
//                        // Scrolling down
//                        mainViewModel.getMainDelegate().showOrHideBottomNavigation(true);
//                    }
//                }
//
//                @Override
//                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//
//                }
//            });
//        } else {
//            layoutManager.setScrollEnabled(false);
//            recyclerView.setLayoutManager(layoutManager);
//        }
//    }
    private void getPosts() {

        adapter.clear();

        reference.addValueEventListener(valueEventListener);

        mainViewModel.getMainDelegate().showOrHideFab(true);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        reference.removeEventListener(valueEventListener);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        getPosts();
    }

    @Override
    public void onDeletePost(int position, String postId) {
        reference.child(postId).removeValue();
    }

    @Override
    public void onEditPost(int position, PostModel postModel) {
        startActivity(AddEventActivity.getAddEventIntent(mContext, postModel));
    }

    //    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void consumeToken(AppEvents.NotificationEvent event) {
//        recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        AppEvents event = EventBus.getDefault().getStickyEvent(AppEvents.class);
//        if (event != null) {
//            EventBus.getDefault().removeStickyEvent(event);
//        }
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }
}

package com.kks.teza14.ui.search;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.custom_control.MyanmarZawgyiConverter;
import com.kks.teza14.custom_control.Rabbit;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

/**
 * Created by kaungkhantsoe on 31/05/2020.
 **/
public class SearchActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    @Inject
    @Named("memberRef")
    DatabaseReference userRef;

    private SearchAdapter adapter;
    private SearchViewModel viewModel;
    private androidx.appcompat.widget.SearchView searchView;

    private String name;

    private List<MemberModel> memberModels;

    private boolean isSearchActive = false;

    public static Intent getSearchIntent(Context context) {
        return new Intent(context,SearchActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        setupToolbarColored(true);
        setupToolbarText("Search");

        init();
    }

    private void init() {

        viewModel = new ViewModelProvider(this,providerFactory).get(SearchViewModel.class);

        setupRecycler();
    }

    private void setupRecycler() {
        adapter = new SearchAdapter(requestManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                searchView.clearFocus();
            }
        });

        getUsers();
    }

    private void getUsers() {
        adapter.clear();

        memberModels = new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int position = 0;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    try {
                        MemberModel model = snap.getValue(MemberModel.class);
                        model.setPosition(position);
                        memberModels.add(model);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    position++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        //return super.onCreateOptionsMenu(menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.search_action)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        EditText searchEdit = searchView.findViewById(R.id.search_src_text);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout searchEditFrame = searchView.findViewById(R.id.search_edit_frame); // Get the Linear Layout
        // Get the associated LayoutParams and set leftMargin
        ((LinearLayout.LayoutParams) searchEditFrame.getLayoutParams()).leftMargin = 18;
        searchView.setIconified(false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                110);

        searchView.setLayoutParams(lp);
        searchView.setQueryHint(getResources().getString(R.string.search_member_by_name));
        searchView.setBackground(getResources().getDrawable(R.drawable.search_grey));
        searchView.setOnCloseListener(() -> {
            return false;
        });

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // filter recycler view when query submitted
                name = getZgText(s.trim());

                if (!name.equals("")) {
                    searchEdit.clearFocus();
                    searchName(name);
                } else {
                    adapter.clear();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // filter recycler view when text is changed
                name = getZgText(s.trim());
                isSearchActive = true;

                if (!name.equals("")) {
                    searchName(name);
                } else {
                    adapter.clear();
                }


                return false;
            }
        });

        return true;
    }

    public void searchName(String text) {
        adapter.clear();

        if (memberModels.size() > 0) {
            for (int i = 0; i< memberModels.size(); i++) {
                if (memberModels.get(i).getName().contains(text))
                    adapter.add(memberModels.get(i));
            }
        }
    }

    private static final String TAG = "SearchActivity";

    public String getZgText(String text) {
        Log.e(TAG, "getZgText: " + text);
        if (MyanmarZawgyiConverter.isZawgyiEncoded(text)) {
            Log.e(TAG, "getZgText: zg");
            return text;
        } else {
            Log.e(TAG, "getZgText: uni" + Rabbit.uni2zg(text) );
            return Rabbit.uni2zg(text);
        }
    }


    @Override
    public void onBackPressed() {

        recyclerView.removeOnScrollListener(null);

        if (isSearchActive) {
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
            isSearchActive = false;
        } else {
            super.onBackPressed();
        }
    }

}

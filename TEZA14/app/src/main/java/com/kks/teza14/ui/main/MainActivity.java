package com.kks.teza14.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.custom_control.MyanEditText;
import com.kks.teza14.models.PostModel;
import com.kks.teza14.ui.add_event.AddEventActivity;
import com.kks.teza14.ui.main.profile.ProfileFragment;
import com.kks.teza14.ui.main.event.EventFragment;
import com.kks.teza14.ui.main.home.HomeFragment;
import com.kks.teza14.ui.search.SearchActivity;
import com.kks.teza14.util.SharePreferenceHelper;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import java.lang.reflect.Method;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainDelegate {

    private static final String TAG = "MainActivity";

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation navigationView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    private final int MY_PERMISSIONS_REQUEST_OVERLAY = 111;

    private MainViewModel viewModel;

    private MenuItem searchMenuItem;

    public static final String HOME_FRAGMENT = "HOME_FRAGMENT";
    public static final String EVENT_FRAGMENT = "APPOINTMENT_FRAGMENT";
    public static final String Profile_FRAGMENT = "ABOUT_FRAGMENT";

    public static Intent getMainIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
//        checkOverlayPermissionForApiLowerThanAndroidO();
        init();
    }

    private void init() {

        viewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);

        viewModel.setHomeFragDelegate(this);

        setupBottomNavBar();

        setupToolbar(false);

//        // Read from the database
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        int actionBarHeight = 0;

        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        // Calculate margin from dimens
        int marginNormal = Math.round(getResources().getDimension(R.dimen.margin_normal));

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        layoutParams.setMargins(0,0,marginNormal, actionBarHeight + marginNormal);

        fab.setOnClickListener(v -> startActivity(AddEventActivity.getAddEventIntent(MainActivity.this, new PostModel())));

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void consumeToken(AppEvents.NotificationEvent event) {
//        saveToken(event.getToken());
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

    private void checkOverlayPermissionForApiLowerThanAndroidO() {
        if (!canDrawOverlayViews(this)) {

            new AlertDialog.Builder(this)
                    .setTitle("Overlay permission required")
                    .setMessage("Please enable draw over application setting for application to work properly")
                    .setPositiveButton("Ok", (dialog, which) -> requestOverlayDrawPermission(this, MY_PERMISSIONS_REQUEST_OVERLAY))
                    .setNegativeButton("Cancel", null).show();
        }
    }

    /**
     * Check for overlay setting
     *
     * @param con application context
     * @return 'true' if overlay can be drawn
     */
    @SuppressLint("NewApi")
    public static boolean canDrawOverlayViews(Context con) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        try {
            return Settings.canDrawOverlays(con);
        } catch (NoSuchMethodError e) {
            return canDrawOverlaysUsingReflection(con);
        }
    }

    public static boolean canDrawOverlaysUsingReflection(Context context) {

        try {

            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class clazz = AppOpsManager.class;
            Method dispatchMethod = clazz.getMethod("checkOp", new Class[]{int.class, int.class, String.class});
            //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
            int mode = (Integer) dispatchMethod.invoke(manager, new Object[]{24, Binder.getCallingUid(), context.getApplicationContext().getPackageName()});

            return AppOpsManager.MODE_ALLOWED == mode;

        } catch (Exception e) {
            return false;
        }

    }

    @SuppressLint("InlinedApi")
    public static void requestOverlayDrawPermission(Activity act, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + act.getPackageName()));
        act.startActivityForResult(intent, requestCode);

    }

    private void setupBottomNavBar() {

        AHBottomNavigationItem homeItem = new AHBottomNavigationItem("", R.drawable.nav_home);
        AHBottomNavigationItem eventItem = new AHBottomNavigationItem("", R.drawable.nav_event);
        AHBottomNavigationItem profileItem = new AHBottomNavigationItem("", R.drawable.nav_account);

        navigationView.addItem(homeItem);
        navigationView.addItem(eventItem);
        navigationView.addItem(profileItem);

        // Change colors
        navigationView.setAccentColor(Color.parseColor("#19BEED"));
        navigationView.setInactiveColor(Color.parseColor("#BABABA"));

        // Disable tint color on item for custom icons
        navigationView.setForceTint(true);

        // Enable the translation inside the CoordinatorLayout
        navigationView.setBehaviorTranslationEnabled(true);

        navigationView.setTranslucentNavigationEnabled(true);

        // Set background color
        navigationView.setDefaultBackgroundColor(getResources().getColor(R.color.colorWhite));

        // Enable the translation of the FloatingActionButton
        navigationView.manageFloatingActionButtonBehavior(fab);

        // Set listeners
        navigationView.setOnTabSelectedListener((position, wasSelected) -> {
            // Do something cool here...

            switch (position) {
                case 0:
                    displayView("Home", HOME_FRAGMENT);
                    return true;
                case 1:
                    displayView("Events", EVENT_FRAGMENT);
                    return true;
                case 2:
                    displayView("Profile", Profile_FRAGMENT);
                    return true;
                default:
                    return false;

            }
        });

        navigationView.setCurrentItem(0);
    }

    /**
     * Show or hide the bottom navigation with animation
     */
    @Override
    public void showOrHideBottomNavigation(boolean show) {
        if (show) {
            navigationView.restoreBottomNavigation(true);
        } else {
            navigationView.hideBottomNavigation(true);
        }
    }

    @Override
    public void showOrHideFab(boolean show) {
        if (show) {
            fab.show();
        } else {
            fab.hide();
        }
    }

    private void displayView(String title, String menu) {

        Fragment fragment = null;

        switch (menu) {
            case HOME_FRAGMENT:
                if (searchMenuItem != null)
                    searchMenuItem.setVisible(true);

                fab.hide();
                fragment = new HomeFragment();
                break;

            case EVENT_FRAGMENT:
                if (searchMenuItem != null)
                    searchMenuItem.setVisible(false);

                fab.show();
                fragment = new EventFragment();
                break;

            case Profile_FRAGMENT:
                if (searchMenuItem != null)
                    searchMenuItem.setVisible(false);

                fab.hide();
                fragment = new ProfileFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
            if (!title.equals("")) {
                setupToolbarText(title);

//                if (getSupportActionBar() != null)
//                    getSupportActionBar().setIcon(R.drawable.logo_small);

            } else {
                setupToolbarText(" ");

//                if (getSupportActionBar() != null)
//                    getSupportActionBar().setIcon(R.drawable.logo_small);

            }
        }
    }

    @Override
    public void onBackPressed() {

        if (navigationView.getCurrentItem() != 0) {
            navigationView.setCurrentItem(0);
        } else {
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            startActivity(SearchActivity.getSearchIntent(MainActivity.this));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

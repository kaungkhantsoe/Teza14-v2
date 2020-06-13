package com.kks.teza14.common;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.R;
import com.kks.teza14.custom_control.MyanProgressDialog;
import com.kks.teza14.custom_control.MyanToast;
import com.kks.teza14.models.firebase_models.FirebaseUserModel;
import com.kks.teza14.ui.login.LoginActivity;
import com.kks.teza14.ui.splash.SplashActivity;
import com.kks.teza14.util.ForceUpdateAsync;
import com.kks.teza14.util.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

import static com.kks.teza14.util.AppConstants.NAME_MEMBER;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_text)
    TextView toolbar_text;

    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @Inject
    @Named(NAME_MEMBER)
    DatabaseReference databaseReference;

    private DatabaseReference userRef;

    private MyanProgressDialog myanProgressDialog;

    private FirebaseUserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);

        myanProgressDialog = new MyanProgressDialog(this);

        toolbar_text.setSelected(true);
        setUpContents(savedInstanceState);

//        if (BaseActivity.this.getClass() != SplashActivity.class && BaseActivity.this.getClass() != LoginActivity.class)
//            setupFirebaseDatabase();

    }

    private void setupFirebaseDatabase() {

        userModel = new FirebaseUserModel(sharePreferenceHelper.getUserAppId(), true);

        userRef = databaseReference.child(sharePreferenceHelper.getUserAppId());
        userRef.keepSynced(true);

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d(TAG, "connected");
                    addUserToFirebase();
                } else {
                    Log.d(TAG, "not connected");

                    if (!BaseActivity.this.isFinishing() && BaseActivity.this.getClass() != SplashActivity.class) {
                        showConnectionErrorDialog();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled");
            }
        });

          /*
        Delete user from database when disconnected
         */
        userRef.onDisconnect()
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                    }
                });

        addUserToFirebase();


    }

    protected void setupToolbar(boolean isChild) {

        if (toolbar != null)
            setSupportActionBar(toolbar);

        if (isChild) {
            if (getSupportActionBar() != null) {

                /*final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                upArrow.setColorFilter(getResources().getColor(R.color.colorTextColorPrimary), PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);*/


                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationIcon(R.drawable.ic_back_white);
            }
        }
    }

    protected void setupToolbarColored(boolean isChild) {

        if (toolbar != null)
            setSupportActionBar(toolbar);

        if (isChild) {
            if (getSupportActionBar() != null) {

                /*final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                upArrow.setColorFilter(getResources().getColor(R.color.colorTextColorPrimary), PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);*/


                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationIcon(R.drawable.ic_back_blue);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    protected void setupToolbarText(String text) {
        //getSupportActionBar().setTitle(text);
        toolbar_text.setText(text);
    }

    protected void setupToolbarBgColor(String color) {
        toolbar.setBackgroundColor(Color.parseColor(color));
    }

    protected void setupToolbarTextColor(String color) {
//        toolbar.setTitleTextColor(Color.parseColor(color));
        toolbar_text.setTextColor(Color.parseColor(color));
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void setUpContents(Bundle savedInstanceState);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // click on 'up' button in the action bar, handle it here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

//        forceUpdate();
    }

    public void showToastMsg(String msg) {
        MyanToast.makeText(this,msg);
    }

    public void showProgressDialog() {
        myanProgressDialog.showDialog();
    }

    public void hideProgressDialog() {
        myanProgressDialog.hideDialog();
    }

    /**
     * Check App Store for update.
     */
    private void forceUpdate() {
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String currentVersion = packageInfo.versionName;

            new ForceUpdateAsync(currentVersion, BaseActivity.this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addUserToFirebase() {
        /*
        Add user to database
         */
        userRef.setValue(userModel);

    }

    private void showConnectionErrorDialog() {
        new AlertDialog.Builder(BaseActivity.this)
                .setTitle("Connection lost")
                .setMessage("Please check your connection and try again.")
                .setPositiveButton("Ok", null)
                .setCancelable(false).show();
    }

}

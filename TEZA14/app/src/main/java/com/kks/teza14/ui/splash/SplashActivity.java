package com.kks.teza14.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.facebook.login.Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kks.teza14.models.LoginModel;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.ui.login.LoginActivity;
import com.kks.teza14.ui.main.MainActivity;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.ui.pending_member.PendingMemberActivity;
import com.kks.teza14.util.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.NAME_PENDING;

/**
 * Created by kaungkhantsoe on 12/04/2020.
 **/
public class SplashActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @Inject
    @Named(NAME_PENDING)
    DatabaseReference pendingRef;

    private CountDownTimer countDownTimer;

    public static Intent getSplashIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        progressBar.setMax(3000);

        countDownTimer = new CountDownTimer(3000,10) {
            @Override
            public void onTick(long millisUntilFinished) {

                progressBar.setProgress(3000-(int)millisUntilFinished);
            }

            @Override
            public void onFinish() {

                if (sharePreferenceHelper.isLogin()) {
                    pendingRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                LoginModel model = snap.getValue(LoginModel.class);

                                if (model.getUid().equals(sharePreferenceHelper.getUserAppId())) {

                                    startActivity(MainActivity.getMainIntent(SplashActivity.this));

//                                    if (model.getApproved()) {
//                                        startActivity(MainActivity.getMainIntent(SplashActivity.this));
//                                    } else {
//                                        startActivity(PendingMemberActivity.getPendingMemberIntent(SplashActivity.this));
//                                    }
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                else
                    startActivity(LoginActivity.getLoginIntent(SplashActivity.this));

            }
        };

    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer.start();
    }
}

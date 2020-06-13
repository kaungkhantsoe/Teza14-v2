package com.kks.teza14.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kks.teza14.R;
import com.kks.teza14.common.BaseActivity;
import com.kks.teza14.models.LoginModel;
import com.kks.teza14.models.MemberModel;
import com.kks.teza14.ui.splash.SplashActivity;
import com.kks.teza14.util.SharePreferenceHelper;
import com.kks.teza14.viewmodels.ViewModelProviderFactory;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.kks.teza14.util.AppConstants.NAME_PENDING;

/**
 * Created by kaungkhantsoe on 22/05/2020.
 **/
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.cv_fb_login)
    CardView cvFbLogin;

    @BindView(R.id.cv_google_login)
    CardView cvGoogleLogin;

    @BindView(R.id.fb_login_button)
    LoginButton fbLoginButton;

    @BindView(R.id.google_login_button)
    SignInButton googleLoginButton;

    @BindView(R.id.tv_terms)
    AppCompatTextView tvTerms;

//    @BindView(R.id.cb_policy)
//    CheckBox cbPolicy;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @Inject
    @Named(NAME_PENDING)
    DatabaseReference pendingRef;

//    private GoogleApiClient mGoogleApiClient;

    private LoginModel model;

    private FirebaseAuth mAuth;

    private CallbackManager mCallbackManager;

    private int RC_SIGN_IN_GMAIL = 9001;

    private static final String KEY_PUBLIC_PROFILE = "public_profile";

    private static final String KEY_EMAIL = "email";

    private String fcmUserToken = "";

    private boolean needLink = false;

    private LoginViewModel viewModel;

    private AuthCredential tempFbCredential;

    private String tempFbGmail;

    private GoogleSignInClient mGoogleSignInClient;

    private ValueEventListener valueEventListener;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        init();

    }

    public static Intent getLoginIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    private void init() {
        viewModel = new ViewModelProvider(this, providerFactory).get(LoginViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        model = new LoginModel();

        cvFbLogin.setOnClickListener(this);
        cvGoogleLogin.setOnClickListener(this);
        tvTerms.setOnClickListener(this);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    LoginModel model = snap.getValue(LoginModel.class);
                    if (model.getUid().equals(model.getUid())) {
                        if (model.getApproved()) {
                            model.setApproved(true);
                            model.setPosition(model.getPosition());
                            model.setDbKey(model.getDbKey());
                            model.setApprovedBy(model.getApprovedBy());
                        } else {
                            model.setPosition(-1);
                            model.setApproved(false);
                        }
                        break;
                    }
                }

                pendingRef.child(model.getUid()).setValue(model);
                sharePreferenceHelper.setLogin(1, model.getUid(), model.getName(), "", model.getProfileUrl());

                startActivity(SplashActivity.getSplashIntent(LoginActivity.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        printKeyHash();

        getFirebaseTokenString();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        fbSetup();

        logoutAllCount();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cv_fb_login:
//                if (cbPolicy.isChecked()) {
//                    fbLoginButton.performClick();
//                } else {
//                    showToastMsg(getString(R.string.terms_and_condition_required));
//                }
                fbLoginButton.performClick();

                break;

            case R.id.cv_google_login:
//                if (cbPolicy.isChecked()) {
//                    signInWithGmail();
//                } else {
//                    showToastMsg(getString(R.string.terms_and_condition_required));
//                }
                signInWithGmail();

                break;

            case R.id.tv_terms:
//                startActivity(DefaultWebActivity.getDefaultWebIntent(LoginActivity.this,"https://info.yammobots.com/privacypolicy",
//                        "Terms & Policy"));

//                startActivity(DefaultWebActivity.getDefaultWebIntent(LoginActivity.this,URL_PRIVACY_POLICY, "Terms & Policy"));
                break;

            default:
                break;
        }

    }


    private void logoutAllCount() {
        logoutFacebook();
        logoutFirebase();
    }

    private void logoutFirebase() {
        FirebaseAuth.getInstance().signOut();
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                })
                .addOnFailureListener(e -> showToastMsg(e.getLocalizedMessage()));
    }

    private void logoutFacebook() {

//        FacebookSdk.sdkInitialize(this);
        LoginManager.getInstance().logOut();

    }

    /*
     * Gmail login
     * */
    private void signInWithGmail() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GMAIL);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        model.setGoogleID(acct.getId());
//        model.setPhoto(acct.getPhotoUrl().toString());
//        model.setUserName(acct.getDisplayName());
        model.setEmail(acct.getEmail());

        model.setName(acct.getDisplayName());
        model.setProfileUrl(acct.getPhotoUrl().toString());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        signInWithFirebase(credential);
    }


    /*
     * facebook login
     * */
    public void fbSetup() {

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        fbLoginButton.setPermissions(KEY_EMAIL, KEY_PUBLIC_PROFILE);
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                showToastMsg(error.toString());
            }
        });

    }

    private void handleFacebookAccessToken(com.facebook.AccessToken token) {

        GraphRequest request = GraphRequest.newMeRequest(
                token,
                (object, response) -> {

                    if (response.getError() != null) {
                        hideProgressDialog();
                    }

                    /*
                    set values in try catch block. Some accounts have errors in retrieving data
                     */
                    try {
                        String id = object.getString("id");
//                        model.setFacebookID(id);

                        String imageUrl = String.format("https://graph.facebook.com/%s/picture?type=large", id);
                        model.setProfileUrl(imageUrl);

                        String name = object.getString("name");
                        model.setName(name);

                        String email = object.getString("email");
                        model.setEmail(email);

//                      String birthday = object.getString("birthday"); // 01/31/1980 format

                        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                        signInWithFirebase(credential);

                    } catch (JSONException e) {

                        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                        signInWithFirebase(credential);

                        e.printStackTrace();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();


    }

    /*
     *  Link firebase accounts
     * */
    private void linkWithAnonymousAccount(final AuthCredential credential) {

        if (mAuth.getCurrentUser() != null)
            mAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            doMemberLogin(user);

                        } else {
                            hideProgressDialog();
                            Log.e(TAG, "onComplete: Authentication failed ", task.getException());
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthUserCollisionException) {
                                signInWithFirebase(credential);
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    /*
     *  Sign in firebase
     * */
    private void signInWithFirebase(AuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        FirebaseUser user = mAuth.getCurrentUser();

                        if (needLink) {
                            if (tempFbCredential != null)
                                if (user.getEmail().equals(tempFbGmail))
                                    linkWithAnonymousAccount(tempFbCredential);
                                else
                                    showToastMsg("Linking Fail: Emails not the same");
                        } else {
                            if (user != null)
                                doMemberLogin(user);
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                            if (credential.getProvider().equals("facebook.com")) {

                                 /*
                                Facebook account exist with same gmail logining in with gmail on firebase. Trying to link the accounts
                                 */
                                tempFbCredential = credential;
                                tempFbGmail = model.getEmail();

                                needLink = true;
                                showLinkingAlert();
                            } else {

                                fbLoginButton.performClick();
                            }

                        } else {

                            hideProgressDialog();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }

    /*
     * Firebase push messaging token
     * */
    private void getFirebaseTokenString() {
        showProgressDialog();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnFailureListener(e -> {
                    Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    hideProgressDialog();
                })
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (task.getResult() != null) {
                            final String firebaseToken = task.getResult().getToken();

                            if (firebaseToken.equals("BLACKLISTED")) {
                                Toast.makeText(LoginActivity.this, "Error retrieving user token", Toast.LENGTH_LONG).show();
                            } else {
                                fcmUserToken = firebaseToken;
                            }
                            hideProgressDialog();
                        }
                    }
                });
    }


    /*
     * Send member data
     * */
    private void doMemberLogin(FirebaseUser user) {

        model.setUid(user.getUid());

        pendingRef.addListenerForSingleValueEvent(valueEventListener);

//        model.setUserToken(fcmUserToken);

//        viewModel.setMemberModel(model);
//        viewModel.observePostLogin()
//                .observe(this, new Observer<Resource<MemberModel>>() {
//                    @Override
//                    public void onChanged(Resource<MemberModel> memberModelResource) {
//
//                        switch (memberModelResource.status) {
//                            case ERROR:
//                                hideProgressDialog();
//                                showInternetErrorAlertDialog();
//                                break;
//                            case SUCCESS:
//                                hideProgressDialog();
//                                MemberModel memberModel = memberModelResource.data;
//
//                                insertIntoDB(memberModel);
//
//                                sharePreferenceHelper.setLogin(memberModel.getId(), memberModel.getUserAppID(),memberModel.getName(), memberModel.getUserToken());
//
//                                startActivity(MainActivity.getMainIntent(LoginActivity.this));
//                                finish();
//                                showToastMsg("Login Success");
//
//                                break;
//                            case LOADING:
//                                showProgressDialog();
//                                break;
//                        }
//                    }
//                });
    }


    private void insertIntoDB(MemberModel model) {

    }


    private void showInternetErrorAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Internet connection problem!")
                .setCancelable(false)
                .setPositiveButton(
                        android.R.string.ok,
                        (dialog, whichButton) -> dialog.dismiss()).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_GMAIL) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null)
                    firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        showInternetErrorAlertDialog();
//    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            doMemberLogin(currentUser);
        }
    }

    private void showLinkingAlert() {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(LoginActivity.this.getResources().getString(R.string.linking_account) + " " + tempFbGmail + LoginActivity.this.getResources().getString(R.string.please_login_with))
                .setCancelable(false)
                .setPositiveButton("Ok",
                        (dialog, whichButton) -> {
                            signInWithGmail();
                        })
                .setNegativeButton("Cancel",
                        (dialog, whichButton) -> dialog.dismiss()).show();
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pendingRef.removeEventListener(valueEventListener);
    }
}

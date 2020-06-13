package com.kks.teza14.di;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kks.teza14.R;
import com.kks.teza14.util.NotificationHelper;
import com.kks.teza14.util.SharePreferenceHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kks.teza14.util.AppConstants.*;


@Module
public class AppModule {

    @Singleton
    @Provides
    static SharePreferenceHelper provideSharePreferenceHelperInstance(Application application) {
        return new SharePreferenceHelper(application);
    }

//    @Singleton
//    @Provides
//    static Retrofit provideRetrofitInstance() {
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .centerCrop();
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static NotificationHelper provideNotificationHelper(Application application) {
        return new NotificationHelper(application);
    }

    @Singleton
    @Provides
    static FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    @Named(NAME_MEMBER)
    static DatabaseReference provideMemberReference(FirebaseDatabase firebaseDatabase) {
        return firebaseDatabase.getReference("members");
    }

    @Singleton
    @Provides
    @Named(NAME_PENDING)
    static DatabaseReference providePendingReference(FirebaseDatabase firebaseDatabase) {
        return firebaseDatabase.getReference("PendingUsers");
    }

    @Singleton
    @Provides
    @Named(NAME_POSTS)
    static DatabaseReference providePostReference(FirebaseDatabase firebaseDatabase) {
        return firebaseDatabase.getReference("Posts");
    }

//    @Singleton
//    @Provides
//    static UpdateUserStatusApi provideUpdateUserStatusApi(Retrofit retrofit) {
//        return retrofit.create(UpdateUserStatusApi.class);
//    }
}

package com.kks.teza14.di;
import com.kks.teza14.di.add_event.AddEventScope;
import com.kks.teza14.di.add_event.AddEventViewModelModule;
import com.kks.teza14.di.edit_member.EditMemberModule;
import com.kks.teza14.di.edit_member.EditMemberScope;
import com.kks.teza14.di.edit_member.EditMemberViewModelModule;
import com.kks.teza14.di.login.LoginModule;
import com.kks.teza14.di.login.LoginScope;
import com.kks.teza14.di.login.LoginViewModelModule;
import com.kks.teza14.di.main.MainFragmentBuildersModule;
import com.kks.teza14.di.main.MainModule;
import com.kks.teza14.di.main.MainScope;
import com.kks.teza14.di.main.MainViewModelModule;
import com.kks.teza14.di.member_detail.MemberDetailModule;
import com.kks.teza14.di.member_detail.MemberDetailScope;
import com.kks.teza14.di.member_detail.MemberDetailViewModelModule;
import com.kks.teza14.di.pending_member.PendingMemberScope;
import com.kks.teza14.di.pending_member.PendingMemberViewModelModule;
import com.kks.teza14.di.search.SearchModule;
import com.kks.teza14.di.search.SearchScope;
import com.kks.teza14.di.search.SearchViewModelModule;
import com.kks.teza14.di.setup_profile_dialog.SetupProfileScope;
import com.kks.teza14.di.setup_profile_dialog.SetupProfileViewModelModule;
import com.kks.teza14.di.splash.SplashScope;
import com.kks.teza14.di.splash.SplashViewModelModule;
import com.kks.teza14.ui.add_event.AddEventActivity;
import com.kks.teza14.ui.edit_member.EditMemberActivity;
import com.kks.teza14.ui.login.LoginActivity;
import com.kks.teza14.ui.main.MainActivity;
import com.kks.teza14.ui.member_detail.MemberDetailActivity;
import com.kks.teza14.ui.pending_member.PendingMemberActivity;
import com.kks.teza14.ui.search.SearchActivity;
import com.kks.teza14.ui.setup_profile_dialog.SetupProfileDialogActivity;
import com.kks.teza14.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class,
                    MainViewModelModule.class,
                    MainModule.class,
            }
    )
    abstract MainActivity contributeMainActivity();

    @LoginScope
    @ContributesAndroidInjector(
            modules = {
                    LoginModule.class,
                    LoginViewModelModule.class
            }
    )
    abstract LoginActivity contributeLoginActivity();

    @SplashScope
    @ContributesAndroidInjector(
            modules = {
                    SplashViewModelModule.class
            }
    )
    abstract SplashActivity contributeSplashActivity();

    @MemberDetailScope
    @ContributesAndroidInjector(
            modules = {
                    MemberDetailModule.class,
                    MemberDetailViewModelModule.class,
            }
    )
    abstract MemberDetailActivity contributeMemberDetailActivity();

    @EditMemberScope
    @ContributesAndroidInjector(
            modules = {
                    EditMemberModule.class,
                    EditMemberViewModelModule.class,
            }
    )
    abstract EditMemberActivity contributeEditeMemberActivity();

    @SearchScope
    @ContributesAndroidInjector(
            modules = {
                    SearchModule.class,
                    SearchViewModelModule.class
            }
    )
    abstract SearchActivity contributeSearchActivity();

    @PendingMemberScope
    @ContributesAndroidInjector(
            modules = {
                    PendingMemberViewModelModule.class,
            }
    )
    abstract PendingMemberActivity contributePendingMemberActivity();

    @SetupProfileScope
    @ContributesAndroidInjector(
            modules = {
                    SetupProfileViewModelModule.class
            }
    )
    abstract SetupProfileDialogActivity contributeSetupProfileActivity();

    @AddEventScope
    @ContributesAndroidInjector(
            modules = {
                    AddEventViewModelModule.class
            }
    )
    abstract AddEventActivity contributeAddEventActivity();
}

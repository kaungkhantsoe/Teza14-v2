package com.kks.teza14.ui.login;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

/**
 * Created by kaungkhantsoe on 22/05/2020.
 **/
public class LoginViewModel extends ViewModel {

    @Inject
    public LoginViewModel() {

    }
//    private LoginRepository loginRepository;
//
//    private MemberModel memberModel;
//
//    private MediatorLiveData<Resource<MemberModel>> memberResource;
//
//    public MemberModel getMemberModel() {
//        return memberModel;
//    }
//
//    public void setMemberModel(MemberModel memberModel) {
//        this.memberModel = memberModel;
//    }
//
//    @Inject
//    public LoginViewModel(LoginRepository loginRepository) {
//        this.loginRepository = loginRepository;
//    }
//
//    public MediatorLiveData<Resource<MemberModel>> observePostLogin() {
//        memberResource = new MediatorLiveData<>();
//        memberResource.setValue(Resource.loading(null));
//
//        LiveData<Resource<MemberModel>> source = LiveDataReactiveStreams.fromPublisher(
//                loginRepository.postLogin(memberModel)
//        );
//
//        memberResource.addSource(source, new Observer<Resource<MemberModel>>() {
//            @Override
//            public void onChanged(Resource<MemberModel> memberModelResource) {
//                memberResource.setValue(memberModelResource);
//                memberResource.removeSource(source);
//            }
//        });
//
//        return memberResource;
//    }
}

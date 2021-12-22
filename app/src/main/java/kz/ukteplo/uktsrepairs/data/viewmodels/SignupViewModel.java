package kz.ukteplo.uktsrepairs.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import kz.ukteplo.uktsrepairs.data.api.BaseResponse;
import kz.ukteplo.uktsrepairs.data.api.RegUserResponse;
import kz.ukteplo.uktsrepairs.data.repositories.SignupRepository;

public class SignupViewModel extends AndroidViewModel {
    private final SignupRepository mRepository;
    private LiveData<BaseResponse> liveData;

    public SignupViewModel(Application application) {
        super(application);
        mRepository = new SignupRepository(application);
    }

    public void initData(String username, String password, String email, String lang) {
        liveData = mRepository.signUp(username, password, email, lang);
    }

    public LiveData<BaseResponse> signUp() {
        return liveData;
    }
}

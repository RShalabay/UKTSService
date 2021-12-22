package kz.ukteplo.uktsrepairs.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import kz.ukteplo.uktsrepairs.data.api.AuthResponse;
import kz.ukteplo.uktsrepairs.data.repositories.SigninRepository;

public class SigninViewModel extends AndroidViewModel {
    private final SigninRepository mRepository;
    private LiveData<AuthResponse> liveData;

    public SigninViewModel(Application application) {
        super(application);
        mRepository = new SigninRepository(application);
    }

    public void initData(String login, String pass) {
        liveData = mRepository.signIn(login, pass);
    }

    public LiveData<AuthResponse> signIn() {
        return liveData;
    }
}

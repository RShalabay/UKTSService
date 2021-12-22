package kz.ukteplo.uktsrepairs.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kz.ukteplo.uktsrepairs.data.api.AuthRequest;
import kz.ukteplo.uktsrepairs.data.api.AuthResponse;
import kz.ukteplo.uktsrepairs.data.api.UKTSApiService;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.RetrofitManager;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninRepository {
    private final RetrofitManager mRetrofitManager;
    private final MutableLiveData<AuthResponse> mResponse = new MutableLiveData<>();
    private final UKTSApiService mApi;
    private final ConfigManager mConfigManager;

    public SigninRepository(Application application) {
        mConfigManager = ((UKTSApplication) application).getConfigManager();
        mRetrofitManager = RetrofitManager.getInstance(mConfigManager);
        mApi = mRetrofitManager.getUKTSApi();
    }

    public LiveData<AuthResponse> signIn(String login, String pass) {
        mApi.login(new AuthRequest(mConfigManager.getApikey(),
                login,
                pass,
                mRetrofitManager.getToken(),
                mConfigManager.getDeviceid(),
                mConfigManager.getVersion()))
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.isSuccessful()) {
                            mResponse.postValue(response.body());
                            if (response.body().isSuccess()) {
                                mRetrofitManager.setSession(response.body().getSession());
                                mConfigManager.setRole(response.body().getRole());
                                mConfigManager.saveRole();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        mResponse.postValue(new AuthResponse(false, (float) 0.0, "", t.getLocalizedMessage(), "0", "0"));
                    }
                });
        return mResponse;
    }
}

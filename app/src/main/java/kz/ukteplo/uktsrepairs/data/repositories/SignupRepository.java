package kz.ukteplo.uktsrepairs.data.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import kz.ukteplo.uktsrepairs.data.api.BaseResponse;
import kz.ukteplo.uktsrepairs.data.api.RegUserRequest;
import kz.ukteplo.uktsrepairs.data.api.RegUserResponse;
import kz.ukteplo.uktsrepairs.data.api.UKTSApiService;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.RetrofitManager;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupRepository {
    private final RetrofitManager mRetrofitManager;
    private final MutableLiveData<BaseResponse> mResponse = new MutableLiveData<>();
    private final UKTSApiService mApi;
    private final ConfigManager mConfigManager;

    public SignupRepository(Application application) {
        mConfigManager = ((UKTSApplication) application).getConfigManager();
        mRetrofitManager = RetrofitManager.getInstance(mConfigManager);
        mApi = mRetrofitManager.getUKTSApi();
    }

    public MutableLiveData<BaseResponse> signUp(String username, String password, String email, String lang) {
        mApi.regUser(new RegUserRequest(mConfigManager.getApikey(),
                username,
                password,
                password,
                email,
                lang,
                mConfigManager.getDeviceid()))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) mResponse.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        mResponse.postValue(new BaseResponse(false, (float) 0.0, "", t.getLocalizedMessage()));
                    }
                });
        return mResponse;
    }

}

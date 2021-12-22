package kz.ukteplo.uktsrepairs.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kz.ukteplo.uktsrepairs.data.api.BaseRequest;
import kz.ukteplo.uktsrepairs.data.api.GetRepairsListResponse;
import kz.ukteplo.uktsrepairs.data.api.UKTSApiService;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.RetrofitManager;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepairsRepository {
    private final RetrofitManager mRetrofitManager;
    private final UKTSApiService mApi;
    private final ConfigManager mConfigManager;
    private final MutableLiveData<GetRepairsListResponse> mResponse = new MutableLiveData<>();

    public RepairsRepository(Application application) {
        mConfigManager = ((UKTSApplication) application).getConfigManager();
        mRetrofitManager = RetrofitManager.getInstance(mConfigManager);
        mApi = mRetrofitManager.getUKTSApi();
    }

    public LiveData<GetRepairsListResponse> getRepairs() {
        mApi.getRepairs(new BaseRequest(mConfigManager.getApikey(), mRetrofitManager.getSession()))
                .enqueue(new Callback<GetRepairsListResponse>() {
                    @Override
                    public void onResponse(Call<GetRepairsListResponse> call, Response<GetRepairsListResponse> response) {
                        if (response.isSuccessful()) {
                            mResponse.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetRepairsListResponse> call, Throwable t) {

                    }
                });
        return mResponse;
    }
}

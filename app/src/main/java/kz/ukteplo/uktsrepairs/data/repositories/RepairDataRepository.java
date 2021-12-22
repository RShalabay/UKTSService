package kz.ukteplo.uktsrepairs.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kz.ukteplo.uktsrepairs.data.api.AddNewRepairRequest;
import kz.ukteplo.uktsrepairs.data.api.BaseResponse;
import kz.ukteplo.uktsrepairs.data.api.GetRepairDataRequest;
import kz.ukteplo.uktsrepairs.data.api.GetRepairDataResponse;
import kz.ukteplo.uktsrepairs.data.api.SetRepairApproveStatusRequest;
import kz.ukteplo.uktsrepairs.data.api.SetRepairPlanIdRequest;
import kz.ukteplo.uktsrepairs.data.api.UKTSApiService;
import kz.ukteplo.uktsrepairs.data.models.RepairData;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.RetrofitManager;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepairDataRepository {
    private final RetrofitManager mRetrofitManager;
    private final MutableLiveData<GetRepairDataResponse> mResponseGetData = new MutableLiveData<>();
    private final MutableLiveData<BaseResponse> mResponseSetData = new MutableLiveData<>();
    private final MutableLiveData<BaseResponse> mResponseSetApprove = new MutableLiveData<>();
    private final MutableLiveData<BaseResponse> mResponseSetPlanId = new MutableLiveData<>();
    private final MutableLiveData<BaseResponse> mResponseDeleteRepair = new MutableLiveData<>();
    private final UKTSApiService mApi;
    private final ConfigManager mConfigManager;

    public RepairDataRepository(Application application) {
        mConfigManager = ((UKTSApplication) application).getConfigManager();
        mRetrofitManager = RetrofitManager.getInstance(mConfigManager);
        mApi = mRetrofitManager.getUKTSApi();
    }

    public LiveData<GetRepairDataResponse> getRepairData(String id) {
        mApi.getRepairData(new GetRepairDataRequest(mConfigManager.getApikey(), mRetrofitManager.getSession(), id))
                .enqueue(new Callback<GetRepairDataResponse>() {
                    @Override
                    public void onResponse(Call<GetRepairDataResponse> call, Response<GetRepairDataResponse> response) {
                        if (response.isSuccessful()) {
                            mResponseGetData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetRepairDataResponse> call, Throwable t) {

                    }
                });
        return mResponseGetData;
    }

    public LiveData<BaseResponse> addNewRepair(RepairData repairData) {
        mApi.addNewRepair(new AddNewRepairRequest(mConfigManager.getApikey(), mRetrofitManager.getSession(), repairData))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            mResponseSetData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.e("addNewRepair", t.getLocalizedMessage());
                    }
                });
        return mResponseSetData;
    }

    public LiveData<BaseResponse> setRepairData(RepairData repairData) {
        mApi.setRepairData(new AddNewRepairRequest(mConfigManager.getApikey(), mRetrofitManager.getSession(), repairData))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            mResponseSetData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.e("setRepairData", t.getLocalizedMessage());
                    }
                });
        return mResponseSetData;
    }

    public LiveData<BaseResponse> setApprove(String id, String status, String comment) {
        mApi.setRepairApproveStatus(new SetRepairApproveStatusRequest(mConfigManager.getApikey(), mRetrofitManager.getSession(), id, status, comment))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            mResponseSetApprove.postValue(response.body());
                            Log.e("setApprove", response.body().getSuccess().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Log.e("setApprove", t.getLocalizedMessage());
                    }
                });
        return mResponseSetApprove;
    }

    public LiveData<BaseResponse> setPlanId(String repairId, String planId) {
        mApi.setRepairPlanId(new SetRepairPlanIdRequest(mConfigManager.getApikey(), mRetrofitManager.getSession(), repairId, planId))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        mResponseSetPlanId.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
        return mResponseSetPlanId;
    }

    public LiveData<BaseResponse> deleteRepair(String repairId) {
        mApi.deleteRepair(new GetRepairDataRequest(mConfigManager.getApikey(), mRetrofitManager.getSession(), repairId))
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        mResponseDeleteRepair.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
        return mResponseDeleteRepair;
    }
}

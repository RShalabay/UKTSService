package kz.ukteplo.uktsrepairs.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.DAO.DistrictDAO;
import kz.ukteplo.uktsrepairs.data.api.BaseRequest;
import kz.ukteplo.uktsrepairs.data.api.GetDistrictsResponse;
import kz.ukteplo.uktsrepairs.data.api.UKTSApiService;
import kz.ukteplo.uktsrepairs.data.models.District;
import kz.ukteplo.uktsrepairs.utils.AppDatabase;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.RetrofitManager;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictsRepository {
    private final RetrofitManager mRetrofitManager;
    private final LiveData<List<District>> allDistricks;
    private final UKTSApiService mApi;
    private final ConfigManager mConfigManager;
    private final DistrictDAO dao;
    //private final MutableLiveData<GetDistrictsResponse> mResponse = new MutableLiveData<>();

    public DistrictsRepository(Application application) {
        mConfigManager = ((UKTSApplication) application).getConfigManager();
        mRetrofitManager = RetrofitManager.getInstance(mConfigManager);
        mApi = mRetrofitManager.getUKTSApi();
        AppDatabase db = AppDatabase.getDatabase(application);
        dao = db.districtDAO();
        allDistricks = dao.getAll();
    }

    public LiveData<List<District>> getAllDistricts() { return allDistricks; }

    public LiveData<District> getDistrictById(String id) {
        return dao.getById(Integer.parseInt(id));
    }

    public void downloadDistricts() {
        mApi.getDistricts(new BaseRequest(mConfigManager.getApikey(), mRetrofitManager.getSession()))
                .enqueue(new Callback<GetDistrictsResponse>() {
                    @Override
                    public void onResponse(Call<GetDistrictsResponse> call, Response<GetDistrictsResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                for (District district : response.body().getDistricts()) {
                                    addDistrict(district);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDistrictsResponse> call, Throwable t) {

                    }
                });
    }

    public void addDistrict(District district) { new insertAsyncTask(dao).execute(district); }

    private static class insertAsyncTask extends AsyncTask<District, Void, Void> {
        private final DistrictDAO mDao;

        public insertAsyncTask(DistrictDAO districtDAO) { mDao = districtDAO; }


        @Override
        protected Void doInBackground(District... districts) {
            mDao.insert(districts[0]);
            return null;
        }
    }

}

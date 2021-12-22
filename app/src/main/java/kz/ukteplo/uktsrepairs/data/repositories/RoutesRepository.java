package kz.ukteplo.uktsrepairs.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.DAO.RouteDAO;
import kz.ukteplo.uktsrepairs.data.api.BaseRequest;
import kz.ukteplo.uktsrepairs.data.api.GetRoutesResponse;
import kz.ukteplo.uktsrepairs.data.api.UKTSApiService;
import kz.ukteplo.uktsrepairs.data.models.Route;
import kz.ukteplo.uktsrepairs.utils.AppDatabase;
import kz.ukteplo.uktsrepairs.utils.ConfigManager;
import kz.ukteplo.uktsrepairs.utils.RetrofitManager;
import kz.ukteplo.uktsrepairs.utils.UKTSApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutesRepository {
    private final RetrofitManager mRetrofitManager;
    private LiveData<List<Route>> allRoutes;
    private final UKTSApiService mApi;
    private final ConfigManager mConfigManager;
    private final RouteDAO dao;
    //private final MutableLiveData<GetRoutesResponse> mResponse = new MutableLiveData<>();

    public RoutesRepository(Application application) {
        mConfigManager = ((UKTSApplication) application).getConfigManager();
        mRetrofitManager = RetrofitManager.getInstance(mConfigManager);
        mApi = mRetrofitManager.getUKTSApi();
        AppDatabase db = AppDatabase.getDatabase(application);
        dao = db.routeDAO();
    }

    public LiveData<Route> getRouteById(Integer id) {
        return dao.getById(id);
    }

    public LiveData<List<Route>> getAllRoutes(String districtId) { return dao.getAll(Integer.parseInt(districtId)); }

    public void downloadRoutes() {
        mApi.getRoutes(new BaseRequest(mConfigManager.getApikey(), mRetrofitManager.getSession()))
                .enqueue(new Callback<GetRoutesResponse>() {
                    @Override
                    public void onResponse(Call<GetRoutesResponse> call, Response<GetRoutesResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                for (Route route : response.body().getRoutes()) {
                                    addRoute(route);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetRoutesResponse> call, Throwable t) {

                    }
                });
    }

    public void addRoute(Route route) { new insertAsyncTask(dao).execute(route); }

    private static class insertAsyncTask extends AsyncTask<Route, Void, Void> {
        private final RouteDAO mDao;

        public insertAsyncTask(RouteDAO routeDAO) { mDao = routeDAO; }


        @Override
        protected Void doInBackground(Route... routes) {
            mDao.insert(routes[0]);
            return null;
        }
    }

}

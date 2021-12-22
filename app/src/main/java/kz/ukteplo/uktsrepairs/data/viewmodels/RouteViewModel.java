package kz.ukteplo.uktsrepairs.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.Route;
import kz.ukteplo.uktsrepairs.data.repositories.RoutesRepository;

public class RouteViewModel extends AndroidViewModel {
    private RoutesRepository repository;
    private LiveData<List<Route>> allRoutes;

    public RouteViewModel(Application application) {
        super(application);
        repository = new RoutesRepository(getApplication());
    }

    public void initData(String districtId) {
        allRoutes = repository.getAllRoutes(districtId);
    }

    public void downloadRoutes() {
        repository.downloadRoutes();
    }

    public LiveData<List<Route>> getAll() {
        return allRoutes;
    }

    public LiveData<Route> getRouteById(Integer id) {
        return repository.getRouteById(id);
    }
}

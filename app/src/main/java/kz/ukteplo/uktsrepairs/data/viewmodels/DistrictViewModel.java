package kz.ukteplo.uktsrepairs.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.District;
import kz.ukteplo.uktsrepairs.data.repositories.DistrictsRepository;

public class DistrictViewModel extends AndroidViewModel {
    private final DistrictsRepository repo;
    private LiveData<List<District>> allDistricts;

    public DistrictViewModel(Application application) {
        super(application);
        repo = new DistrictsRepository(application);
    }

    public void initData() {
        allDistricts = repo.getAllDistricts();
    }

    public LiveData<List<District>> getAll() { return allDistricts; }

    public LiveData<District> getDistrictById(String id) {
        return repo.getDistrictById(id);
    }

    public void downloadDistricts() {
        repo.downloadDistricts();
    }
}

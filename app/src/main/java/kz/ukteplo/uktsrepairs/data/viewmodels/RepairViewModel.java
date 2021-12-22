package kz.ukteplo.uktsrepairs.data.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import kz.ukteplo.uktsrepairs.data.api.GetRepairsListResponse;
import kz.ukteplo.uktsrepairs.data.repositories.RepairsRepository;

public class RepairViewModel extends AndroidViewModel {
    private final RepairsRepository repo;
    private LiveData<GetRepairsListResponse> allRepairs;

    public RepairViewModel(Application application) {
        super(application);
        repo = new RepairsRepository(application);
    }

    public void initData() {
        allRepairs = repo.getRepairs();
    }

    public LiveData<GetRepairsListResponse> getAll() {
        return allRepairs;
    }

}

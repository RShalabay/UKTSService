package kz.ukteplo.uktsrepairs.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import kz.ukteplo.uktsrepairs.data.api.BaseResponse;
import kz.ukteplo.uktsrepairs.data.api.GetRepairDataResponse;
import kz.ukteplo.uktsrepairs.data.models.RepairData;
import kz.ukteplo.uktsrepairs.data.repositories.RepairDataRepository;

public class RepairDataViewModel extends AndroidViewModel {
    private final RepairDataRepository repo;
    private LiveData<GetRepairDataResponse> repairData;
    private LiveData<BaseResponse> addResult;

    public RepairDataViewModel(@NonNull Application application) {
        super(application);
        repo = new RepairDataRepository(application);
    }

    public void initData(String id) {
        repairData = repo.getRepairData(id);
    }

    public LiveData<GetRepairDataResponse> getRepairData() {
        return repairData;
    }

    public LiveData<BaseResponse> addNewRepair(RepairData repairData) {
        addResult = repo.addNewRepair(repairData);
        return addResult;
    }

    public LiveData<BaseResponse> setRepairData(RepairData repairData) {
        return repo.setRepairData(repairData);
    }

    public LiveData<BaseResponse> setApprove(String id, String status, String comment) {
        return repo.setApprove(id, status, comment);
    }

    public LiveData<BaseResponse> setPlanId(String repairId, String planId) {
        return repo.setPlanId(repairId, planId);
    }

    public LiveData<BaseResponse> deleteRepair(String repairId) {
        return repo.deleteRepair(repairId);
    }

}

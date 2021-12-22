package kz.ukteplo.uktsrepairs.data.api;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.Repair;

public class GetRepairsListResponse extends BaseResponse {
    private List<Repair> repairs;


    public GetRepairsListResponse(Boolean success, Float errorCode, String errorText, String errorDescription) {
        super(success, errorCode, errorText, errorDescription);
    }

    public List<Repair> getRepairs() {
        return repairs;
    }

    public void setRepairs(List<Repair> repairs) {
        this.repairs = repairs;
    }
}

package kz.ukteplo.uktsrepairs.data.api;

import java.util.List;

import kz.ukteplo.uktsrepairs.data.models.RepairData;

public class GetRepairDataResponse extends BaseResponse {
    private List<RepairData> repairParams;

    public GetRepairDataResponse(Boolean success, Float errorCode, String errorText, String errorDescription) {
        super(success, errorCode, errorText, errorDescription);
    }

    public List<RepairData> getRepairParams() {
        return repairParams;
    }

    public void setRepairParams(List<RepairData> repairParams) {
        this.repairParams = repairParams;
    }
}

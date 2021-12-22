package kz.ukteplo.uktsrepairs.data.api;

import kz.ukteplo.uktsrepairs.data.models.RepairData;

public class AddNewRepairRequest extends BaseRequest {
    private RepairData repairParams;

    public AddNewRepairRequest(String apikey, String session, RepairData  repairData) {
        super(apikey, session);
        this.repairParams = repairData;
    }

    public RepairData getRepairParams() {
        return repairParams;
    }

    public void setRepairParams(RepairData repairParams) {
        this.repairParams = repairParams;
    }
}

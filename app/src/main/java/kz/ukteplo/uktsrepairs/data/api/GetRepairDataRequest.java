package kz.ukteplo.uktsrepairs.data.api;

public class GetRepairDataRequest extends BaseRequest {
    private String repairId;

    public GetRepairDataRequest(String apikey, String session, String repairId) {
        super(apikey, session);
        this.repairId = repairId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }
}

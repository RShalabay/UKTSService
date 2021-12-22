package kz.ukteplo.uktsrepairs.data.api;

public class SetRepairPlanIdRequest extends BaseRequest {
    private String repairId;
    private String planId;

    public SetRepairPlanIdRequest(String apikey, String session, String repairId, String planId) {
        super(apikey, session);
        this.repairId = repairId;
        this.planId = planId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}

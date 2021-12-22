package kz.ukteplo.uktsrepairs.data.models;

public class RepairData {
    private String id;
    private String planId;
    private String districtId;
    private String routeId;
    private String beginDate;
    private String endDate;
    private String text;

    public RepairData(String id, String planId, String districtId, String routeId, String beginDate, String endDate, String text) {
        this.id = id;
        this.planId = planId;
        this.districtId = districtId;
        this.routeId = routeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.text = text;
    }

    public RepairData() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

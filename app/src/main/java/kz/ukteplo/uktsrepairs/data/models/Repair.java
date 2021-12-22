package kz.ukteplo.uktsrepairs.data.models;

public class Repair {
    private String id;
    private String planId;
    private String districtId;
    private String routeId;
    private String beginDate;
    private String endDate;
    private String text;
    private String employee;
    private String employeeName;
    private String isApproved;
    private String comment;
    private String isCompleted;
    private String isCancelled;

    public Repair(String id,
                  String planId,
                  String districtId,
                  String routeId,
                  String beginDate,
                  String endDate,
                  String text,
                  String employee,
                  String employeeName,
                  String isApproved,
                  String comment,
                  String isCompleted,
                  String isCancelled) {
        this.id = id;
        this.planId = planId;
        this.districtId = districtId;
        this.routeId = routeId;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.text = text;
        this.employee = employee;
        this.employeeName = employeeName;
        this.isApproved = isApproved;
        this.comment = comment;
        this.isCompleted = isCompleted;
        this.isCancelled = isCancelled;
    }

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

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIsApproved() {
        String s = "";
        switch (isApproved) {
            case "":
                s = "Новый ремонт";
                break;
            case "Y":
                s = "Согласовано";
                break;
            case "N":
                s = "Отказано";
                break;
        }
        if (getIsCancelled().equals("Y")) s = "Отменен";
        return s;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(String isCancelled) {
        this.isCancelled = isCancelled;
    }
}

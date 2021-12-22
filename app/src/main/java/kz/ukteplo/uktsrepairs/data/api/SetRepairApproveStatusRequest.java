package kz.ukteplo.uktsrepairs.data.api;

public class SetRepairApproveStatusRequest extends BaseRequest {
    private String repairId;
    private String isApproved;
    private String comment;

    public SetRepairApproveStatusRequest(String apikey, String session, String id, String idApproved, String comment) {
        super(apikey, session);
        this.repairId = id;
        this.isApproved = idApproved;
        this.comment = comment;
    }

    public String getId() {
        return repairId;
    }

    public void setId(String id) {
        this.repairId = id;
    }

    public String getIdApproved() {
        return isApproved;
    }

    public void setIdApproved(String idApproved) {
        this.isApproved = idApproved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

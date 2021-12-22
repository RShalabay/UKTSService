package kz.ukteplo.uktsrepairs.data.api;

public class RegUserResponse {
    private Boolean success;
    private String errorCode;
    private String error;

    public RegUserResponse(Boolean success, String errorCode, String error) {
        this.success = success;
        this.errorCode = errorCode;
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

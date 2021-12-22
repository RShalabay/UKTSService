package kz.ukteplo.uktsrepairs.data.api;

public class BaseResponse {
    private Boolean success;
    private Float errorCode;
    private String errorText;
    private String errorDescription;

    public BaseResponse(Boolean success, Float errorCode, String errorText, String errorDescription) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorText = errorText;
        this.errorDescription = errorDescription;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Float getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Float errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}

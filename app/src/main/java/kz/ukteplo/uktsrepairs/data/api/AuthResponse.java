package kz.ukteplo.uktsrepairs.data.api;

public class AuthResponse extends BaseResponse {
    private String session;
    private String role;

    public AuthResponse(Boolean success, Float errorCode, String errorText, String errorDescription, String session, String role) {
        super(success, errorCode, errorText, errorDescription);
        this.session = session;
        this.role = role;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isSuccess() {
        return super.getSuccess();
    }
}

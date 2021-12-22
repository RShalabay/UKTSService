package kz.ukteplo.uktsrepairs.data.api;

public class RegUserRequest {
    private final String apikey;
    private String username;
    private String password;
    private String password2;
    private String email;
    private String lang;
    private String deviceId;

    public RegUserRequest(String apikey, String username, String password, String password2, String email, String language, String deviceId) {
        this.apikey = apikey;
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.email = email;
        this.lang = language;
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

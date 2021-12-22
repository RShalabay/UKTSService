package kz.ukteplo.uktsrepairs.data.api;

public class BaseRequest {
    private String apikey;
    private String session;

    public BaseRequest(String apikey, String session) {
        this.apikey = apikey;
        this.session = session;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}

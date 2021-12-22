package kz.ukteplo.uktsrepairs.data.models;

public class User {
    private String login;
    private String password;
    private String email;
    private String lang;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String email, String lang) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.lang = lang;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

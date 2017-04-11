package models;

public class User {
    private transient String password;
    private String userName, apiKey;

    public User(final String userName, final String password) {
        this(userName, password, null);
    }

    private User(final String userName, final String password, final String apiKey) {
        this.apiKey = apiKey;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }
}

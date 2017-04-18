package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Optional;
import java.util.UUID;

@Entity
public class User extends Model {
    private static Finder<String, User> find = new Finder<>(User.class);

    @Id
    private String userName;
    @Constraints.Required
    private String password;

    @Constraints.Required
    private UUID apiKey;

    public User(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
        this.apiKey = UUID.randomUUID();
    }

    public static Optional<User> getUser(final UUID apiKey) {
        return Optional.ofNullable(find.where().eq("apiKey", apiKey).findUnique());
    }

    public static Optional<User> getUser(final String userName, final String password) {
        return Optional.ofNullable(find.where()
                .eq("userName", userName)
                .eq("password", password)
                .findUnique()
        );
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getApiKey() {
        return apiKey;
    }
}

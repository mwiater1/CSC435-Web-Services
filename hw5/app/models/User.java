package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

@Entity
public class User extends Model {
    private static Finder<String, User> find = new Finder<>(User.class);

    @Id
    private String userName;

    @Constraints.Required
    private UUID apiKey;

    @Constraints.Required
    private String password;

    @Constraints.Required
    private List<Preference> preferences;

    public User(final String userName, final String password) {
        this.apiKey = UUID.randomUUID();
        this.userName = userName;
        this.password = password;
        this.preferences = new ArrayList<>();
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

    public UUID getApiKey() {
        return apiKey;
    }

    public Preference getPreference(final long articleId) {
        return preferences.stream().filter(p -> p.getArticleId() == articleId).findFirst().orElse(new Preference(articleId));
    }

    public List<Preference> getPreferences() {
        return Collections.unmodifiableList(preferences);
    }

    public static Optional<User> getUser(final UUID apiKey) {
        return Optional.ofNullable(find.where().eq("apiKey",apiKey).findUnique());
    }

    public static Optional<User> getUser(final String userName, final String password) {
        return Optional.ofNullable(find.where().eq("userName",userName).eq("password",password).findUnique());
    }
}

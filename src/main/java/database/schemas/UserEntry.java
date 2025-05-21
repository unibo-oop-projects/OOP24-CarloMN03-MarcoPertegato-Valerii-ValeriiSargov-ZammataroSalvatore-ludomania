package database.schemas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import database.schemas.api.Entry;

public class UserEntry implements Entry {
    private final String username;
    private final String password;

    @JsonCreator
    public UserEntry(@JsonProperty("username") final String username, @JsonProperty("password") final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public final String identifier() {
        return this.username;
    }

    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }

}

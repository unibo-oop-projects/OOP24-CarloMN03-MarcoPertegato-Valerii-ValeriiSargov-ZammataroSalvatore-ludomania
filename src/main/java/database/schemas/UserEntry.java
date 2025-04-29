package database.schemas;

import database.schemas.api.Entry;

public class UserEntry extends Entry {
    public final String username;
    public final String password;

    public UserEntry(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}

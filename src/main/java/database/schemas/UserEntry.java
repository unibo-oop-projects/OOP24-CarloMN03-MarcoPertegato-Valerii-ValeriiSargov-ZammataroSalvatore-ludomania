package database.schemas;

import database.schemas.api.Entry;

public class UserEntry extends Entry {
    private final String username;
    private final String password;

    public UserEntry(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}

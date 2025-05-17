package database.models;

import database.core.LudomaniaDBManager;
import database.models.api.DBModel;
import database.schemas.UserEntry;

public class User implements DBModel {
    private final String dbFilename = "users.json";
    private final UserEntry entry;

    public User(final UserEntry entry) {
        this.entry = entry;
    }

    @Override
    public boolean insert() {
        return LudomaniaDBManager.getManager().insert(entry, this.dbFilename);
    }

    @Override
    public boolean update() {
        return LudomaniaDBManager.getManager().update(entry, this.dbFilename);
    }

    @Override
    public boolean delete() {
        return LudomaniaDBManager.getManager().delete(entry, this.dbFilename);
    }

    @Override
    public UserEntry read() {
        return LudomaniaDBManager.getManager().read(entry, this.dbFilename);
    }
}

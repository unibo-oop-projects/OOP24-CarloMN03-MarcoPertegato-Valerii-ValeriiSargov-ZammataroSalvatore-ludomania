package database.models;

import database.core.LudomaniaDBManager;
import database.models.api.DBModel;
import database.schemas.UserEntry;


public class User implements DBModel {
    private final UserEntry user;

    public User(UserEntry entry) {
        this.user = entry;
    }

    @Override
    public boolean insert() {
        return LudomaniaDBManager.getManager().insert(user, "users.json");
    }

    @Override
    public boolean update() {
        return LudomaniaDBManager.getManager().update(user, "users.json");
    }

    @Override
    public boolean delete() {
        return LudomaniaDBManager.getManager().delete(user, "users.json");
    }

    @Override
    public UserEntry read() {
        return LudomaniaDBManager.getManager().read(user, "users.json");
    }
}

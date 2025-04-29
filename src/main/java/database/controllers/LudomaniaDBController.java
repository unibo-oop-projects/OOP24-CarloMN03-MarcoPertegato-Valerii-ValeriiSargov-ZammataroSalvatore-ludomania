package database.controllers;

import database.controllers.api.DatabaseController;
import database.models.User;
import database.models.Wallet;
import database.models.api.DBModel;
import database.schemas.UserEntry;
import database.schemas.WalletEntry;
import database.schemas.api.Entry;

public class LudomaniaDBController implements DatabaseController {

    private final DBModel model;

    public LudomaniaDBController(UserEntry entry) {
        this.model = new User(entry);
    }

    public LudomaniaDBController(WalletEntry entry) {
        this.model = new Wallet(entry);
    }

    @Override
    public boolean insert() {
        return this.model.insert();
    }

    @Override
    public boolean update() {
        return this.model.update();
    }

    @Override
    public boolean delete() {
        return this.model.delete();
    }

    @Override
    public Entry read() {
        return this.model.read();
    }

}

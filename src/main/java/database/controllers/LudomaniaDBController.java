package database.controllers;

import database.controllers.api.DatabaseController;
import database.models.User;
import database.models.Wallet;
import database.schemas.UserEntry;
import database.schemas.WalletEntry;

public class LudomaniaDBController extends DatabaseController {
    public LudomaniaDBController(UserEntry entry) {
        this.model = new User(entry);
    }

    public LudomaniaDBController(WalletEntry entry) {
        this.model = new Wallet(entry);
    }
}

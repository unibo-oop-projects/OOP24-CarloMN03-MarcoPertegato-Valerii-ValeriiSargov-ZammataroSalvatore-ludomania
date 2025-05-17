package database.models;

import database.core.LudomaniaDBManager;
import database.models.api.DBModel;
import database.schemas.WalletEntry;

public class Wallet implements DBModel {
    private final String dbFilename = "wallets.json";
    private final WalletEntry entry;

    public Wallet(final WalletEntry entry) {
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
    public WalletEntry read() {
        return LudomaniaDBManager.getManager().read(entry, this.dbFilename);
    }
}

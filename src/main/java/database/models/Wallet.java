package database.models;

import database.core.LudomaniaDBManager;
import database.models.api.DBModel;
import database.schemas.WalletEntry;

public class Wallet implements DBModel{
    private final WalletEntry wallet;

    public Wallet(WalletEntry entry) {
        this.wallet = entry;
    }
    
    @Override
    public boolean insert() {
        return LudomaniaDBManager.getManager().insert(wallet, "wallets.json");
    }

    @Override
    public boolean update() {
        return LudomaniaDBManager.getManager().update(wallet, "wallets.json");
    }

    @Override
    public boolean delete() {
        return LudomaniaDBManager.getManager().delete(wallet, "wallets.json");
    }

    @Override
    public WalletEntry read() {
        return LudomaniaDBManager.getManager().read(wallet, "wallets.json");
    }
}

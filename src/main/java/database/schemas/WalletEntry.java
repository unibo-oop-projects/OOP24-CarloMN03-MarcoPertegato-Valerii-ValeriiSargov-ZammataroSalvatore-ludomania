package database.schemas;

import database.schemas.api.Entry;

public class WalletEntry extends Entry {
    public final double amount;
    public final String username;

    public WalletEntry(final double amount, final String username) {
        this.amount = amount;
        this.username = username;
    }    
}

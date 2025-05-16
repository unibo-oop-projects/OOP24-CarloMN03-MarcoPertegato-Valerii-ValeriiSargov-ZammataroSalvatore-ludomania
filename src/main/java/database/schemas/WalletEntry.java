package database.schemas;

import database.schemas.api.Entry;

public class WalletEntry extends Entry {
    private final double amount;
    private final String username;

    public WalletEntry(final String username, final double amount) {
        this.amount = amount;
        this.username = username;
    }    

    public WalletEntry(final String username) {
        this(username, 0);
    }
}

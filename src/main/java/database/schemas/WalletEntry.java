package database.schemas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import database.schemas.api.Entry;

public class WalletEntry implements Entry {
    private final double amount;
    private final String username;

    @JsonCreator
    public WalletEntry(@JsonProperty("username") final String username, @JsonProperty("amount") final double amount) {
        this.amount = amount;
        this.username = username;
    }

    public WalletEntry(final String username) {
        this(username, 0);
    }

    @Override
    public final String identifier() {
        return this.username;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getUsername() {
        return this.username;
    }
}

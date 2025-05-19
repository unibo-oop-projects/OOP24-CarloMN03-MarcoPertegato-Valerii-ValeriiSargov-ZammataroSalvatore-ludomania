package ludomania.model.bet.impl;

import ludomania.model.bet.api.BetType;

public enum BlackJackBetType implements BetType {
    BASE("Base", 1.0),
    BLACKJACK("BlackJack", 1.5),
    PUSH("Push", 0.0),
    LOSE("Lose", -1.0);

    private final String typeName;
    private final double payout;

    BlackJackBetType(String displayName, double payout) {
        this.typeName = displayName;
        this.payout = payout;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Double getPayout() {
        return payout;
    }
    
}

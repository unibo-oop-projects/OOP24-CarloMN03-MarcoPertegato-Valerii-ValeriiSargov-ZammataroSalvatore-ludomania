package ludomania.model.player.impl;

import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.api.Wallet;

public class TrenteEtQuarantePlayer extends Player {

    public TrenteEtQuarantePlayer(final Wallet wallet) {
        super(wallet);
    }

    @Override
    public Bet makeBet(final Double amount, final BetType type) {
        if (!(type instanceof TrenteEtQuaranteBetType)) {
            throw new IllegalArgumentException("Invalid bet type for Trente et Quarante.");
        }
        if (!withdraw(amount)) {
            throw new IllegalArgumentException("Not enough money to place a bet.");
        }
        return new TrenteEtQuaranteBet(amount, (TrenteEtQuaranteBetType) type);
    }

}

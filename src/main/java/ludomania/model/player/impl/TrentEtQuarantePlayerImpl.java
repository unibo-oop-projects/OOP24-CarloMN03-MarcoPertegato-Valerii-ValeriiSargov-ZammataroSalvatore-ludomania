package ludomania.model.player.impl;

import ludomania.model.bet.api.Bet;
import ludomania.model.player.api.TrenteEtQuarantePlayer;
import ludomania.model.wallet.api.Wallet;

public class TrentEtQuarantePlayerImpl extends TrenteEtQuarantePlayer {

    public TrentEtQuarantePlayerImpl(Wallet wallet) {
        super(wallet);
    }

    @Override
    public Bet makeBet(Double amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeBet'");
    }

}

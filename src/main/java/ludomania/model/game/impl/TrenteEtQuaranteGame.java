package ludomania.model.game.impl;

import java.util.List;

import ludomania.model.Pair;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.croupier.impl.TrenteEtQuaranteDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.impl.TrenteEtQuarantePlayer;

public class TrenteEtQuaranteGame implements Game<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> {
    TrenteEtQuaranteDealer dealer;
    List<TrenteEtQuarantePlayer> players;
    String currentUser;
    int userNumber;

    public TrenteEtQuaranteGame(final TrenteEtQuaranteDealer dealer, final List<TrenteEtQuarantePlayer> players){
        this.dealer = dealer;
        this.players = players;
        currentUser = players.getFirst().getUsername();
        userNumber = 1;
    }

    @Override
    public CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> runGame() {
        while (!dealer.isEnough(dealer.getNoir())) {
            dealer.extractNewCard(dealer.getNoir());
        }
        while (!dealer.isEnough(dealer.getRouge())) {
            dealer.extractNewCard(dealer.getRouge());
        }
        return dealer.declareResult();
    }

    @Override
    public Boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

    @Override
    public Boolean playAgain() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playAgain'");
    }

    public void playerMakesBet(TrenteEtQuaranteBet bet){
        dealer.addBet(players.get(userNumber), bet);
    }

    public Boolean nextPlayer() {
        if (userNumber == players.size()){
            return false;
        }
        currentUser = players.get(userNumber).getUsername();
        userNumber ++;
        return true;
    }

    public String getCurrentUser(){
        return currentUser;
    }
    
}

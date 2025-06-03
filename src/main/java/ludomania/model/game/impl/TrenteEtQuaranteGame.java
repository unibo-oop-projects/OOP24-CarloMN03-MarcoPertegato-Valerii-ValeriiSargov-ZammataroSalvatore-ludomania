package ludomania.model.game.impl;

import java.util.List;
import java.util.Map;

import io.lyuda.jcards.Hand;
import ludomania.model.Pair;
import ludomania.model.bet.impl.TrenteEtQuaranteBet;
import ludomania.model.bet.impl.TrenteEtQuaranteBetType;
import ludomania.model.croupier.impl.TrenteEtQuaranteDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.TrenteEtQuarantePlayer;

public class TrenteEtQuaranteGame implements Game<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> {
    private TrenteEtQuaranteDealer dealer;
    private List<TrenteEtQuarantePlayer> players;
    private String currentUser;
    private int userNumber;
    private int deckNumber;

    public TrenteEtQuaranteGame(final TrenteEtQuaranteDealer dealer, final List<TrenteEtQuarantePlayer> players, final int deckNumber){
        this.dealer = dealer;
        this.players = players;
        this.deckNumber = deckNumber;
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

    public void playAgain() {
        resetRound();
        resetDecks();
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

    public Double getCurrentPlayerBalance(){
        return players.get(userNumber-1).getBalance();
    }

    public Map<Player, Double> evaluateBets(final CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> result) {
       return this.dealer.checkBets(result);        
    }

    public void payUp(final Map<Player, Double> winner) {
        for (final Map.Entry<Player, Double> entry : winner.entrySet()) {
            final Player player = entry.getKey();
            player.deposit(entry.getValue());
        }
    }

    public void resetRound(){
        dealer.reset();
    }

    public void resetDecks(){
        if(dealer.needToResetAllDecks()){
            dealer.initDeck(deckNumber);
        }
    }

    public int getNoirTotalValue(){
        return dealer.getHandTotal(dealer.getNoir());
    }

    public int getRougeTotalValue(){
        return dealer.getHandTotal(dealer.getRouge());
    }

    public Hand getNoir(){
        return dealer.getNoir();
    }

    public Hand getRouge(){
        return dealer.getRouge();
    }
    
}

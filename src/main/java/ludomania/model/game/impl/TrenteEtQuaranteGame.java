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

/**
 * Implementation of the {@link Game} interface for Trente et Quarante.
 * <p>
 * Handles the main game flow, including managing players, handling bets,
 * drawing cards, and determining winners.
 */
public class TrenteEtQuaranteGame implements Game<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> {
    private TrenteEtQuaranteDealer dealer;
    private List<TrenteEtQuarantePlayer> players;
    private String currentUser;
    private int userNumber;
    private int deckNumber;

    /**
     * Constructs a new TrenteEtQuaranteGame.
     *
     * @param dealer     the game dealer
     * @param players    the list of players
     * @param deckNumber the number of decks to initialize
     */
    public TrenteEtQuaranteGame(final TrenteEtQuaranteDealer dealer,
     final List<TrenteEtQuarantePlayer> players, final int deckNumber) {
        this.dealer = dealer;
        this.players = players;
        this.deckNumber = deckNumber;
        currentUser = players.getFirst().getUsername();
        userNumber = 1;
    }

    /**
     * Executes a round of the game by drawing cards and determining the result.
     *
     * @return the result of the round
     */
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

    /**
     * Resets the game round and decks if necessary.
     */
    public void resetRound() {
        dealer.reset();
        resetDecks();
    }

    /**
     * Adds a bet made by the current player.
     *
     * @param bet the bet placed
     */
    public void playerMakesBet(final TrenteEtQuaranteBet bet) {
        dealer.addBet(players.get(userNumber), bet);
    }

    /**
     * Advances to the next player.
     *
     * @return true if another player is available, false otherwise
     */
    public Boolean nextPlayer() {
        if (userNumber == players.size()) {
            return false;
        }
        currentUser = players.get(userNumber).getUsername();
        userNumber++;
        return true;
    }

    /**
     * Advances to the next player.
     *
     * @return true if another player is available, false otherwise
     */
    public String getCurrentUser() {
        return currentUser;
    }

     /**
     * Gets the balance of the current player.
     *
     * @return the current player's balance
     */
    public Double getCurrentPlayerBalance() {
        return players.get(userNumber - 1).getBalance();
    }

    /**
     * Evaluates all bets and determines winners based on the game result.
     *
     * @param result the result of the round
     * @return a map of players to their winnings
     */
    public Map<Player, Double> evaluateBets(final CounterResult<Pair<TrenteEtQuaranteBetType, TrenteEtQuaranteBetType>> result) {
       return this.dealer.checkBets(result);
    }

    /**
     * Pays the winners based on the result.
     *
     * @param winner a map of players to their winnings
     */
    public void payUp(final Map<Player, Double> winner) {
        for (final Map.Entry<Player, Double> entry : winner.entrySet()) {
            final Player player = entry.getKey();
            player.deposit(entry.getValue());
        }
    }

    /**
     * Resets the decks if the number of decks is below the minimum.
     */
    public void resetDecks() {
        if (dealer.needToResetAllDecks()) {
            dealer.initDeck(deckNumber);
        }
    }

    /**
     * Gets the total value of the noir hand.
     *
     * @return the value of the noir hand
     */
    public int getNoirTotalValue() {
        return dealer.getHandTotal(dealer.getNoir());
    }

    /**
     * Gets the total value of the rouge hand.
     *
     * @return the value of the rouge hand
     */
    public int getRougeTotalValue() {
        return dealer.getHandTotal(dealer.getRouge());
    }

    /**
     * Gets the noir hand.
     *
     * @return the noir hand
     */
    public Hand getNoir() {
        return dealer.getNoir();
    }

    /**
     * Gets the rouge hand.
     *
     * @return the rouge hand
     */
    public Hand getRouge() {
        return dealer.getRouge();
    }
}

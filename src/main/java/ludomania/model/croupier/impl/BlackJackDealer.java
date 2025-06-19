package ludomania.model.croupier.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.impl.BlackJackOutcomeResult;
import ludomania.model.game.impl.BlackJackResult;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.api.Player;

/**
 * Represents a Blackjack dealer that manages card dealing, hand tracking,
 * and bet evaluation in a Blackjack round.
 * Extends {@link CardDealer} with Blackjack-specific logic.
 */
public class BlackJackDealer extends CardDealer<Map<Player, BlackJackOutcomeResult>> {

    private static final int MAX_HAND_VALUE = 17;
    private static final int NUMBER_OF_DEKS = 6;

    private Hand dealer;
    private Hand player;
    private int dealerTot;
    private int playerTot;
    private final Map<Player, Bet> bjRoundBet;

    /**
     * Constructs a new BlackJackDealer with bets for the current round and deck source.
     *
     * @param roundBet map of players and their associated bets
     * @param decks factory used to create and shuffle card decks
     */
    public BlackJackDealer(List<Pair<Player, Bet>> roundBet, DeckFactory decks) {
        super(roundBet, decks);
        this.bjRoundBet = roundBet.stream().collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        this.dealer = new Hand();
        this.player = new Hand();
        this.dealerTot = 0;
        this.playerTot = 0;
    }

    /**
     * Checks the outcomes of all player bets and distributes winnings accordingly.
     *
     * @param result a CounterResult of player outcomes (must be a BlackJackResult)
     * @return a map of winning players and the amounts they won
     */
    @Override
    public Map<Player, Double> checkBets(CounterResult<Map<Player, BlackJackOutcomeResult>> result) {
        if (!(result instanceof BlackJackResult)) {
            throw new IllegalArgumentException("Invalid result type for BlackJackDealer");
        }

        Map<Player, Double> winners = new HashMap<>();
        Map<Player, BlackJackOutcomeResult> outcomes = result.getResult();

        for (Map.Entry<Player, BlackJackOutcomeResult> entry : outcomes.entrySet()) {
            Player currentPlayer = entry.getKey();
            BlackJackOutcomeResult outcomeResult = entry.getValue();
            
            if (!bjRoundBet.containsKey(currentPlayer)) {
                System.out.println("Nessuna bet trovata per il player: " + currentPlayer);
                continue;
            }
            Bet bet = new BlackJackBet(bjRoundBet.get(currentPlayer).getValue(), (BlackJackBetType) bjRoundBet.get(currentPlayer).getType());

            // Evaluate winnings based on the outcome
            switch (outcomeResult.getOutcome()) {
                case WIN -> {
                    winners.put(currentPlayer, bet.evaluate());
                    deposit(currentPlayer, bet.evaluate());
                }
                case BLACKJACK -> {
                    double blackjackWin = bet.getValue() * BlackJackBetType.BLACKJACK.getPayout();
                    winners.put(currentPlayer, blackjackWin);
                    deposit(currentPlayer, blackjackWin);
                }
                case PUSH -> {
                    winners.put(currentPlayer, bet.getValue());
                    deposit(currentPlayer, bet.getValue());
                }
                case LOSE -> deposit(currentPlayer, 0.0);
                default -> deposit(currentPlayer, 0.0);
            }
        }
        clearRound();
        return winners;
    }

    public Map<Player, Bet> getBjRoundBet() {
        return this.bjRoundBet;
    }

    // Deposits the specified value to the player's account
    private void deposit(Player player, Double value) {
        player.deposit(value);
    }

    // Resets the dealer and player hands and totals for a new round
    public void reset() {
        dealer = new Hand();
        player = new Hand();
        dealerTot = 0;
        playerTot = 0;
    }

    // === Accessors ===

    public Hand getPlayer() {
        return player;
    }

    public Hand getDealer() {
        return dealer;
    }

    public int getPlayerTot() {
        return playerTot;
    }

    public int getDealerTot() {
        return dealerTot;
    }

    // === Totals Management ===

    // Increases the dealer's total hand value
    public void increaseDealerTot(int amount) {
        dealerTot += amount;
    }

    // Increases the player's total hand value
    public void increasePlayerTot(int amount) {
        playerTot += amount;
    }

    /**
     * Draws a new card and adds it to the specified hand.
     * If all decks are used up, it reinitializes them.
     *
     * @param hand the hand to add a new card to
     * @return the drawn card
     */
    public Card extractNewCard(Hand hand) {
        if(super.needToResetAllDecks()) {
            initDeck(NUMBER_OF_DEKS);
        }
        Card extractedCard = drawCard();
        hand.addCard(extractedCard);
        return extractedCard;
    }

    // Determines if the current hand value is high enough to stop drawing cards
    public boolean isEnough(int handTot) {
        return handTot >= MAX_HAND_VALUE;
    }
}

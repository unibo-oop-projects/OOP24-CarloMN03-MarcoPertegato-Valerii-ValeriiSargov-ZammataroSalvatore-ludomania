package ludomania.model.croupier.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
    public BlackJackDealer(final List<Pair<Player, Bet>> roundBet, final DeckFactory decks) {
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
    public Map<Player, Double> checkBets(final CounterResult<Map<Player, BlackJackOutcomeResult>> result) {
        if (!(result instanceof BlackJackResult)) {
            throw new IllegalArgumentException("Invalid result type for BlackJackDealer");
        }

        final Map<Player, Double> winners = new HashMap<>();
        final Map<Player, BlackJackOutcomeResult> outcomes = result.getResult();

        for (final Map.Entry<Player, BlackJackOutcomeResult> entry : outcomes.entrySet()) {
            final Player currentPlayer = entry.getKey();
            final BlackJackOutcomeResult outcomeResult = entry.getValue();
            if (!bjRoundBet.containsKey(currentPlayer)) {
                //System.out.println("Nessuna bet trovata per il player: " + currentPlayer);
                continue;
            }
            final Bet bet = new BlackJackBet(bjRoundBet.get(currentPlayer).getValue(), 
                (BlackJackBetType) bjRoundBet.get(currentPlayer).getType());

            // Evaluate winnings based on the outcome
            switch (outcomeResult.getOutcome()) {
                case WIN -> {
                    winners.put(currentPlayer, bet.evaluate());
                    deposit(currentPlayer, bet.evaluate());
                }
                case BLACKJACK -> {
                    final double blackjackWin = bet.getValue() * BlackJackBetType.BLACKJACK.getPayout();
                    winners.put(currentPlayer, blackjackWin);
                    deposit(currentPlayer, blackjackWin);
                }
                case PUSH -> {
                    winners.put(currentPlayer, bet.getValue());
                    deposit(currentPlayer, bet.getValue());
                }
                case LOSE -> deposit(currentPlayer, 0.0);
            }
        }
        clearRound();
        return winners;
    }

    /**
     * Returns the current round's map of player bets.
     *
     * @return a {@code Map} of players and their associated bets
     */
    public Map<Player, Bet> getBjRoundBet() {
        return new HashMap<>(this.bjRoundBet);
    }

    /**
     * Deposits the specified value into the player's wallet.
     *
     * @param player the player to deposit the winnings into
     * @param value  the amount to deposit
     */
    private void deposit(final Player player, final Double value) {
        player.deposit(value);
    }

    /**
     * Resets the dealer and player hands and totals for a new round.
     */
    public void reset() {
        dealer = new Hand();
        player = new Hand();
        dealerTot = 0;
        playerTot = 0;
    }

    // === Accessors ===

    /**
     * Returns the player's current hand.
     *
     * @return the player's {@code Hand}
     */
    @SuppressFBWarnings(
        value = "EI",
        justification = "Access to player hand is intentionally allowed."
    )
    public Hand getPlayer() {
        return player;
    }

    /**
     * Returns the dealer's current hand.
     *
     * @return the dealer's {@code Hand}
     */
    @SuppressFBWarnings(
        value = "EI",
        justification = "Access to dealer hand is intentionally allowed."
    )
    public Hand getDealer() {
        return dealer;
    }

    /**
     * Returns the player's current hand total value.
     *
     * @return the player's hand total
     */
    public int getPlayerTot() {
        return playerTot;
    }

    /**
     * Returns the dealer's current hand total value.
     *
     * @return the dealer's hand total
     */
    public int getDealerTot() {
        return dealerTot;
    }

    // === Totals Management ===

    /**
     * Increases the dealer's hand total by the specified amount.
     *
     * @param amount the amount to add
     */
    public void increaseDealerTot(final int amount) {
        dealerTot += amount;
    }

    /**
     * Increases the player's hand total by the specified amount.
     *
     * @param amount the amount to add
     */
    public void increasePlayerTot(final int amount) {
        playerTot += amount;
    }

    /**
     * Draws a new card and adds it to the specified hand.
     * If all decks are used up, it reinitializes them.
     *
     * @param hand the hand to add a new card to
     * @return the drawn card
     */
    public Card extractNewCard(final Hand hand) {
        if (super.needToResetAllDecks()) {
            initDeck(NUMBER_OF_DEKS);
            shuffleAll();
        }
        final Card extractedCard = drawCard();
        hand.addCard(extractedCard);
        return extractedCard;
    }

    /**
     * Determines whether the current hand total is enough to stop drawing cards.
     *
     * @param handTot the total value of the hand
     * @return {@code true} if the hand total is greater than or equal to the threshold (17),
     *         {@code false} otherwise
     */
    public boolean isEnough(final int handTot) {
        return handTot >= MAX_HAND_VALUE;
    }
}

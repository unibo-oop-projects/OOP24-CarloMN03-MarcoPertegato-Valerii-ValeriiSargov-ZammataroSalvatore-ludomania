package ludomania.model.game.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.impl.BlackJackDealer;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.BlackJackPlayer;

/**
 * Central class of the model, it manages the blackjack game by making 
 * the dealer and player interact together and therefore managing an entire game.
 */
public class BlackJackGame implements Game<Map<Player, BlackJackOutcomeResult>> {

    private static final int MAX_HAND_VALUE = 21;
    private static final int ACE = 11;
    private static final int NUMBER_OF_DEKS = 6;

    private final BlackJackDealer dealer;
    private final BlackJackPlayer player;
    private boolean gameOver;

    /**
     * Constructs a Blackjack game with a single player and initializes the dealer and decks.
     *
     * @param player the {@link BlackJackPlayer} participating in the game
     */
    public BlackJackGame(final BlackJackPlayer player) {
        this.player = player;
        final List<Pair<Player, Bet>> roundBet = new LinkedList<>();
        final DeckFactory deckFactory = createMultiDeck(NUMBER_OF_DEKS);
        deckFactory.shuffleAllDecks();
        this.dealer = new BlackJackDealer(roundBet, deckFactory);
        this.gameOver = false;
    }

    /**
     * Places a new bet for the current player.
     *
     * @param amount the amount of money the player wants to bet
     */
    public void placeBet(final double amount) {
        player.makeBet(amount, BlackJackBetType.BASE);
        final Bet bet = player.getPlacedBet();
        dealer.getBjRoundBet().put(player, bet);
    }

    /**
     * Starts a new round by resetting the dealer and player states.
     */
    public void startNewRound() {
        dealer.reset();
        gameOver = false;
    }

    /**
     * Deals two initial cards to both the player and the dealer.
     */
    public void dealInitialCards() {
        dealer.extractNewCard(dealer.getPlayer());
        dealer.extractNewCard(dealer.getPlayer());
        dealer.extractNewCard(dealer.getDealer());
        dealer.extractNewCard(dealer.getDealer());
    }

    /**
     * Draws one additional card for the player ("hit" action).
     * If the total exceeds 21, the game ends.
     */
    public void hit() {
        dealer.extractNewCard(dealer.getPlayer());
        if (getPlayerTotal() > MAX_HAND_VALUE) {
            gameOver = true;
        }
    }

    /**
     * Ends the player's turn and lets the dealer draw cards until reaching at least 17.
     */
    public void stand() {
        while (!dealer.isEnough(getDealerTotal())) {
            dealer.extractNewCard(dealer.getDealer());
        }
        gameOver = true;
    }

    @Override
    public final CounterResult<Map<Player, BlackJackOutcomeResult>> runGame() {
        if (!gameOver) {
            stand();
        }

        final int playerTot = getPlayerTotal();
        final int dealerTot = getDealerTotal();
        final BlackJackOutcome outcome;
        final BlackJackBetType type;

        if (playerTot > MAX_HAND_VALUE || isBlackjack(getDealerHand())) {
            outcome = BlackJackOutcome.LOSE;
            type = BlackJackBetType.LOSE;
        } else if (dealerTot > MAX_HAND_VALUE || playerTot > dealerTot) {
            if (isBlackjack(getPlayerHand())) {
                outcome = BlackJackOutcome.BLACKJACK;
                type = BlackJackBetType.BLACKJACK;
            } else {
                outcome = BlackJackOutcome.WIN;
                type = BlackJackBetType.BASE;
            }
        } else if (playerTot == dealerTot) {
            outcome = BlackJackOutcome.PUSH;
            type = BlackJackBetType.PUSH;
        } else {
            outcome = BlackJackOutcome.LOSE;
            type = BlackJackBetType.LOSE;
        }

        gameOver = true;
        final Map<Player, BlackJackOutcomeResult> outcomes = new HashMap<>();
        outcomes.put(player, new BlackJackOutcomeResult(outcome, type));
        final BlackJackResult result = new BlackJackResult(outcomes);

        dealer.checkBets(result);

        return result;
    }

    /**
     * Gets the current hand of the player.
     *
     * @return the {@link Hand} of the player
     */
    public Hand getPlayerHand() {
        return dealer.getPlayer();
    }

    /**
     * Gets the current hand of the dealer.
     *
     * @return the {@link Hand} of the dealer
     */
    public Hand getDealerHand() {
        return dealer.getDealer();
    }

    /**
     * Returns the number of cards in the player's hand.
     *
     * @return the size of the player's hand
     */
    public int getPlayerTotalCards() {
        return dealer.getPlayer().size();
    }

    /**
     * Returns the number of cards in the dealer's hand.
     *
     * @return the size of the dealer's hand
     */
    public int getDealerTotalCards() {
        return dealer.getDealer().size();
    }

    /**
     * Calculates the total value of the player's hand.
     *
     * @return the total value
     */
    public int getPlayerTotal() {
        return calculateTotal(dealer.getPlayer());
    }

    /**
     * Calculates the total value of the dealer's hand.
     *
     * @return the total value
     */
    public int getDealerTotal() {
        return calculateTotal(dealer.getDealer());
    }

    /**
     * Returns the player's current balance.
     *
     * @return the balance as a {@link Double}
     */
    public Double getPlayerFinance() {
        return player.getBalance();
    }

    /**
     * Checks whether the given hand is a blackjack (2 cards totaling 21).
     *
     * @param hand the {@link Hand} to evaluate
     * @return {@code true} if it's a blackjack, {@code false} otherwise
     */
    private boolean isBlackjack(final Hand hand) {
        return hand.getCards().size() == 2 && calculateTotal(hand) == MAX_HAND_VALUE;
    }

    /**
     * Indicates whether the game is over.
     *
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    public Boolean isOver() {
        return gameOver;
    }

    /**
     * Allows the player to start a new game.
     *
     * @return always {@code true}
     */
    public Boolean playAgain() {
        gameOver = false;
        return true;
    }

    /**
     * Calculates the total value of a hand, adjusting for Aces.
     *
     * @param hand the {@link Hand} to calculate the total for
     * @return the integer value of the hand
     */
    private int calculateTotal(final Hand hand) {
        int total = 0;
        int aceCount = 0;

        for (final Card card : hand.getCards()) {
            final String rank = card.getRank().toString();
            switch (rank) {
                case "ACE" -> {
                    total += ACE;
                    aceCount++;
                }
                case "KING", "QUEEN", "JACK" -> total += 10;
                default -> total += card.getRank().getValue();
            }
        }

        // Adjust axes from 11 to 1 if we go over
        while (total > MAX_HAND_VALUE && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    /**
     * Creates a multi-deck factory containing the specified number of standard decks.
     *
     * @param numDecks number of decks to include
     * @return the created {@link DeckFactory}
     */
    private DeckFactory createMultiDeck(final int numDecks) {
        final DeckFactory factory = new DeckFactory();
        for (int i = 0; i < numDecks; i++) {
            final Deck tmp = new Deck();
            factory.addDeck(tmp);
        }

        return factory;
    }
}

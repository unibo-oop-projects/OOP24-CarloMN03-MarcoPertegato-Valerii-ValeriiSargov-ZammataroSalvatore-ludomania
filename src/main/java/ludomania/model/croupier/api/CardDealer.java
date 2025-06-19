package ludomania.model.croupier.api;

import java.util.List;
import java.util.Random;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import ludomania.model.Pair;
import ludomania.model.bet.api.Bet;
import ludomania.model.player.api.Player;

/**
 * Abstract class representing a dealer that manages a set of card decks
 * for a card-based game.
 * <p>
 * Extends {@link Croupier} and provides functionality for deck creation,
 * shuffling, card drawing, and automatic deck maintenance.
 *
 * @param <T> the type of result the dealer produces
 */
public abstract class CardDealer<T> extends Croupier<T> {

    private static final int MIN_DECK_NUM = 3;
    private final DeckFactory decks;

    /**
     * Constructs a CardDealer with a list of bets and a deck factory.
     *
     * @param roundBet the list of bets for the current round
     * @param decks    the deck factory used to manage card decks
     */
    public CardDealer(final List<Pair<Player, Bet>> roundBet, final DeckFactory decks) {
        super(roundBet);
        this.decks = decks;
    }

    /**
     * Returns the current number of decks available.
     *
     * @return the number of decks
     */
    public int getDeckCount() {
        return decks.getDeckCount();
    }

    /**
     * Shuffles all decks managed by the deck factory.
     */
    public void shuffleAll() {
        decks.shuffleAllDecks();
    }

    /**
     * Initializes the decks by clearing all existing ones
     * and creating the specified number of new decks.
     *
     * @param amount the number of decks to create
     */
    public void initDeck(final int amount) {
        decks.clearDecks();
        for (int i = 0; i < amount; i++) {
            decks.createDeck();
        }
    }

    /**
     * Draws a random card from a non-empty deck.
     * If a randomly selected deck is empty, it will be removed and a new one will be selected.
     *
     * @return a card from one of the available decks
     */
    public Card drawCard() {
        final Random rand = new Random();
        int index = rand.nextInt(getDeckCount());
        while (decks.getDeck(index).getCards().isEmpty()) {
            decks.removeDeck(index);
            index = rand.nextInt(getDeckCount());
        }
        return decks.getDeck(index).deal();
    }

    /**
     * Checks whether all decks should be reset based on the minimum threshold.
     *
     * @return {@code true} if the number of decks is less than the minimum required
     */
    public boolean needToResetAllDecks() {
        return decks.getDeckCount() < MIN_DECK_NUM;
    }
}

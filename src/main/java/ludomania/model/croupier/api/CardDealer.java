package ludomania.model.croupier.api;

import java.util.Map;
import java.util.Random;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.DeckFactory;
import ludomania.model.bet.api.Bet;
import ludomania.model.player.api.Player;

public abstract class CardDealer<T> extends Croupier<T> {

    private static final int MIN_DECK_NUM = 3;
    private final DeckFactory decks;

    public CardDealer(Map<Player, Bet> roundBet, DeckFactory decks) {
        super(roundBet);
        this.decks = decks;
    }

    public int getDeckCount() {
        return decks.getDeckCount();
    }

    //Probabilmente inutilizzata
    public Deck getSingleDeck(int index) {
        return decks.getDeck(index);
    }

    public void shuffleAll() {
        decks.shuffleAllDecks();
    }
    
    public void initDeck(int amount) {
        for (int i = 0; i < amount; i++) {
            decks.createDeck();
        }
    }

    public void removeDeck(int index) {
        decks.removeDeck(index);
    }

    public Card drawCard() {
        Random rand = new Random();
        int index = rand.nextInt(getDeckCount());
        while(decks.getDeck(index).getCards().isEmpty()) {
            decks.removeDeck(index);
            index = rand.nextInt(getDeckCount());
        }
        return decks.getDeck(index).deal();
    }

    public boolean needToResetAllDecks() {
        return decks.getDeckCount() < MIN_DECK_NUM;
    }
}
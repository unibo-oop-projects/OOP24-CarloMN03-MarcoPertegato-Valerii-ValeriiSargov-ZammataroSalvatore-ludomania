package ludomania.model.game.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.impl.BlackJackDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.BlackJackPlayer;

/**
 * Central class of the model, it manages the blackjack game by making 
 * the dealer and player interact together and therefore managing an entire game.
 */
public class BlackJackGame implements Game<Map<Player, BlackJackOutcomeResult>> {

    private final BlackJackDealer dealer;
    private final BlackJackPlayer player;
    private boolean gameOver;
    
    // Blackjack Game Builder
    public BlackJackGame(BlackJackPlayer player) {
        this.player = player;
        Map<Player, Bet> roundBet = new HashMap<>();
        DeckFactory deckFactory = createMultiDeck(6);
        deckFactory.shuffleAllDecks();
        this.dealer = new BlackJackDealer(roundBet, deckFactory);
        this.gameOver = false;
    }

    // Method to add a bet to the player
    public void placeBet(double amount) {
        player.makeBet(amount, BlackJackBetType.BASE);
        Bet bet = player.getPlacedBet();
        dealer.getRoundBet().put(player, bet);
    }

    // Method to reset the game
    public void startNewRound() {
        dealer.reset();
        gameOver = false;
    }

    // Method for the initial distribution of cards to dealers and players
    public void dealInitialCards() {
        dealer.extractNewCard(dealer.getPlayer());
        dealer.extractNewCard(dealer.getPlayer());
        dealer.extractNewCard(dealer.getDealer());
        dealer.extractNewCard(dealer.getDealer());
    }

    // Method for drawing a player card
    public void hit() {
        dealer.extractNewCard(dealer.getPlayer());
        if (getPlayerTotal() > 21) {
            gameOver = true;
        }
    }

    // Method that manages the dealer's card draw
    public void stand() {
        while (!dealer.isEnough(getDealerTotal())) {
            dealer.extractNewCard(dealer.getDealer());
        }
        gameOver = true;
    }

    @Override
    public CounterResult<Map<Player, BlackJackOutcomeResult>> runGame() {
        if (!gameOver) {
            stand();
        }
        
        int playerTot = getPlayerTotal();
        int dealerTot = getDealerTotal();
        BlackJackOutcome outcome;
        BlackJackBetType type;

        if (playerTot > 21 || isBlackjack(getDealerHand())) {
            outcome = BlackJackOutcome.LOSE;
            type = BlackJackBetType.LOSE;
        } else if (dealerTot > 21 || playerTot > dealerTot) {
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
        Map<Player, BlackJackOutcomeResult> outcomes = new HashMap<>();
        outcomes.put(player, new BlackJackOutcomeResult(outcome, type));
        BlackJackResult result = new BlackJackResult(outcomes);

        dealer.checkBets(result);

        return result;
    }

    // Returns the player's hand
    public Hand getPlayerHand() {
        return dealer.getPlayer();
    }

    // Returns the dealer's hand
    public Hand getDealerHand() {
        return dealer.getDealer();
    }

    // Returns the number of cards the player has
    public int getPlayerTotalCards() {
        return dealer.getPlayer().size();
    }

    // Returns the number of cards the dealer has
    public int getDealerTotalCards() {
        return dealer.getDealer().size();
    }

    // Returns the total value of the cards in the player's hand
    public int getPlayerTotal() {
        return calculateTotal(dealer.getPlayer());
    }

    //Returns the total value of the cards in the dealer's hand
    public int getDealerTotal() {
        return calculateTotal(dealer.getDealer());
    }

    // Returns the player's balance
    public Double getPlayerFinance() {
        return player.getBalance();
    }

    // Returns if the hand contains a blackjack
    private boolean isBlackjack(Hand hand) {
        return hand.getCards().size() == 2 && calculateTotal(hand) == 21;
    }

    public Boolean isOver() {
        return gameOver;
    }

    public Boolean playAgain() {
        gameOver = false;
        return true;    
    }

    // Support method to get the total value of the hand
    private int calculateTotal(Hand hand) {
        int total = 0;
        int aceCount = 0;
        
        for (Card card : hand.getCards()) {
            String rank = card.getRank().toString();
            switch (rank) {
                case "ACE" -> {
                    total += 11;
                    aceCount++;
                }
                case "KING", "QUEEN", "JACK" -> total += 10;
                default -> total += card.getRank().getValue();
            }
        }

        // Adjust axes from 11 to 1 if we go over
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    /*
     * Method that creates a multiple deck with 
     * the value of the decks passed as parameter.
     */
    private DeckFactory createMultiDeck(int numDecks) {
        DeckFactory factory = new DeckFactory();
        for (int i = 0; i < numDecks; i++) {
            Deck tmp = new Deck();
            factory.addDeck(tmp);
        }

        return factory;
    }    
}
package ludomania.model.game.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.BlackJackPlayer;

public class BlackJackGame implements Game<Map<Player, BlackJackOutcomeResult>> {

    private final BlackJackPlayer player;
    private final Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private boolean gameOver;
    

    public BlackJackGame(BlackJackPlayer player) {
        this.deck = new DeckFactory().createDeck(); 
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.gameOver = false;
        this.player = player;
    }

    public void placeBet(double amount) {
        player.makeBet(amount, BlackJackBetType.BASE);
    }

    public void startNewRound() {
        playerHand = new Hand();
        dealerHand = new Hand();
        gameOver = false;
    }

    public void dealInitialCards() {
        playerHand.addCard(deck.deal());
        playerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
    }

    public void hit() {
        playerHand.addCard(deck.deal());
        if (getPlayerTotal() > 21) {
            gameOver = true;
        }
    }

    public void stand() {
        while (getDealerTotal() < 17) {
            dealerHand.addCard(deck.deal());
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

        if (playerTot > 21) {
            outcome = BlackJackOutcome.LOSE;
            type = BlackJackBetType.LOSE;
        } else if (dealerTot > 21 || playerTot > dealerTot) {
            if (playerTot == 21 && playerHand.size() == 2) {
                outcome = BlackJackOutcome.BLACKJACK;
                type = BlackJackBetType.BLACKJACK;
                player.deposit(type.getPayout());
            } else {
                outcome = BlackJackOutcome.WIN;
                type = BlackJackBetType.BASE;
                player.deposit(type.getPayout());
            }
        } else if (playerTot == dealerTot) {
            outcome = BlackJackOutcome.PUSH;
            type = BlackJackBetType.PUSH;
            player.deposit(type.getPayout()); // restituisce la puntata
        } else {
            outcome = BlackJackOutcome.LOSE;
            type = BlackJackBetType.LOSE;
        }

        gameOver = true;
        Map<Player, BlackJackOutcomeResult> result = new HashMap<>();
        result.put(player, new BlackJackOutcomeResult(outcome, type));
        return new BlackJackResult(result);
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public int getPlayerTotalCards() {
        return playerHand.size();
    }

    public int getDealerTotalCards() {
        return dealerHand.size();
    }

    @Override
    public Boolean isOver() {
        return gameOver;
    }

    @Override
    public Boolean playAgain() {
        gameOver = false;
        return true;    
    }

    public int getPlayerTotal() {
        return calculateTotal(playerHand);
    }

    public int getDealerTotal() {
        return calculateTotal(dealerHand);
    }

    // Metodo di supporto per ottenere il valore di una carta
    private int calculateTotal(Hand hand) {
        int total = 0;
        int aceCount = 0;
        
        for (Card card : hand.getCards()) {
            String rank = card.getRank().toString();
            switch (rank) {
                case "A" -> {
                    total += 11;
                    aceCount++;
                }
                case "K", "Q", "J" -> total += 10;
                default -> total += card.getRank().getValue();
            }
        }

        // Adatta gli assi da 11 a 1 se sforiamo
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

}
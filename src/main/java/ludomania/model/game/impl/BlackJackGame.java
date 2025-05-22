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

public class BlackJackGame implements Game<Map<Player, BlackJackOutcomeResult>> {

    private final BlackJackDealer dealer;
    private final BlackJackPlayer player;
    private boolean gameOver;
    
    public BlackJackGame(BlackJackPlayer player) {
        this.player = player;
        Map<Player, Bet> roundBet = new HashMap<>();
        DeckFactory deckFactory = createMultiDeck(6);
        deckFactory.shuffleAllDecks();
        this.dealer = new BlackJackDealer(roundBet, deckFactory);
        this.gameOver = false;
    }

    public void placeBet(double amount) {
        player.makeBet(amount, BlackJackBetType.BASE);
        dealer.getRoundBet().put(player, player.getPlacedBet());
    }

    public void startNewRound() {
        dealer.reset();
        gameOver = false;
    }

    public void dealInitialCards() {
        dealer.extractNewCard(dealer.getPlayer());
        dealer.extractNewCard(dealer.getPlayer());
        dealer.extractNewCard(dealer.getDealer());
        dealer.extractNewCard(dealer.getDealer());
    }

    public void hit() {
        dealer.extractNewCard(dealer.getPlayer());
        if (getPlayerTotal() > 21) {
            gameOver = true;
        }
    }

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

        if (playerTot > 21) {
            outcome = BlackJackOutcome.LOSE;
            type = BlackJackBetType.LOSE;
        } else if (dealerTot > 21 || playerTot > dealerTot) {
            if (playerTot == 21 && dealer.getPlayer().size() == 2) {
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

        // Costruisci il risultato finale passando i risultati a checkBets
        dealer.checkBets(new BlackJackResult(outcomes));

        // Restituisci il risultato originale come CounterResult
        return new BlackJackResult(outcomes);
    }

    public Hand getPlayerHand() {
        return dealer.getPlayer();
    }

    public Hand getDealerHand() {
        return dealer.getDealer();
    }

    public int getPlayerTotalCards() {
        return dealer.getPlayer().size();
    }

    public int getDealerTotalCards() {
        return dealer.getDealer().size();
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
        return calculateTotal(dealer.getPlayer());
    }

    public int getDealerTotal() {
        return calculateTotal(dealer.getDealer());
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

    private DeckFactory createMultiDeck(int numDecks) {
        DeckFactory factory = new DeckFactory();
        for (int i = 0; i < numDecks; i++) {
            Deck tmp = new Deck();
            factory.addDeck(tmp);
        }

        return factory;
    }

    public Double getPlayerFinance() {
        return player.getBalance();
    }
}
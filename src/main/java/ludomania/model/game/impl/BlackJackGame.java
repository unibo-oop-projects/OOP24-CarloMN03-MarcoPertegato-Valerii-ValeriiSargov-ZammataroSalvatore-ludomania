package ludomania.model.game.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.impl.BlackJackDealer;
import ludomania.model.player.api.Player;

public class BlackJackGame {

    private final BlackJackDealer dealer;
    private final Map<Player, Hand> playerHands = new HashMap<>();
    private BlackJackResult lastResult;

    public BlackJackGame(Map<Player, Bet> bets, DeckFactory deckFactory) {
        this.dealer = new BlackJackDealer(bets, deckFactory);
    }

    public BlackJackResult playRound() {
        // 1. Reset
        dealer.reset();
        playerHands.clear();

        // 2. Deal initial cards
        dealer.dealInitialCards(playerHands);

        // 3. Player turns
        for (Map.Entry<Player, Hand> entry : playerHands.entrySet()) {
            Hand hand = entry.getValue();

            // Simple strategy: hit until 17 or more
            while (BlackJackDealer.calculateBestScore(hand) < 17) {
                hand.addCard(dealer.drawCard());
            }
        }

        // 4. Dealer plays
        dealer.dealerPlay();

        // 5. Determine result
        lastResult = dealer.declareResult(playerHands);

        // 6. Evaluate bets
        Map<Player, Double> winnings = dealer.checkBets(lastResult);

        // 7. Pay out
        for (Map.Entry<Player, Double> entry : winnings.entrySet()) {
            Player player = entry.getKey();
            double payout = entry.getValue();
            player.deposit(payout);
        }

        return lastResult;
    }

    public BlackJackResult getLastResult() {
        return lastResult;
    }

    public Hand getDealerHand() {
        return dealer.getDealerHand();
    }

    public Map<Player, Hand> getPlayerHands() {
        return playerHands;
    }
}

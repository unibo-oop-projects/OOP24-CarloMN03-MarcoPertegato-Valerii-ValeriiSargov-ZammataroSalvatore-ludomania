package ludomania.model.croupier.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.impl.BlackJackResult;
import ludomania.model.player.api.Player;

public class BlackJackDealer extends CardDealer<BlackJackBetType> {

    private static final int BLACKJACK = 21;
    private static final int DEALER_HIT_LIMIT = 17;

    private Hand dealerHand;
    private int dealerTotal;

    public BlackJackDealer(Map<Player, Bet> roundBet, DeckFactory decks) {
        super(roundBet, decks);
        dealerHand = new Hand();
        dealerTotal = 0;
    }

    public void reset() {
        dealerHand = new Hand();
        dealerTotal = 0;
        clearRound();
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public int getDealerTotal() {
        return dealerTotal;
    }

    public void dealInitialCards(Map<Player, Hand> playerHands) {
        for (Player player : roundBet.keySet()) {
            Hand hand = new Hand();
            hand.addCard(drawCard());
            hand.addCard(drawCard());
            playerHands.put(player, hand);
        }
        dealerHand.addCard(drawCard());
        dealerHand.addCard(drawCard());
        updateDealerTotal();
    }

    private void updateDealerTotal() {
        dealerTotal = calculateBestScore(dealerHand);
    }

    public void dealerPlay() {
        while (dealerTotal < DEALER_HIT_LIMIT) {
            dealerHand.addCard(drawCard());
            updateDealerTotal();
        }
    }

    public static int calculateBestScore(Hand hand) {
        int total = 0;
        int aces = 0;

        for (Card card : hand.getCards()) {
            int value = card.getRank().getValue();
            if (value > 10) {
                value = 10;
            } else if (value == 1) {
                aces++;
                value = 11;
            }
            total += value;
        }

        while (total > BLACKJACK && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public BlackJackResult declareResult(Map<Player, Hand> playerHands) {
        Map<Player, Integer> playerScores = new HashMap<>();
        for (Map.Entry<Player, Hand> entry : playerHands.entrySet()) {
            int score = calculateBestScore(entry.getValue());
            playerScores.put(entry.getKey(), score);
        }
        return new BlackJackResult(playerScores, dealerTotal);
    }

    @Override
    public Map<Player, Double> checkBets(CounterResult<BlackJackBetType> result) {
        if (!(result instanceof BlackJackResult)) {
            throw new IllegalArgumentException("Invalid result type for BlackJackDealer");
        }

        Map<Player, Double> winners = new HashMap<>();
        BlackJackResult bjResult = (BlackJackResult) result;

        for (Player player : roundBet.keySet()) {
            Bet bet = roundBet.get(player);
            BlackJackBetType betType = (BlackJackBetType) bet.getType();
            int playerScore = bjResult.getPlayerScores().getOrDefault(player, 0);
            
            if (playerScore > BLACKJACK) {
                // Player busts
                winners.put(player, 0.0);
            } else if (dealerTotal > BLACKJACK || playerScore > dealerTotal) {
                // Player wins
                if (betType == BlackJackBetType.BLACKJACK && playerScore == BLACKJACK) {
                    winners.put(player, bet.getValue() * betType.getPayout());
                } else {
                    winners.put(player, bet.evaluate());
                }
            } else if (playerScore == dealerTotal) {
                // Push
                winners.put(player, bet.getValue());
            } else {
                // Player loses
                winners.put(player, 0.0);
            }
        }

        return winners;
    }
}

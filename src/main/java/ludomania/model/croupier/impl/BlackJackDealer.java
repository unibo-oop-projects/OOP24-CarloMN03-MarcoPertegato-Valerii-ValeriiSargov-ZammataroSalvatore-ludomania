package ludomania.model.croupier.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.impl.BlackJackOutcomeResult;
import ludomania.model.game.impl.BlackJackResult;
import ludomania.model.player.api.Player;

public class BlackJackDealer extends CardDealer<Map<Player, BlackJackOutcomeResult>> {

    private static final int MAX_HAND_VALUE = 17;

    private Hand dealer;
    private Hand player;
    private int dealerTot;
    private int playerTot;

    public BlackJackDealer(Map<Player, Bet> roundBet, DeckFactory decks) {
        super(roundBet, decks);
        this.dealer = new Hand();
        this.player = new Hand();
        this.dealerTot = 0;
        this.playerTot = 0;
    }

    @Override
    public Map<Player, Double> checkBets(CounterResult<Map<Player, BlackJackOutcomeResult>> result) {
        if (!(result instanceof BlackJackResult)) {
            throw new IllegalArgumentException("Invalid result type for BlackJackDealer");
        }

        Map<Player, Double> winners = new HashMap<>();
        Map<Player, BlackJackOutcomeResult> outcomes = result.getResult();

        for (Map.Entry<Player, BlackJackOutcomeResult> entry : outcomes.entrySet()) {
            Player currentPlayer = entry.getKey();
            System.out.println("Saldo PRIMA: " + currentPlayer.wallet.getMoney());
            BlackJackOutcomeResult outcomeResult = entry.getValue();
            
            if (!roundBet.containsKey(currentPlayer)) {
                System.out.println("Nessuna bet trovata per il player: " + currentPlayer);
                continue;
            }
            Bet bet = roundBet.get(currentPlayer);
            

            switch (outcomeResult.getOutcome()) {
                case WIN -> {
                    winners.put(currentPlayer, bet.evaluate());
                    currentPlayer.deposit(bet.evaluate());
                }
                case BLACKJACK -> {
                    winners.put(currentPlayer, bet.evaluate());
                    currentPlayer.deposit(bet.evaluate());
                }
                case PUSH -> {
                    winners.put(currentPlayer, bet.getValue());
                    currentPlayer.deposit(bet.evaluate());
                }
                case LOSE -> currentPlayer.withdraw(bet.evaluate());
                default -> currentPlayer.withdraw(bet.evaluate());
            }
        }
        
        return winners;
    }

    public void reset() {
        dealer = new Hand();
        player = new Hand();
        dealerTot = 0;
        playerTot = 0;
        clearRound();
    }

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

    public Map<Player, Bet> getRoundBet() {
        return this.roundBet;
    }

    public void increaseDealerTot(int amount) {
        dealerTot += amount;
    }

    public void increasePlayerTot(int amount) {
        playerTot += amount;
    }

    public Card extractNewCard(Hand hand) {
        Card extractedCard = drawCard();
        hand.addCard(extractedCard);
        return extractedCard;
    }

    public boolean isEnough(int handTot) {
        return handTot >= MAX_HAND_VALUE;
    }
}

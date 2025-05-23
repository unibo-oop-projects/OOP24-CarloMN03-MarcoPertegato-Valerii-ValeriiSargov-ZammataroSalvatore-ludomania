package ludomania.model.croupier.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.api.CardDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.impl.BlackJackOutcomeResult;
import ludomania.model.game.impl.BlackJackResult;
import ludomania.model.player.api.Player;

public class BlackJackDealer extends CardDealer<Map<Player, BlackJackOutcomeResult>> {

    private static final int MAX_HAND_VALUE = 17;
    private static final int NUMBER_OF_DEKS = 6;

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
            BlackJackOutcomeResult outcomeResult = entry.getValue();
            
            if (!roundBet.containsKey(currentPlayer)) {
                System.out.println("Nessuna bet trovata per il player: " + currentPlayer);
                continue;
            }
            Bet bet = new BlackJackBet(roundBet.get(currentPlayer).getValue(), (BlackJackBetType) roundBet.get(currentPlayer).getType());

            switch (outcomeResult.getOutcome()) {
                case WIN -> {
                    winners.put(currentPlayer, bet.evaluate());
                    deposit(currentPlayer, bet.evaluate());
                }
                case BLACKJACK -> {
                    double blackjackWin = bet.getValue() * BlackJackBetType.BLACKJACK.getPayout();
                    winners.put(currentPlayer, blackjackWin);
                    deposit(currentPlayer, blackjackWin);
                }
                case PUSH -> {
                    winners.put(currentPlayer, bet.getValue());
                    deposit(currentPlayer, bet.getValue());
                }
                case LOSE -> deposit(currentPlayer, 0.0);
                default -> deposit(currentPlayer, 0.0);
            }
        }
        clearRound();
        return winners;
    }

    private void deposit(Player player, Double value) {
        player.deposit(value);
    }

    public void reset() {
        dealer = new Hand();
        player = new Hand();
        dealerTot = 0;
        playerTot = 0;
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
        return roundBet;
    }

    public void increaseDealerTot(int amount) {
        dealerTot += amount;
    }

    public void increasePlayerTot(int amount) {
        playerTot += amount;
    }

    public Card extractNewCard(Hand hand) {
        if(super.needToResetAllDecks()) {
            initDeck(NUMBER_OF_DEKS);
        }
        Card extractedCard = drawCard();
        hand.addCard(extractedCard);
        return extractedCard;
    }

    public boolean isEnough(int handTot) {
        return handTot >= MAX_HAND_VALUE;
    }
}

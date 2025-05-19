package ludomania.model.game.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.croupier.impl.BlackJackDealer;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.api.Player;

public class BlackJackGame implements Game<BlackJackResult> {

    private final BlackJackDealer dealer;
    private final Map<Player, Bet> playerBets;
    private boolean gameOver = false;

    public BlackJackGame(Map<Player, Bet> playerBets, DeckFactory deckFactory) {
        this.playerBets = playerBets;
        this.dealer = new BlackJackDealer(playerBets, deckFactory);
    }

    @Override
    public CounterResult<BlackJackResult> runGame() {
        dealer.reset();

        Map<Player, BlackJackOutcomeResult> results = new HashMap<>();

        for (Player player : playerBets.keySet()) {
            Hand playerHand = dealer.getPlayer();
            Hand dealerHand = dealer.getDealer();
            int playerTot = 0;
            int dealerTot = 0;

            //Il player pesca le due carte iniziali
            playerTot += playerCardValue(dealer.extractNewCard(playerHand));
            playerTot += playerCardValue(dealer.extractNewCard(playerHand));
            dealer.increasePlayerTot(playerTot);

            //Il dealer pesca le due carte iniziali
            dealerTot += playerCardValue(dealer.extractNewCard(dealerHand));
            dealerTot += playerCardValue(dealer.extractNewCard(dealerHand));
            dealer.increaseDealerTot(dealerTot);

            //Logica di pesca del dealer
            while(!dealer.isEnough(dealerTot)) {
                Card newCard = dealer.extractNewCard(dealerHand);
                dealerTot += playerCardValue(newCard);
                dealer.increaseDealerTot(playerCardValue(newCard));
                if (dealerTot > 21) break;
            }

            //Logica di pesca del player (!!!TEMPORANEA!!!)
            while(!dealer.isEnough(playerTot)) {
                Card newCard = dealer.extractNewCard(playerHand);
                playerTot += playerCardValue(newCard);
                dealer.increasePlayerTot(playerCardValue(newCard));
                if (playerTot > 21) break;
            }

            //Valutazione della vincita/perdita/pareggio
            if (playerTot > 21) {
                results.put(player, new BlackJackOutcomeResult(BlackJackOutcome.LOSE, BlackJackBetType.BASE)); //DA CAMBIARE BASE CON LA PUNTATA SCELTA DAL PLAYER
            } else if (dealerTot > 21 || playerTot > dealerTot) {
                if (playerTot == 21 && playerHand.size() == 2) {
                    results.put(player, new BlackJackOutcomeResult(BlackJackOutcome.BLACKJACK, BlackJackBetType.BASE)); //DA CAMBIARE BASE CON LA PUNTATA SCELTA DAL PLAYER
                } else {
                    results.put(player, new BlackJackOutcomeResult(BlackJackOutcome.WIN, BlackJackBetType.BASE)); //DA CAMBIARE BASE CON LA PUNTATA SCELTA DAL PLAYER
                } 
            } else if (playerTot == dealerTot) {
                results.put(player, new BlackJackOutcomeResult(BlackJackOutcome.PUSH, BlackJackBetType.BASE)); //DA CAMBIARE BASE CON LA PUNTATA SCELTA DAL PLAYER
            } else {
                results.put(player, new BlackJackOutcomeResult(BlackJackOutcome.LOSE, BlackJackBetType.BASE)); //DA CAMBIARE BASE CON LA PUNTATA SCELTA DAL PLAYER
            }
        }

        gameOver = true;
        return null; //SISTEMARE
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

    // Metodo di supporto per ottenere il valore di una carta
    private int playerCardValue(Card card) {
        String rank = card.getRank().toString(); // Es: "2", "J", "A"
        return switch (rank) {
            case "A" -> 11;
            case "K", "Q", "J" -> 10;
            default -> Integer.parseInt(rank);
        };
    }

}
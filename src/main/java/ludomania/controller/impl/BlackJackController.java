package ludomania.controller.impl;

import java.util.HashMap;
import java.util.Map;

import io.lyuda.jcards.DeckFactory;
import io.lyuda.jcards.Hand;
import javafx.scene.Parent;
import ludomania.controller.api.Controller;
import ludomania.core.api.AudioManager;
import ludomania.core.api.SceneManager;
import ludomania.handler.BlackJackHandler;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.impl.BlackJackBetType;
import ludomania.model.game.impl.BlackJackGame;
import ludomania.model.game.impl.BlackJackResult;
import ludomania.model.player.api.Player;
import ludomania.model.player.impl.BlackJackPlayer;
import ludomania.model.wallet.impl.WalletImpl;

public class BlackJackController implements BlackJackHandler, Controller {

    private final Map<String, Player> players = new HashMap<>();
    private final Map<Player, Bet> currentBets = new HashMap<>();
    private BlackJackGame game;
    private final DeckFactory deckFactory;
    private Parent view;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;

    public BlackJackController(SceneManager sceneManager, AudioManager audioManager) {
        this.deckFactory = new DeckFactory();
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;

        // Setup iniziale (può essere reso dinamico)
        Player player1 = new BlackJackPlayer(new WalletImpl(1000.0));
        players.put("222", player1);
    }

    @Override
    public void handleBet(double amount, String playerId) {
        Player player = players.get(playerId);
        if (player == null || player.getAmount() < amount) {
            System.out.println("Scommessa non valida");
            return;
        }

        player.withdraw(amount); // Scala soldi
        currentBets.put(player, player.makeBet(amount, BlackJackBetType.BASE));
        System.out.println("Scommessa piazzata: " + amount + "$");
    }

    @Override
    public void handleDrawCard(String playerId) {
        // Se il gioco non è stato inizializzato, avvia un round
        if (game == null) {
            System.out.println("Avvia prima un round!");
            return;
        }

        Player player = players.get(playerId);
        Hand hand = game.getPlayerHands().get(player);
        if (hand == null) {
            System.out.println("Nessuna mano trovata per il giocatore.");
            return;
        }

        hand.addCard(game.getDealerHand().getCards().getLast()); // pescata dal mazzo
        System.out.println("Carta pescata per " + playerId);
    }

    @Override
    public void handleCancel(String playerId) {
        Player player = players.get(playerId);
        Bet bet = currentBets.remove(player);
        if (bet != null) {
            player.deposit(bet.getValue());
            System.out.println("Scommessa annullata per " + playerId);
        }
    }

    @Override
    public void handleExit() {
        System.out.println("Uscita dal gioco richiesta.");
        // Qui ci sarebbe il cambio scena in un'app completa
        sceneManager.switchToMainMenu();
        audioManager.initialize();
    }

    @Override
    public void handleStartRound() {
        if (currentBets.isEmpty()) {
            System.out.println("Nessuna scommessa piazzata!");
            return;
        }

        game = new BlackJackGame(currentBets, deckFactory);
        BlackJackResult result = game.playRound();

        // Log di debug
        game.getPlayerHands().forEach((player, hand) -> System.out.println(player.toString() + " ha: " + player.getAmount()));
        System.out.println("Esito: " + result);

        // Pulizia
        currentBets.clear();
    }

    @Override
    public Parent getView() {
        if (view == null) {
            view = new ludomania.view.blackjack.BlackJackViewBuilder(this).build();
        }
        return view;
    }
}



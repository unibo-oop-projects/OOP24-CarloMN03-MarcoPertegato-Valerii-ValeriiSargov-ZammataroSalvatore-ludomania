package ludomania.model.game.roulette;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import ludomania.model.Pair;
import ludomania.model.bet.RouletteBetType;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteCroupier;
import ludomania.model.croupier.roulette.RouletteWheel;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.RoulettePlayer;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.impl.WalletImpl;

import java.util.*;
import java.util.stream.Collectors;

public class RouletteGameManager {
    private final RouletteCroupier rouletteCroupier;
    private final Map<String, RoulettePlayer> players;
    private RoulettePlayer currentPlayer;
    private CounterResult<Pair<Integer, RouletteColor>> lastResult;

    public RouletteGameManager(RouletteCroupier rouletteCroupier, Set<RoulettePlayer> players) {
        this.rouletteCroupier = rouletteCroupier;
        this.players = players == null ? new HashMap<>() : new HashMap<>(players.stream().collect(Collectors.toMap(Player::getUsername, p -> p)));

        if (players != null && !players.isEmpty()) {
            Optional<RoulettePlayer> firstPlayer = players.stream().findFirst();
            this.currentPlayer = firstPlayer.orElseGet(() -> {
                RoulettePlayer demoPlayer = new RoulettePlayer(new WalletImpl(1000.0), "DemoPlayer");
                this.players.put(demoPlayer.getUsername(), demoPlayer);
                return demoPlayer;
            });
        } else {
            RoulettePlayer demoPlayer = new RoulettePlayer(new WalletImpl(1000.0), "DemoPlayer");
            this.players.put(demoPlayer.getUsername(), demoPlayer);
            this.currentPlayer = demoPlayer;
        }
    }

    public RouletteGameManager(RouletteCroupier rouletteCroupier, RoulettePlayer player) {
        this.rouletteCroupier = rouletteCroupier;

        RoulettePlayer singlePlayer = player == null ? new RoulettePlayer(new WalletImpl(1000.0), "DemoPlayer") : player;
        this.players = new HashMap<>();
        this.players.put(singlePlayer.getUsername(), singlePlayer);
        this.currentPlayer = singlePlayer;
    }

    public RouletteGameManager(RouletteCroupier rouletteCroupier) {
        this.rouletteCroupier = rouletteCroupier;
        RoulettePlayer demoPlayer = new RoulettePlayer(new WalletImpl(1000.0), "DemoPlayer");
        this.players = new HashMap<>();
        this.players.put(demoPlayer.getUsername(), demoPlayer);
        this.currentPlayer = demoPlayer;
    }

    public CounterResult<Pair<Integer, RouletteColor>> runGame() {
        this.lastResult = new CounterResult<>(RouletteWheel.random());
        return this.lastResult;
    }

    public void evaluateGame() {
        Map<Player, Double> winners =  this.rouletteCroupier.checkBets(this.lastResult);

        winners.forEach((player, amount) -> {
            if (this.players.containsKey(player.getUsername())) {
                this.players.get(player.getUsername()).deposit(amount);
            }
        });

        this.rouletteCroupier.clearRound();
    }

    public void setCurrentPlayer(String username) {
        if (this.players.containsKey(username)) {
            this.currentPlayer = this.players.get(username);
        }
    }

    public void pleinBet(MouseEvent event) throws IllegalArgumentException {
        if (event.getSource() instanceof Button button) {
            int id = Integer.parseInt(button.getId());
            this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.PLEIN, Set.of(id)));
            this.currentPlayer.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void chevalBet(MouseEvent event) throws IllegalArgumentException {
        if (event.getSource() instanceof Separator separator) {
            String id = separator.getId();
            Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
            this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.CHEVAL, Set.of(choices)));
            this.currentPlayer.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void carreBet(MouseEvent event) throws IllegalArgumentException {
        if (event.getSource() instanceof Button button) {
            String id = button.getId();
            Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
            this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.CARRE, Set.of(choices)));
            this.currentPlayer.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void colonneBet(MouseEvent event) throws IllegalArgumentException {
        if (event.getSource() instanceof Button button) {
            String id = button.getId();
            Set<Object> choices;
            switch (id.charAt(0)) {
                case 'b' -> choices = RouletteWheel.firstColonne();
                case 'm' -> choices = RouletteWheel.secondColonne();
                case 't' -> choices = RouletteWheel.thirdColonne();
                default -> throw new IllegalArgumentException("Wrong column name: " + id.charAt(0));
            }
            this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.COLONNE, Set.of(choices)));
            this.currentPlayer.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void noirBet() {
        this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.NOIR, Set.of(RouletteColor.NOIR)));
        this.currentPlayer.resetBetAmount();
    }

    public void rougeBet() {
        this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.ROUGE, Set.of(RouletteColor.ROUGE)));
        this.currentPlayer.resetBetAmount();
    }

    public void pairBet() {
        this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.PAIR, Set.of()));
        this.currentPlayer.resetBetAmount();
    }

    public void impairBet() {
        this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.IMPAIR, Set.of()));
        this.currentPlayer.resetBetAmount();
    }

    public void passeBet() {
        this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.PASSE, Set.of()));
        this.currentPlayer.resetBetAmount();
    }

    public void manqueBet() {
        this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.MANQUE, Set.of()));
        this.currentPlayer.resetBetAmount();
    }

    public void douzaineBet(MouseEvent event) throws IllegalArgumentException {
        if (event.getSource() instanceof Button button) {
            String id = button.getId();
            Set<Object> choices;
            switch (id.charAt(0)) {
                case 'p' -> choices = RouletteWheel.firstDouzaine();
                case 'm' -> choices = RouletteWheel.secondDouzaine();
                case 'd' -> choices = RouletteWheel.thirdDouzaine();
                default -> throw new IllegalArgumentException("Button is not clicked");
            }
            this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.DOUZAINE, Set.of(choices)));
            this.currentPlayer.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public Double addBetAmount(Integer amount) {
        if (this.currentPlayer.withdraw(Double.valueOf(amount))) {
            this.currentPlayer.addBetAmount(Double.valueOf(amount));
        }
        return this.currentPlayer.getBetAmount();
    }

    public Double getPlayerBalance() {
        return this.currentPlayer.getBalance();
    }
}

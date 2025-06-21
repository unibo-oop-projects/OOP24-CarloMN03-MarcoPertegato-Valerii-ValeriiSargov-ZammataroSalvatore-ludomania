package ludomania.model.game.roulette;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import ludomania.model.Pair;
import ludomania.model.bet.RouletteBetType;
import ludomania.model.bet.api.Bet;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteCroupier;
import ludomania.model.croupier.roulette.RouletteWheel;
import ludomania.model.game.impl.CounterResult;
import ludomania.model.player.RoulettePlayer;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.impl.WalletImpl;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the core function that manages all behaviour aspects concerning Roulette game
 */
public class RouletteGameManager {
    private final String DEFAULT_PLAYER_USERNAME = "DemoPlayer";

    private final RouletteCroupier rouletteCroupier;
    private final Map<String, RoulettePlayer> players;
    private RoulettePlayer currentPlayer;
    private CounterResult<Pair<Integer, RouletteColor>> lastResult;

    public RouletteGameManager(final RouletteCroupier rouletteCroupier, final Set<RoulettePlayer> players) {
        this.rouletteCroupier = rouletteCroupier;
        if (players == null) {
            this.players = new HashMap<>();
        } else {
            this.players = new HashMap<>(players.stream().collect(Collectors.toMap(Player::getUsername, p -> p)));
        }

        if (players != null && !players.isEmpty()) {
            final Optional<RoulettePlayer> firstPlayer = players.stream().findFirst();
            this.currentPlayer = firstPlayer.orElseGet(() -> {
                RoulettePlayer demoPlayer = new RoulettePlayer(new WalletImpl(1000.0), this.DEFAULT_PLAYER_USERNAME);
                this.players.put(demoPlayer.getUsername(), demoPlayer);
                return demoPlayer;
            });
        } else {
            final RoulettePlayer demoPlayer = new RoulettePlayer(new WalletImpl(1000.0), this.DEFAULT_PLAYER_USERNAME);
            this.players.put(demoPlayer.getUsername(), demoPlayer);
            this.currentPlayer = demoPlayer;
        }
    }

    public RouletteGameManager(final RouletteCroupier rouletteCroupier, final RoulettePlayer player) {
        this.rouletteCroupier = rouletteCroupier;

        final RoulettePlayer singlePlayer = player == null ? new RoulettePlayer(new WalletImpl(1000.0), this.DEFAULT_PLAYER_USERNAME) : player;
        this.players = new HashMap<>();
        this.players.put(singlePlayer.getUsername(), singlePlayer);
        this.currentPlayer = singlePlayer;
    }

    public RouletteGameManager(final RouletteCroupier rouletteCroupier) {
        this.rouletteCroupier = rouletteCroupier;
        final RoulettePlayer demoPlayer = new RoulettePlayer(new WalletImpl(1000.0), this.DEFAULT_PLAYER_USERNAME);
        this.players = new HashMap<>();
        this.players.put(demoPlayer.getUsername(), demoPlayer);
        this.currentPlayer = demoPlayer;
    }

    public CounterResult<Pair<Integer, RouletteColor>> runGame() {
        this.lastResult = new CounterResult<>(RouletteWheel.random());
        return this.lastResult;
    }

    public void evaluateGame() {
        final Map<Player, Double> winners =  this.rouletteCroupier.checkBets(this.lastResult);

        winners.forEach((player, amount) -> {
            if (this.players.containsKey(player.getUsername())) {
                this.players.get(player.getUsername()).deposit(amount);
            }
        });

        this.rouletteCroupier.clearRound();
    }

    public boolean checkGameOver() {
        return this.currentPlayer.getBalance() == 0;
    }

    public void setCurrentPlayer(final String username) {
        if (this.players.containsKey(username)) {
            this.currentPlayer = this.players.get(username);
        }
    }

    public void pleinBet(final MouseEvent event) {
        try {
            if (event.getSource() instanceof Button button) {
                final int id = Integer.parseInt(button.getId());
                this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.PLEIN, Set.of(id)));
                this.currentPlayer.resetBetAmount();
            } else {
                this.currentPlayer.restoreBalance();
            }
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void chevalBet(final MouseEvent event) {
        try {
            if (event.getSource() instanceof Separator separator) {
                final String id = separator.getId();
                Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
                this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.CHEVAL, Set.of(choices)));
                this.currentPlayer.resetBetAmount();
            } else {
                this.currentPlayer.restoreBalance();
            }
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void carreBet(final MouseEvent event) {
        try {
            if (event.getSource() instanceof Button button) {
                final String id = button.getId();
                Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
                this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.CARRE, Set.of(choices)));
                this.currentPlayer.resetBetAmount();
            } else {
                this.currentPlayer.restoreBalance();
            }
        } catch (NumberFormatException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void colonneBet(final MouseEvent event) {
        try {
            if (event.getSource() instanceof Button button) {
                final String id = button.getId();
                Set<Object> choices;
                switch (id.charAt(0)) {
                    case 'b' -> choices = RouletteWheel.firstColonne();
                    case 'm' -> choices = RouletteWheel.secondColonne();
                    case 't' -> choices = RouletteWheel.thirdColonne();
                    default -> throw new IllegalArgumentException("Wrong column name: " + id.charAt(0));
                }
                this.rouletteCroupier.addBet(this.currentPlayer, this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.COLONNE, choices));
                this.currentPlayer.resetBetAmount();
            } else {
                this.currentPlayer.restoreBalance();
            }
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void noirBet() {
        try {
            this.rouletteCroupier.addBet(
                    this.currentPlayer,
                    this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.NOIR, Set.of(RouletteColor.NOIR)));

            this.currentPlayer.resetBetAmount();
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void rougeBet() {
        try {
            this.rouletteCroupier.addBet(
                    this.currentPlayer,
                    this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.ROUGE, Set.of(RouletteColor.ROUGE)));

            this.currentPlayer.resetBetAmount();
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void pairBet() {
        try {
            this.rouletteCroupier.addBet(
                    this.currentPlayer,
                    this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.PAIR, Set.of()));
            this.currentPlayer.resetBetAmount();
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void impairBet() {
        try {
            this.rouletteCroupier.addBet(
                    this.currentPlayer,
                    this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.IMPAIR, Set.of()));

            this.currentPlayer.resetBetAmount();
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void passeBet() {
        try {
            this.rouletteCroupier.addBet(
                    this.currentPlayer,
                    this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.PASSE, Set.of()));
            this.currentPlayer.resetBetAmount();
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void manqueBet() {
        try {
            this.rouletteCroupier.addBet(
                    this.currentPlayer,
                    this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.MANQUE, Set.of()));

            this.currentPlayer.resetBetAmount();
        } catch (IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public void douzaineBet(final MouseEvent event) {
        try {
            if (event.getSource() instanceof Button button) {
                final String id = button.getId();
                Set<Object> choices;
                switch (id.charAt(0)) {
                    case 'p' -> choices = RouletteWheel.firstDouzaine();
                    case 'm' -> choices = RouletteWheel.secondDouzaine();
                    case 'd' -> choices = RouletteWheel.thirdDouzaine();
                    default -> throw new IllegalArgumentException("Button is not clicked");
                }
                this.rouletteCroupier.addBet(
                        this.currentPlayer,
                        this.currentPlayer.makeBet(this.currentPlayer.getBetAmount(), RouletteBetType.DOUZAINE, choices));
                this.currentPlayer.resetBetAmount();
            } else {
                this.currentPlayer.restoreBalance();
            }
        } catch (final IllegalArgumentException e) {
            this.currentPlayer.restoreBalance();
        }
    }

    public Double addBetAmount(final Integer amount) {
        if (this.currentPlayer.withdraw(Double.valueOf(amount))) {
            this.currentPlayer.addBetAmount(Double.valueOf(amount));
        }
        return this.currentPlayer.getBetAmount();
    }

    public Double getPlayerBalance() {
        return this.currentPlayer.getBalance();
    }

    public List<Pair<Player, Bet>> getBets() {
        return this.rouletteCroupier.getRoundBet();
    }
}

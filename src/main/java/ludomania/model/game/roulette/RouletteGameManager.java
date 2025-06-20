package ludomania.model.game.roulette;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import ludomania.model.bet.RouletteBetType;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteCroupier;
import ludomania.model.croupier.roulette.RouletteWheel;
import ludomania.model.player.RoulettePlayer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class RouletteGameManager {
    private final RouletteCroupier rouletteCroupier;
    private final RoulettePlayer player;

    public RouletteGameManager(RouletteCroupier rouletteCroupier, RoulettePlayer player) {
        this.rouletteCroupier = rouletteCroupier;
        this.player = player;
    }

    public void pleinBet(MouseEvent event) {
        if (event.getSource() instanceof Button button) {
            int id = Integer.parseInt(button.getId());
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.PLEIN, Set.of(id)));
            this.player.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void chevalBet(MouseEvent event) {
        if (event.getSource() instanceof Separator separator) {
            String id = separator.getId();
            Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.CHEVAL, choices));
            this.player.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void carreBet(MouseEvent event) {
        if (event.getSource() instanceof Button button) {
            String id = button.getId();
            Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.CARRE, choices));
            this.player.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void colonneBet(MouseEvent event) {
        if (event.getSource() instanceof Button button) {
            String id = button.getId();
            Set<Object> choices;
            switch (id.charAt(0)) {
                case 'b' -> choices = RouletteWheel.firstColonne();
                case 'm' -> choices = RouletteWheel.secondColonne();
                case 't' -> choices = RouletteWheel.thirdColonne();
                default -> throw new IllegalArgumentException("Wrong column name: " + id.charAt(0));
            }
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.COLONNE, choices));
            this.player.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void noirBet(MouseEvent event) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.NOIR, Set.of(RouletteColor.NOIR)));
            this.player.resetBetAmount();
    }

    public void rougeBet(MouseEvent event) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.ROUGE, Set.of(RouletteColor.ROUGE)));
            this.player.resetBetAmount();
    }

    public void pairBet() {
        this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.PAIR, Set.of()));
        this.player.resetBetAmount();
    }

    public void impairBet() {
        this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.IMPAIR, Set.of()));
        this.player.resetBetAmount();
    }

    public void passeBet() {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.PASSE, Set.of()));
            this.player.resetBetAmount();
    }

    public void manqueBet() {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.MANQUE, Set.of()));
            this.player.resetBetAmount();
    }

    public void douzaineBet(MouseEvent event) {
        if (event.getSource() instanceof Button button) {
            String id = button.getId();
            Set<Object> choices;
            switch (id.charAt(0)) {
                case 'p' -> choices = RouletteWheel.firstDouzaine();
                case 'm' -> choices = RouletteWheel.secondDouzaine();
                case 'd' -> choices = RouletteWheel.thirdDouzaine();
                default -> throw new IllegalArgumentException("Button is not clicked");
            }
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(this.player.getBetAmount(), RouletteBetType.DOUZAINE, choices));
            this.player.resetBetAmount();
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public Double addBetAmount(Integer amount) {
        if (this.player.withdraw(Double.valueOf(amount))) {
            this.player.addBetAmount(Double.valueOf(amount));
        }
        return this.player.getBetAmount();
    }

    public Double getPlayerBalance() {
        return this.player.getBalance();
    }
}

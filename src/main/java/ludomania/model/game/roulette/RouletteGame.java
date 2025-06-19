package ludomania.model.game.roulette;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import ludomania.controller.roulette.RouletteController;
import ludomania.core.api.SceneManager;
import ludomania.model.bet.RouletteBetType;
import ludomania.model.croupier.roulette.RouletteColor;
import ludomania.model.croupier.roulette.RouletteCroupier;
import ludomania.model.croupier.roulette.RouletteWheel;
import ludomania.model.game.api.CounterResult;
import ludomania.model.game.api.Game;
import ludomania.model.player.RoulettePlayer;
import ludomania.model.wallet.impl.WalletImpl;
import ludomania.view.ViewBuilder;
import ludomania.view.roulette.RouletteViewBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RouletteGame implements Game {

    private final ViewBuilder viewBuilder;
    private final RouletteCroupier rouletteCroupier;
    private final RoulettePlayer player;

    public RouletteGame(RouletteController controller, final SceneManager sceneManager) {
        this.viewBuilder = new RouletteViewBuilder(
                controller, sceneManager.getLanguageManager(), sceneManager.getImageProvider());

        this.rouletteCroupier = new RouletteCroupier();
        this.player = new RoulettePlayer(new WalletImpl(10000.0), "RoulettePlayer");
    }

    @Override
    public CounterResult<Integer> runGame() {
        return null;
    }

    public Parent getView() {
        return viewBuilder.build();
    }

    public void pleinBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button clickedButton) {
            int id = Integer.parseInt(clickedButton.getId());
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.PLEIN, Set.of(id)));
        } else {
            throw  new IllegalArgumentException("Button is not clicked");
        }
    }

    public void chevalBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button clickedButton) {
            String id = clickedButton.getId();
            Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.CHEVAL, choices));
        } else {
            throw  new IllegalArgumentException("Button is not clicked");
        }
    }

    public void carreBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button clickedButton) {
            String id = clickedButton.getId();
            Set<Object> choices = Arrays.stream(id.split("-")).map(Integer::parseInt).collect(Collectors.toSet());
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.CARRE, choices));
        } else {
            throw  new IllegalArgumentException("Button is not clicked");
        }
    }

    public void colonneBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button clickedButton) {
            String id = clickedButton.getId();
            Set<Object> choices;
            switch(id.charAt(0)) {
                case 'p' -> {
                    choices = RouletteWheel.firstColonne();
                }
                case 'm' -> {
                    choices = RouletteWheel.secondColonne();
                }
                case 'd' -> {
                    choices = RouletteWheel.thirdColonne();
                }
                default -> throw new IllegalArgumentException("Button is not clicked");
            }
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.COLONNE, choices));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void noirBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.NOIR, Set.of(RouletteColor.NOIR)));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void rougeBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.ROUGE, Set.of(RouletteColor.ROUGE)));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void pairBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.PAIR, Set.of()));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void impairBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.IMPAIR, Set.of()));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void passeBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.PASSE, Set.of()));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void manqueBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button) {
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.MANQUE, Set.of()));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void douzineBet(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Button clickedButton) {
            String id = clickedButton.getId();
            Set<Object> choices;
            switch(id.charAt(0)) {
                case 'p' -> {
                    choices = RouletteWheel.firstDouzaine();
                }
                case 'm' -> {
                    choices = RouletteWheel.secondDouzaine();
                }
                case 'd' -> {
                    choices = RouletteWheel.thirdDouzaine();
                }
                default -> throw new IllegalArgumentException("Button is not clicked");
            }
            this.rouletteCroupier.addBet(this.player, this.player.makeBet(1.0, RouletteBetType.DOUZAINE, choices));
        } else {
            throw new IllegalArgumentException("Button is not clicked");
        }
    }

    public void spinWheel(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void highlightCarre(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void unhighlightCarre(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void glowWheel(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void unglowWheel(MouseEvent event) {
        throw new UnsupportedOperationException();
    }

    public void quitGame() {
        throw new UnsupportedOperationException();
    }

    public void selectAmount(MouseEvent event) {
        throw new UnsupportedOperationException();
    }
}

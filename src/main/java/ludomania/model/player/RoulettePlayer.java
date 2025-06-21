package ludomania.model.player;

import ludomania.model.bet.RouletteBetFactory;
import ludomania.model.bet.api.Bet;
import ludomania.model.bet.api.BetType;
import ludomania.model.player.api.Player;
import ludomania.model.wallet.api.Wallet;

import java.util.Set;

public class RoulettePlayer extends Player {
    private Double betAmount = 0.0;

    public RoulettePlayer(final Wallet wallet, final String username) {
        super(wallet, username);
    }

    @Override
    public Bet makeBet(final Double amount, final BetType type) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported.");
    }

    public Double getBetAmount() {
        return this.betAmount;
    }

    public void addBetAmount(final Double amount) {
        this.betAmount += amount;
    }

    public void resetBetAmount() {
        this.betAmount = 0.0;
    }

    public void restoreBalance() {
        this.deposit(this.getBetAmount());
        this.resetBetAmount();
    }

    public Bet makeBet(final Double amount, final BetType type, final Set<Object> choice) {
        if (amount >= 0) {
            switch (type.getTypeName()) {
                case "PLEIN" -> {
                    return RouletteBetFactory.pleinBet(choice, amount);
                }
                case "CHEVAL" -> {
                    return RouletteBetFactory.chevalBet(choice, amount);
                }
                case "CARRE" -> {
                    return RouletteBetFactory.carreBet(choice, amount);
                }
                case "DOUZAINE" -> {
                    return RouletteBetFactory.douzaineBet(choice, amount);
                }
                case "COLONNE" -> {
                    return RouletteBetFactory.colonneBet(choice, amount);
                }
                case "PAIR" -> {
                    return RouletteBetFactory.pairBet(choice, amount);
                }
                case "IMPAIR" -> {
                    return RouletteBetFactory.impairBet(choice, amount);
                }
                case "PASSE" -> {
                    return RouletteBetFactory.passeBet(choice, amount);
                }
                case "MANQUE" -> {
                    return RouletteBetFactory.manqueBet(choice, amount);
                }
                case "ROUGE" -> {
                    return RouletteBetFactory.rougeBet(choice, amount);
                }
                case "NOIR" -> {
                    return RouletteBetFactory.noirBet(choice, amount);
                }
                default -> throw new IllegalArgumentException("Invalid bet type " + type);
            }
        } else {
            throw new IllegalArgumentException("Cannot place a bet with amount 0");
        }
    }
}

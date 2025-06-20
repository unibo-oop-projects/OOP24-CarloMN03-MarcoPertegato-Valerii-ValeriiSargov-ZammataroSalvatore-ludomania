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

    public Double getBetAmount() {
        return this.betAmount;
    }

    public void addBetAmount(Double amount) {
        this.betAmount += amount;
    }

    public void resetBetAmount() {
        this.betAmount = 0.0;
    }

    @Override
    public Bet makeBet(Double amount, BetType type) {
        return null;
    }

    public Bet makeBet(Double amount, BetType type, Set<Object> choice) throws IllegalArgumentException {
        if (amount >= 0) {
            switch (type.getTypeName()) {
                case "PLEIN" -> {
                    return RouletteBetFactory.PleinBet(choice, amount);
                }
                case "CHEVAL" -> {
                    return RouletteBetFactory.ChevalBet(choice, amount);
                }
                case "CARRE" -> {
                    return RouletteBetFactory.CarreBet(choice, amount);
                }
                case "DOUZAINE" -> {
                    return RouletteBetFactory.DouzaineBet(choice, amount);
                }
                case "COLONNE" -> {
                    return RouletteBetFactory.ColonneBet(choice, amount);
                }
                case "PAIR" -> {
                    return RouletteBetFactory.PairBet(choice, amount);
                }
                case "IMPAIR" -> {
                    return RouletteBetFactory.ImpairBet(choice, amount);
                }
                case "PASSE" -> {
                    return RouletteBetFactory.PasseBet(choice, amount);
                }
                case "MANQUE" -> {
                    return RouletteBetFactory.ManqueBet(choice, amount);
                }
                case "ROUGE" -> {
                    return RouletteBetFactory.RougeBet(choice, amount);
                }
                case "NOIR" -> {
                    return RouletteBetFactory.NoirBet(choice, amount);
                }
                default -> throw new IllegalArgumentException("Invalid bet type " + type);
            }
        } else {
            throw new IllegalArgumentException("Cannot place a bet with amount 0");
        }

    }
}
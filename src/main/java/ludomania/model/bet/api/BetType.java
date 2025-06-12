package ludomania.model.bet.api;

/**
 * Interface representing the type of a bet in a game.
 * Implementing classes or enums define specific bet types with associated
 * names and payout multipliers.
 */
public interface BetType {

    String getTypeName();

    Double getPayout();

}

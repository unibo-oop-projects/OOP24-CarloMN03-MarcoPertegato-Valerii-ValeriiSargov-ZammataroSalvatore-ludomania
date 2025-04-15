package ludomania.cosmetics.cards;

import io.lyuda.jcards.CardImage;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import ludomania.cosmetics.CardTheme;

public class EuropeanCardTheme implements CardTheme {
    private String getColor() {
        return "#000";
    }

    private String getCard() {
        return "M10 0h80c5.54 0 10 4.46 10 10v130c0 5.54-4.46 10-10 10H10c-5.54 0-10-4.46-10-10V10C0 4.46 4.46 0 10 0z";
    }

    @Override
    public String getCosmetic(Rank rank, Suit suit) {
        return CardImage.makeCard(suit.getPath(), rank.getPath(), getCard(), getColor());
    }
}

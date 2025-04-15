package ludomania.cosmetics;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;

public interface CardTheme {
    String getCosmetic(Rank rank, Suit suit);
}

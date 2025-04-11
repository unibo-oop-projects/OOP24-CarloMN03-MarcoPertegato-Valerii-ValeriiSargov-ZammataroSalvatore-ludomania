/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ludomania.core.impl;

import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import javafx.scene.Node;
import ludomania.cosmetics.CosmeticTheme;

class CosmeticSet {
    CosmeticTheme card,background,fiche;
    public CosmeticSet(CosmeticTheme card,CosmeticTheme background,CosmeticTheme fiche){
        this.card=card;
        this.background=background;
        this.fiche=fiche;
    }
    public void setBackgroundTheme(CosmeticTheme theme) {
        background=theme;
    }
    public void setCardTheme(CosmeticTheme theme) {
        card=theme;
    }
    public void setFicheTheme(CosmeticTheme theme) {
        fiche=theme;
    }

     public Node getBackground() {
        return background.getCosmetic();
    }

    public Node getCard(Rank rank, Suit suit) {
        return card.getCosmetic();
    }

    public Node getFiche() {
        return fiche.getCosmetic();
    }

}

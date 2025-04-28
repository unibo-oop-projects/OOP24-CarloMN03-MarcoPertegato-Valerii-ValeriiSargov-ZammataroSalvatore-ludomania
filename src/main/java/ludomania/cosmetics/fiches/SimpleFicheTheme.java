package ludomania.cosmetics.fiches;

import java.util.EnumMap;
import java.util.Map;

import ludomania.cosmetics.CosmeticTheme;
import ludomania.cosmetics.FicheTheme;
import ludomania.cosmetics.FicheValue;

public abstract class SimpleFicheTheme implements FicheTheme{
    protected final Map<FicheValue, String> colori = new EnumMap<>(FicheValue.class);
    protected String backgroundColor;
    protected String textColor;

    protected abstract void initColori();  // Dove setti colori fiche + background + text
    protected abstract CosmeticTheme getThemeType();

    public SimpleFicheTheme() {
        initColori();
    }

    private String getFicheSVG(String ficheColor, int number) {
        return String.format(
            """
            <svg version="1.2" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="100" height="100">
                <style>
                    tspan { white-space:pre }
                    .s0 { fill: %s }
                    .s1 { fill: %s }
                    .t1 { font-size: 150px; fill: %s; font-weight: 400; font-family: "DejaVuSans", "DejaVu Sans" }
                </style>
                <path id="Background" fill-rule="evenodd" class="s0" d="m300 600c-165.9 0-300-134.1-300-300 0-165.9 134.1-300 300-300 165.9 0 300 134.1 300 300 0 165.9-134.1 300-300 300z"/>
                <path id="Forma 1" fill-rule="evenodd" class="s1" d="m300.5 517c-124.7 0-225.5-97-225.5-217 0-120 100.8-217 225.5-217 124.7 0 225.5 97 225.5 217 0 120-100.8 217-225.5 217z"/>
                <text id="Testo" style="transform: matrix(1,0,0,1,261,290)">
                    <tspan x="50%%" y="55%%" text-anchor="middle" dominant-baseline="middle" class="t1">
                        %s
                    </tspan>
                </text>
            </svg>""",
            backgroundColor, ficheColor, textColor, number
        );
    }

    @Override
    public String getCosmetic(Integer value) {
        FicheValue fiche = FicheValue.fromValue(value);
        String ficheColor = colori.get(fiche);
        return getFicheSVG(ficheColor, value);
    }

    @Override
    public CosmeticTheme getTheme() {
        return getThemeType();
    }
}

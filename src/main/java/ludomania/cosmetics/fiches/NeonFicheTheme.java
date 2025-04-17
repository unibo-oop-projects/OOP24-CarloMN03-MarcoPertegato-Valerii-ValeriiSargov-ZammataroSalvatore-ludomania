package ludomania.cosmetics.fiches;

import ludomania.cosmetics.FicheTheme;

public class NeonFicheTheme implements FicheTheme {

    private String getFiche(String color, int number) {
        String svg = String.format(
                """
                        <svg version="1.2" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 600 600" width="100" height="100">
                            <style>
                                tspan { white-space:pre }
                                    .s0 { fill:rgb(239, 191, 4) }
                                    .s1 { fill: %s }
                                    .t1 { font-size: 150px;fill: #ffffff;font-weight: 400;font-family: "DejaVuSans", "DejaVu Sans" }
                            </style>
                            <path id="Background" fill-rule="evenodd" class="s0" d="m300 600c-165.9 0-300-134.1-300-300 0-165.9 134.1-300 300-300 165.9 0 300 134.1 300 300 0 165.9-134.1 300-300 300z"/>
                            <path id="Forma 1" fill-rule="evenodd" class="s1" d="m300.5 517c-124.7 0-225.5-97-225.5-217 0-120 100.8-217 225.5-217 124.7 0 225.5 97 225.5 217 0 120-100.8 217-225.5 217z"/>
                            <text id="Testo" style="transform: matrix(1,0,0,1,261,290)">
                                <tspan x="50%%" y="50%%" text-anchor="middle" dominant-baseline="middle" class="t1">
                                    %s
                                </tspan>
                            </text>
                            </svg>""",
                color, number);
        return svg;
    }

    @Override
    public String getCosmetic(Integer value) {
        if (value >= 500) {
            return getFiche("#800080", value);
        }
        if (value >= 100) {
            return getFiche("#808080", value);
        }
        if (value >= 25) {
            return getFiche("#008000", value);
        }
        if (value >= 10) {
            return getFiche("#000080", value);
        }
        if (value >= 5) {
            return getFiche("#800000", value);
        }
        return getFiche("#ffffff", value);
    }
}

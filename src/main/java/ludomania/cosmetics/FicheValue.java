package ludomania.cosmetics;

public enum FicheValue {
    UNO(1),
    CINQUE(5),
    DIECI(10),
    VENTICINQUE(25),
    CINQUANTA(50),
    CENTO(100),
    CINQUECENTO(500);

    private final int value;

    FicheValue(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FicheValue fromValue(final int value) {
        if (value >= 500) return CINQUECENTO;
        if (value >= 100) return CENTO;
        if (value >= 25) return VENTICINQUE;
        if (value >= 10) return DIECI;
        if (value >= 5) return CINQUE;
        return UNO;
    }

}

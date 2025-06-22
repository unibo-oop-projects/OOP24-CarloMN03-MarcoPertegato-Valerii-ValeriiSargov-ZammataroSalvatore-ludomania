package ludomania.core;

import javafx.application.Application;

/**
 * Main class for launching the Ludomania application.
 * <p>
 * This class starts the JavaFX application and sets up the various managers
 * required for the application's functionality, including settings, image,
 * audio,
 * language, and scene managers. After initialization, the main menu scene is
 * displayed.
 * </p>
 */

public final class Ludomania {

    // Private constructor to prevent instantiation
    private Ludomania() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * The main entry point for the application.
     * <p>
     * This method starts the application by calling the launch(String[])
     * method from JavaFX.
     * </p>
     * 
     * @param args command line arguments (not used in this case)
     */
    public static void main(final String[] args) {
        Application.launch(LudomaniaLauncher.class, args);
    }
}

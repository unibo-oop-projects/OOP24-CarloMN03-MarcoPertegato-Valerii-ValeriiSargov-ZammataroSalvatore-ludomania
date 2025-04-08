package ludomania.controller.api;

import javafx.scene.Parent;

/**
 * Represents a generic UI controller in the application.
 * <p>
 * Each controller is responsible for providing its associated JavaFX view
 * through the {@link #getView()} method.
 */
public interface Controller {
    /**
     * Returns the root node of the controller's JavaFX view.
     *
     * @return the {@link Parent} representing the root of the view
     */
    Parent getView();
}
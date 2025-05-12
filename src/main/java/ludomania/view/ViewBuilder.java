package ludomania.view;

import javafx.scene.Parent;
import javafx.util.Builder;

/**
 * Specialized builder interface for constructing JavaFX {@link Parent} views.
 * 
 * <p>
 * Extends the generic {@link Builder} interface with a specific target type of
 * {@code Parent},
 * 
 * used to build UI components for different screens or views.
 */

public interface ViewBuilder extends Builder<Parent> {
}

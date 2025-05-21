package database.schemas.api;

import org.json.JSONObject;

/**
 * Defines the database entry interface.
 * <p>
 * Represents a record in the database.
 */
public interface Entry {
    /**
     * Getter for the entry identifier
     * @return a string identifier
     */
    String identifier();
}

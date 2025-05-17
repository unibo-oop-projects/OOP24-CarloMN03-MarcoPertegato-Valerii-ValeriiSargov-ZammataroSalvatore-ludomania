package database.controllers.api;

import database.models.api.DBModel;
import database.schemas.api.Entry;

/**
 * Defines the database controller interface for reading and writing on the database.
 * <p>
 * Implementations of this interface are responsible for calling the correct database model
 */
public abstract class DatabaseController {
    protected DBModel model;

    /**
     * Calls the DBModel insert method
     * @return true is success
     */
    public boolean insert() {
        return this.model.insert();
    }

    /**
     * Calls the DBModel update method
     * @return true is success
     */
    public boolean update() {
        return this.model.update();
    }

    /**
     * Calls the DBModel delete method
     * @return true is success
     */
    public boolean delete() {
        return this.model.delete();
    }

    /**
     * Calls the DBModel read method
     * @return the database entry if present
     */
    public Entry read() {
        return this.model.read();
    }
}
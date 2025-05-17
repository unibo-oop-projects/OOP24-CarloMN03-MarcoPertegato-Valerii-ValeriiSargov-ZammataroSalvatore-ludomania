package database.models.api;

import database.schemas.api.Entry;

/**
 * Defines the database model interface for reading and writing database files.
 * <p>
 * Implementations of this interface handle the operations of 
 * reading, writing, updating and deleting records from the database
 */
public interface DBModel {

    /**
     * Inserts a new record in the database.
     */
    public boolean insert();

    /**
     * Updates an existing record in the database.
     */
    public boolean update();

    /**
     * Removes an existing record from the database.
     */
    public boolean delete();

    /**
     * Reads a record from the database.
     */
    public Entry read();
}
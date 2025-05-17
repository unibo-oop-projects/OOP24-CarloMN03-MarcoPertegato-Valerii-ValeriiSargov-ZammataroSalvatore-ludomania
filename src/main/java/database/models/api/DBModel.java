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
    boolean insert();

    /**
     * Updates an existing record in the database.
     */
    boolean update();

    /**
     * Removes an existing record from the database.
     */
    boolean delete();

    /**
     * Reads a record from the database.
     */
    Entry read();
}
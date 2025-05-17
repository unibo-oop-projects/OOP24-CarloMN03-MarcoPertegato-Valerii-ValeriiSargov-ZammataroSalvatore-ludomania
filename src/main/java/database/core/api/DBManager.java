package database.core.api;

import database.schemas.api.Entry;

/**
 * Defines the database manager interface for reading and writing database files.
 * <p>
 * Implementations of this interface handle the operations of 
 * reading, writing, updating and deleting records from the database
 */
public interface DBManager {
    /**
     * Stores a new record in the database.
     * @param entry the database entry to store
     * @param filename the database file in which to store the entry
     * @return true if success
     */
    public boolean insert(Entry entry, String filename);

    /**
     * Updates a record in the database.
     * @param entry the database entry to update
     * @param filename the database file in which to update the entry
     * @return true if success
     */
    public boolean update(Entry entry, String filename);
    
    /**
     * Removes a record from the database.
     * @param entry the database entry to remove
     * @param filename the database file from which to remove the entry
     * @return true if success
     */
    public boolean delete(Entry entry, String filename);

    /**
     * Reads a record from the database if present
     * @param <E> type of the record
     * @param entry entry to read from the database
     * @param filename file in the database from which retriving the record
     * @return the record of type E if present
     */
    public <E> E read(E entry, String filename);
}

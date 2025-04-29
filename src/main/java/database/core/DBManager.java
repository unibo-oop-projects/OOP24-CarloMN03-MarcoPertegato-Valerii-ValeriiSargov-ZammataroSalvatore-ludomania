package database.core;

import database.schemas.api.Entry;

public interface DBManager {
    public boolean insert(Entry entry, String filename);
    public boolean update(Entry entry, String filename);
    public boolean delete(Entry entry, String filename);
    public <E> E read(E entry, String filename);
}

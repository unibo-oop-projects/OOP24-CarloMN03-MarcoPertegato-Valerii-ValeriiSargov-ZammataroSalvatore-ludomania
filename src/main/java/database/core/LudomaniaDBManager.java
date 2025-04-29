package database.core;

import database.schemas.api.Entry;

public class LudomaniaDBManager implements DBManager {

    private static final LudomaniaDBManager manager = new LudomaniaDBManager();

    private LudomaniaDBManager() {}

    public static LudomaniaDBManager getManager() {
        return manager;
    }
    @Override
    public boolean insert(Entry entry, String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean update(Entry entry, String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Entry entry, String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public <E> E read(E entry, String filename) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

}

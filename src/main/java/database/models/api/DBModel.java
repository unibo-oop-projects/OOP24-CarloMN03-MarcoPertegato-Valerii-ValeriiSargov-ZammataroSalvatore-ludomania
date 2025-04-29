package database.models.api;

import database.schemas.api.Entry;

public interface DBModel {
    public boolean insert();
    public boolean update();
    public boolean delete();
    public Entry read();
}
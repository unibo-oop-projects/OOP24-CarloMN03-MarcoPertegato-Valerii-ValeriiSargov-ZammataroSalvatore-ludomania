package database.controllers.api;

import database.schemas.api.Entry;

public interface DatabaseController {
    public boolean insert();
    public boolean update();
    public boolean delete();
    public Entry read();
}
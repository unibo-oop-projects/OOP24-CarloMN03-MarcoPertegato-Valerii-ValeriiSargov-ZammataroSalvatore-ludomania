package database.schemas.api;

import org.json.JSONObject;

public abstract class Entry {
    public JSONObject toJson() {
        return new JSONObject(this);
    }
}

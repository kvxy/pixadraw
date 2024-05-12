package persistence;

import org.json.JSONObject;

// Represents all classes that can be saved
public interface Writable {

    // EFFECTS: converts data to object and returns it
    Object toObject();

}
package model;

import org.json.*;

import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a collection of sprites
public class Collection implements Writable {
    private List<Sprite> sprites;

    // EFFECTS: creates a new empty collection
    public Collection() {
        sprites = new ArrayList<>();
    }

    // REQUIRES: width and height are both > 0
    //           name has at least 1 character
    // MODIFIES: this
    // EFFECTS: creates and adds to sprite to the collection
    public Sprite createSprite(String name, int width, int height) {
        sprites.add(new Sprite(name, width, height));

        Event event = new Event("created new sprite named " + name + " and added it to collection.");
        EventLog.getInstance().logEvent(event);

        return sprites.get(size() - 1);
    }

    // REQUIRES: 0 <= index < number of sprites
    // MODIFIES: this
    // EFFECTS: removes sprite at index
    public void deleteSprite(int index) {
        Sprite removedSprite = sprites.remove(index);

        Event event = new Event("removed sprite named " + removedSprite.getName() + " from the collection.");
        EventLog.getInstance().logEvent(event);
    }

    // REQUIRES: 0 <= index < number of sprites
    // EFFECTS: returns sprite at index
    public Sprite getSprite(int index) {
        return sprites.get(index);
    }

    // EFFECTS: returns the amount of sprites
    public int size() {
        return sprites.size();
    }

    // MODIFIES: this
    // EFFECTS: resets the collection and removes all sprites
    public void reset() {
        sprites = new ArrayList<>();
    }

    // EFFECTS: returns the object of the collection
    @Override
    public Object toObject() {
        JSONArray spritesArray = new JSONArray();
        for (Sprite sprite : sprites) {
            spritesArray.put(sprite.toObject());
        }
        return spritesArray;
    }
}

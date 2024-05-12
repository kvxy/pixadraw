package persistence;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.*;
import org.json.*;

// Reads data from given file
public class PDReader {
    Collection collection;

    // EFFECTS: creates the PDReader object
    public PDReader() {
        collection = new Collection();
    }

    // MODIFIES: this
    // EFFECTS: reads the path and readies the constructed collection
    //          throws InvalidPathException when the path isn't found
    public void read(String path) throws IOException {
        String data = readPath(path);
        JSONArray collectionData = new JSONArray(data);
        constructCollection(collectionData);
    }

    // MODIFIES: this
    // EFFECTS: sets the effected collection
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    // EFFECTS: returns the constructed collection
    public Collection getCollection() {
        return collection;
    }

    // MODIFIES: this
    // EFFECTS: reads the path and returns the JSON file in string form
    //          throws InvalidPathException when the path isn't found
    private String readPath(String path) throws IOException {
        String data = "";
        for (String line : Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)) {
            data += line;
        }
        return data;
    }

    // REQUIRES: given JSON is in correct format
    // MODIFIES: this
    // EFFECTS: constructs the collection from given JSON array
    private void constructCollection(JSONArray collectionData) {
        collection.reset();
        for (Object spriteData : collectionData) {
            constructSprite((JSONObject)spriteData);
        }
    }

    // MODIFIES: this
    // EFFECTS: constructs a sprite from given data and adds it to the collection
    private void constructSprite(JSONObject spriteData) {
        String name = spriteData.getString("name");
        int width = spriteData.getInt("width");
        int height = spriteData.getInt("height");

        Sprite sprite = collection.createSprite(name, width, height);
        for (Object frameData : spriteData.getJSONArray("frames")) {
            constructFrame(sprite, (JSONArray)frameData, width, height);
        }
        sprite.deleteFrame(0);
    }

    // MODIFIES: sprite
    // EFFECTS: constructs a frame and adds it to given sprite
    private void constructFrame(Sprite sprite, JSONArray frameData, int width, int height) {
        Frame frame = sprite.addFrame(sprite.getFramesLength());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = (y + x * height) * 4;
                int r = frameData.getInt(index);
                int g = frameData.getInt(index + 1);
                int b = frameData.getInt(index + 2);
                boolean transparent = frameData.getBoolean(index + 3);

                frame.setPixelColor(x, y, r, g, b);
                frame.setPixelTransparent(x, y, transparent);
            }
        }
    }
}

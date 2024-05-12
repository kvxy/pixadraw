package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionTest {
    Collection collection;

    @BeforeEach
    public void setup() {
        collection = new Collection();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, collection.size());
    }

    @Test
    public void testSpriteCreationAndDeletion() {
        Sprite s0 = collection.createSprite("Test Sprite 1", 8, 8);
        Sprite s1 = collection.createSprite("Test Sprite 2", 8, 8);
        assertEquals(2, collection.size());
        assertEquals(s0, collection.getSprite(0));
        assertEquals(s1, collection.getSprite(1));

        collection.deleteSprite(0);
        assertEquals(1, collection.size());
        assertEquals(s1, collection.getSprite(0));
    }

    @Test
    public void testCollectionReset() {
        collection.createSprite("Test Sprite 1", 8, 8);
        collection.createSprite("Test Sprite 2", 8, 8);

        collection.reset();
        assertEquals(0, collection.size());
    }

    @Test
    public void testToObject() {
        collection.createSprite("Test Sprite 1", 8, 8);
        collection.createSprite("Test Sprite 2", 8, 8);

        Object collectionObject = collection.toObject();
        assertEquals(2, ((JSONArray)collectionObject).length());
    }
}

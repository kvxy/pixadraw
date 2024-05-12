package persistence;

import model.Collection;
import model.Frame;
import model.Sprite;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PDWriterTest {

    @Test
    public void testWriteGeneralCollection() {
        Collection generalCollection = new Collection();
        generalCollection.createSprite("sprite 0", 1, 2);
        Sprite sprite = generalCollection.createSprite("sprite 1", 16, 20);

        Frame frame = sprite.addFrame(1);
        frame.setPixelColor(0, 0, 1, 2, 3);
        frame.setPixelColor(15, 19, 3, 2, 5);
        frame.setPixelTransparent(15, 19, false);

        try {
            PDWriter writer = new PDWriter("./data/newGeneralCollection.json");
            writer.write(generalCollection);
            writer.close();

            PDReader reader = new PDReader();
            reader.read("./data/newGeneralCollection.json");
            assertEquals(generalCollection.toObject().toString(), reader.getCollection().toObject().toString());
        } catch (Exception e) {
            fail("Unexpected Exception :(");
        }
    }

    @Test
    public void testEmptyCollection() {
        Collection emptyCollection = new Collection();

        try {
            PDWriter writer = new PDWriter("./data/newEmptyCollection.json");
            writer.write(emptyCollection);
            writer.close();

            PDReader reader = new PDReader();
            reader.read("./data/newEmptyCollection.json");
            assertEquals(0, reader.getCollection().size());
        } catch (Exception e) {
            fail("Unexpected Exception :(");
        }
    }

    @Test
    public void testInvalidPath() {
        try {
            new PDWriter("./data/ A@ \n :).json");
            fail ("Exception expected");
        } catch (IOException e) {
            // all good!
        }
    }
}

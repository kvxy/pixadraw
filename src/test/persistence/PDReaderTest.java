package persistence;

import model.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PDReaderTest {
    PDReader reader;

    @BeforeEach
    public void setup() {
        reader = new PDReader();
    }

    @Test
    public void testReadGeneralCollection() {
        String generalCollectionPath = "./data/generalCollection.json";
        Collection myCollection = new Collection();
        reader.setCollection(myCollection);
        try {
            reader.read(generalCollectionPath);
        } catch (IOException e) {
            fail("Unexpected IOException!");
        }
        assertEquals(3, myCollection.size());

        String collectionString = "";
        try {
            for (String line : Files.readAllLines(Paths.get(generalCollectionPath), StandardCharsets.UTF_8)) {
                collectionString += line;
            }
        } catch (IOException e) {
            fail(generalCollectionPath + " should exist");
        }
        assertEquals(collectionString, myCollection.toObject().toString());
    }

    @Test
    public void testFileNotFound() {
        try {
            reader.read("./data/noSuchFile.json");
            fail("Expected IOException!");
        } catch (IOException e) {
            // we good :)
        }
    }

    @Test
    public void testEmptyCollection() {
        try {
            reader.read("./data/emptyCollection.json");
        } catch (IOException e) {
            fail("Unexpected IOException!");
        }
        assertEquals(0, reader.getCollection().size());
    }
}

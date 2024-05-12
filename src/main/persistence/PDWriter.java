package persistence;

import model.Collection;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Writes data to files
public class PDWriter {
    PrintWriter writer;

    // EFFECTS: constructs the PDWriter object
    public PDWriter(String path) throws FileNotFoundException {
        writer = new PrintWriter(path);
    }

    // EFFECTS: saves collection to file
    public void write(Collection collection) {
        String collectionJson = collection.toObject().toString();
        writer.println(collectionJson);
        writer.flush();
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }
}

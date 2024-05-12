package ui;

import ui.terminal.TerminalApplication;
import ui.graphical.GraphicalApplication;

import java.io.IOException;

// Runs the application
public class Main {
    public static void main(String[] args) {
        // new GraphicalApplication();
        try {
            new TerminalApplication();
        } catch (IOException error) {
            // :(
        }
    }
}
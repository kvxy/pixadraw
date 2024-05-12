package ui.graphical.menus;

import model.Collection;
import persistence.PDReader;
import ui.graphical.listeners.SwitchMenuListenerBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// A menu to load files
public class LoadMenu extends Menu {
    private JTextField fileField;
    private String error;

    // Action listener for load button
    private class LoadActionListener implements ActionListener {
        // MODIFIES: LoadMenu.this
        // EFFECTS: loads given file in file field, displays error on failure
        @Override
        public void actionPerformed(ActionEvent e) {
            PDReader reader = new PDReader();
            reader.setCollection(collection);
            try {
                reader.read(fileField.getText());
                smlBuilder.createActionListener(MenuType.COLLECTION_MENU, LoadMenu.this).actionPerformed(e);
            } catch (IOException ex) {
                error = "Error loading file!";
                render();
            }
        }
    }

    // EFFECTS: creates the load menu
    public LoadMenu(int width, int height, Collection collection,
                    SwitchMenuListenerBuilder smlBuilder, String filePath) {
        super(width, height, collection, smlBuilder);
        setLayout(new BorderLayout());
        initFileField(filePath);
        error = "";
    }

    // MODIFIES: this
    // EFFECTS: initiates the filter field
    private void initFileField(String filePath) {
        fileField = new JTextField(1);
        setDimensions(fileField, getWidth(), 20);
        fileField.setAlignmentX(Component.CENTER_ALIGNMENT);
        fileField.setPreferredSize(new Dimension(getWidth(), 20));
        fileField.setText(filePath);
    }

    // MODIFIES: this
    // EFFECTS: draws/redraws the frame
    @Override
    public void draw() {
        drawFileField();
        drawButtons();
        drawError(error);
    }

    // MODIFIES: this
    // EFFECTS: draws the file name input field
    private void drawFileField() {
        JPanel fileFieldPanel = new JPanel();
        fileFieldPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        fileFieldPanel.setLayout(new BoxLayout(fileFieldPanel, BoxLayout.X_AXIS));

        fileFieldPanel.add(new JLabel("File name:  "));
        fileFieldPanel.add(fileField);

        add(fileFieldPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: draws the back/load buttons
    private void drawButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton loadBtn = new JButton("Load");
        JButton backBtn = new JButton("Back");
        buttonsPanel.add(loadBtn);
        buttonsPanel.add(backBtn);

        loadBtn.addActionListener(new LoadActionListener());
        backBtn.addActionListener(smlBuilder.createActionListener(MenuType.COLLECTION_MENU, this));

        add(buttonsPanel);
    }

    // MODIFIES: this
    // EFFECTS: draws the error
    private void drawError(String message) {
        JPanel errorPanel = new JPanel();
        errorPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        errorPanel.add(new JLabel(message));
        add(errorPanel, BorderLayout.PAGE_END);
    }

    // EFFECTS: returns this menu type
    @Override
    public MenuType getMenuType() {
        return MenuType.LOAD_MENU;
    }

    // EFFECTS: returns the loaded file name
    public String getFilePath() {
        return fileField.getText();
    }
}

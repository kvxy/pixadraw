package ui.graphical.menus;

import model.Collection;
import persistence.PDWriter;
import ui.graphical.listeners.SwitchMenuListenerBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// A menu to save files
public class SaveMenu extends Menu {
    private JTextField fileField;
    private String error;

    // Action listener for save button
    private class SaveActionListener implements ActionListener {
        // MODIFIES: SaveMenu.this
        // EFFECTS: saves given file in file field, displays error on failure
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PDWriter writer = new PDWriter(fileField.getText());
                writer.write(collection);
                smlBuilder.createActionListener(MenuType.COLLECTION_MENU, SaveMenu.this).actionPerformed(e);
            } catch (FileNotFoundException ex) {
                error = "Error saving file!";
                render();
            }
        }
    }

    // EFFECTS: constructs the save menu
    public SaveMenu(int width, int height, Collection collection,
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
    // EFFECTS: draws the back/save buttons
    private void drawButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("Back");
        buttonsPanel.add(saveBtn);
        buttonsPanel.add(backBtn);

        saveBtn.addActionListener(new SaveActionListener());
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
        return MenuType.SAVE_MENU;
    }

    // EFFECTS: returns the saved file name
    public String getFilePath() {
        return fileField.getText();
    }
}

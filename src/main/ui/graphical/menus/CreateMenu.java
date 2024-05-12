package ui.graphical.menus;

import model.Collection;
import ui.graphical.listeners.SwitchMenuListenerBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Menu for creating new sprites
public class CreateMenu extends Menu {
    private JTextField nameField;
    private JTextField widthField;
    private JTextField heightField;
    private String error;

    // Action listener for create button
    private class CreateActionListener implements ActionListener {
        // MODIFIES: CreateMenu.this
        // EFFECTS: creates the sprite if there are no errors
        @Override
        public void actionPerformed(ActionEvent e) {
            updateErrorMessage();
            if (error.equals("")) {
                String name = nameField.getText();
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                collection.createSprite(name, width, height);

                smlBuilder.createActionListener(MenuType.EDITOR_MENU, CreateMenu.this).actionPerformed(e);
            } else {
                render();
            }
        }
    }

    // EFFECTS: constructs the create menu
    public CreateMenu(int width, int height, Collection collection, SwitchMenuListenerBuilder smlBuilder) {
        super(width, height, collection, smlBuilder);
        setLayout(new BorderLayout());

        nameField = new JTextField("Untitled Sprite");
        widthField = new JTextField("32");
        heightField = new JTextField("32");
        error = "";
    }

    // MODIFIES: this
    // EFFECTS: draws the menu
    @Override
    public void draw() {
        drawInputFields();
        drawButtons();
        drawErrorMessage();
    }

    // MODIFIES: this
    // EFFECTS: draws the error message
    private void drawErrorMessage() {
        JLabel errorLabel = new JLabel(error);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(errorLabel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: updates the error message
    private void updateErrorMessage() {
        error = "";
        if (nameField.getText().trim().equals("")) {
            error = "Name can't be empty!";
            return;
        }
        try {
            int width = Integer.parseInt(widthField.getText());
            if (width < 4 || width > 64) {
                error = "Width is bounded between [4, 64]!";
            }
        } catch (NumberFormatException e) {
            error = "Width has to be integer!";
        }
        try {
            int height = Integer.parseInt(heightField.getText());
            if (height < 4 || height > 64) {
                error = "Height is bounded between [4, 64]!";
            }
        } catch (NumberFormatException e) {
            error = "Height has to be integer!";
        }
    }

    // MODIFIES: this
    // EFFECTS: draws the back/create buttons
    private void drawButtons() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton createBtn = new JButton("Create");
        JButton backBtn = new JButton("Back");
        buttonsPanel.add(createBtn);
        buttonsPanel.add(backBtn);

        createBtn.addActionListener(new CreateActionListener());
        backBtn.addActionListener(smlBuilder.createActionListener(MenuType.COLLECTION_MENU, this));

        add(buttonsPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: draws the fields
    private void drawInputFields() {
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));

        fieldsPanel.add(makeFieldPanel("Name: ", nameField));
        fieldsPanel.add(makeFieldPanel("Width:", widthField));
        fieldsPanel.add(makeFieldPanel("Height: ", heightField));

        add(fieldsPanel, BorderLayout.PAGE_START);
    }

    // EFFECTS: makes a field panel
    private JPanel makeFieldPanel(String name, JTextField field) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS));
        fieldPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        field.setMaximumSize(new Dimension(9999, 20));

        JLabel label = new JLabel(name);
        label.setPreferredSize(new Dimension(50, 20));
        fieldPanel.add(label);
        fieldPanel.add(field);

        return fieldPanel;
    }

    // EFFECTS: gets this menu type
    @Override
    public MenuType getMenuType() {
        return MenuType.CREATE_MENU;
    }

    // EFFECTS: gets the sprite index of created sprite
    public int getSpriteIndex() {
        return collection.size() - 1;
    }
}

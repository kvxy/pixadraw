package ui.graphical.menus;

import model.*;
import ui.graphical.listeners.SwitchMenuListenerBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// A menu of a collection of sprites
public class CollectionMenu extends Menu {
    private JTextField searchField;
    private String filter;
    private int spriteIndex;

    // Action listener for search bar
    private class SearchFieldActionListener implements ActionListener {
        // MODIFIES: CollectionMenu.this
        // EFFECTS: searches for given text
        @Override
        public void actionPerformed(ActionEvent e) {
            filter = searchField.getText();
            render();
        }
    }

    // Action listener for sprite deletion
    private class DeleteSpriteActionListener implements ActionListener {
        private int deleteIndex;

        // EFFECTS: creates sprite deletion event listener
        public DeleteSpriteActionListener(int index) {
            super();
            deleteIndex = index;
        }

        // MODIFIES: CollectionMenu.this
        // EFFECTS: deletes sprite at deleteIndex
        @Override
        public void actionPerformed(ActionEvent e) {
            collection.deleteSprite(deleteIndex);
            render();
        }
    }

    // Action listener for sprite editing
    private class EditSpriteActionListener implements ActionListener {
        int editIndex;

        // EFFECTS: creates sprite deletion event listener
        public EditSpriteActionListener(int index) {
            super();
            editIndex = index;
        }

        // MODIFIES: CollectionMenu.this
        // EFFECTS: sets spriteIndex to the edited sprite index
        @Override
        public void actionPerformed(ActionEvent e) {
            spriteIndex = editIndex;
            smlBuilder.createActionListener(MenuType.EDITOR_MENU, CollectionMenu.this).actionPerformed(e);
            render();
        }
    }

    // EFFECTS: constructs the collection menu
    public CollectionMenu(int width, int height, Collection collection, SwitchMenuListenerBuilder smlBuilder) {
        super(width, height, collection, smlBuilder);
        setLayout(new BorderLayout());
        initSearchField();

        spriteIndex = 0;
    }

    // MODIFIES: this
    // EFFECTS: initiates the filter field
    private void initSearchField() {
        searchField = new JTextField(1);
        filter = "";
        setDimensions(searchField, getWidth(), 20);

        searchField.addActionListener(new SearchFieldActionListener());
    }

    // MODIFIES: this
    // EFFECTS: draws/redraws the frame
    @Override
    public void draw() {
        drawHeader();
        drawCollection();
        drawFooter();
    }

    // MODIFIES: this
    // EFFECTS: draws the header text and filter text field
    private void drawHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel("  My Collection  ");
        headerPanel.add(label);

        label = new JLabel("  Search:  ");
        headerPanel.add(label);
        headerPanel.add(searchField);

        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        add(headerPanel, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: draws the footer create sprite, load, and save buttons
    private void drawFooter() {
        JPanel footerPanel = new JPanel();
        JButton createBtn = new JButton("Create Sprite");
        JButton saveBtn = new JButton("Save File");
        JButton loadBtn = new JButton("Load File");
        createBtn.addActionListener(smlBuilder.createActionListener(MenuType.CREATE_MENU, this));
        loadBtn.addActionListener(smlBuilder.createActionListener(MenuType.LOAD_MENU, this));
        saveBtn.addActionListener(smlBuilder.createActionListener(MenuType.SAVE_MENU, this));
        footerPanel.add(createBtn);
        footerPanel.add(saveBtn);
        footerPanel.add(loadBtn);

        footerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        add(footerPanel, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: draws the collection of sprites
    private void drawCollection() {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, 1));
        scrollPanel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scrollPanel, BorderLayout.PAGE_START);

        for (int i = 0; i < collection.size(); i++) {
            Sprite sprite = collection.getSprite(i);
            if (sprite.getName().toLowerCase().contains(filter.toLowerCase())) {
                scrollPanel.add(createSpritePanel(sprite, i));
            }
        }

        JScrollPane scrollPane = new JScrollPane(wrapper,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 100));
        scrollPane.setName("collection");
        add(scrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: creates and returns a sprite panel
    private JPanel createSpritePanel(Sprite sprite, int index) {
        JPanel spritePanel = new JPanel(new FlowLayout());

        JButton spriteBtn = new JButton(sprite.getName());
        spriteBtn.addActionListener(new EditSpriteActionListener(index));
        setDimensions(spriteBtn, getWidth() - 80 - 50, 20);

        JButton delSpriteBtn = new JButton("Delete");
        delSpriteBtn.addActionListener(new DeleteSpriteActionListener(index));
        setDimensions(delSpriteBtn, 80, 20);

        spritePanel.add(delSpriteBtn);
        spritePanel.add(spriteBtn);
        return spritePanel;
    }

    // EFFECTS: returns this menu type
    @Override
    public MenuType getMenuType() {
        return MenuType.COLLECTION_MENU;
    }

    // EFFECTS: gets the sprite index of edited sprite
    public int getSpriteIndex() {
        return spriteIndex;
    }
}

package ui.graphical.menus;

import model.*;
import tools.*;
import ui.graphical.listeners.SwitchMenuListenerBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Menu for editing sprites
public class EditorMenu extends Menu {
    private static final Tool[] tools = { new PenTool(), new EraseTool() };
    private static final String[] toolNames = { "Pen", "Eraser" };

    private Sprite sprite;
    private int frameIndex;

    private JButton[][] canvasCells;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private JComboBox toolSelector;
    private JLabel frameIndexLabel;

    // Action listener for previous frame button
    private class PreviousFrameAction implements ActionListener {
        // MODIFIES: EditorMenu.this
        // EFFECTS: goes to previous frame, or if already at 1st frame, jump to last
        @Override
        public void actionPerformed(ActionEvent e) {
            if (frameIndex > 0) {
                frameIndex--;
            } else {
                frameIndex = sprite.getFramesLength() - 1;
            }
            updateFrameIndexLabel();
            updateCanvas();
        }
    }

    // Action listener for next frame button
    private class NextFrameAction implements ActionListener {
        // MODIFIES: EditorMenu.this
        // EFFECTS: goes to next frame, or if already at last frame, jump to first
        @Override
        public void actionPerformed(ActionEvent e) {
            if (frameIndex < sprite.getFramesLength() - 1) {
                frameIndex++;
            } else {
                frameIndex = 0;
            }
            updateFrameIndexLabel();
            updateCanvas();
        }
    }

    // Action listener for create frame button
    private class CreateFrameAction implements ActionListener {
        // MODIFIES: EditorMenu.this
        // EFFECTS: creates new frame at current frame index
        @Override
        public void actionPerformed(ActionEvent e) {
            sprite.addFrame(frameIndex + 1);
            frameIndex++;
            updateFrameIndexLabel();
            updateCanvas();
        }
    }

    // Action listener for delete frame button
    private class DeleteFrameAction implements ActionListener {
        // MODIFIES: EditorMenu.this
        // EFFECTS: deletes frame at current frame index
        @Override
        public void actionPerformed(ActionEvent e) {
            if (sprite.getFramesLength() != 1) {
                sprite.deleteFrame(frameIndex);
                if (frameIndex <= sprite.getFramesLength()) {
                    frameIndex = sprite.getFramesLength() - 1;
                }
            }
            updateFrameIndexLabel();
            updateCanvas();
        }
    }

    // Action listener for create button
    private class CanvasCellAction implements ActionListener {
        private int posX;
        private int posY;

        // REQUIRES: x and y are in bounds
        // EFFECTS: constructs the cell action listener
        public CanvasCellAction(int x, int y) {
            this.posX = x;
            this.posY = y;
        }

        // MODIFIES: EditorMenu.this
        // EFFECTS: uses selected tool on cell
        @Override
        public void actionPerformed(ActionEvent e) {
            int r = redSlider.getValue();
            int g = greenSlider.getValue();
            int b = blueSlider.getValue();
            tools[toolSelector.getSelectedIndex()].use(posX, posY, r, g, b, sprite.getFrame(frameIndex));

            Pixel pixel = sprite.getFrame(frameIndex).getPixel(posX, posY);
            fillCell(posX, posY, pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getTransparent());
        }
    }

    // EFFECTS: constructs the editor menu
    public EditorMenu(int width, int height, Collection collection,
                      SwitchMenuListenerBuilder smlBuilder, int spriteIndex) {
        super(width, height, collection, smlBuilder);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 15, 15, 15));

        sprite = collection.getSprite(spriteIndex);
        frameIndex = 0;
        canvasCells = new JButton[getWidth()][getHeight()];

        initiateJObjects();
    }

    // MODIFIES: this
    // EFFECTS: initiates color sliders & tool selector
    private void initiateJObjects() {
        redSlider = new JSlider(0, 255, 0);
        greenSlider = new JSlider(0, 255, 0);
        blueSlider = new JSlider(0, 255, 0);
        setDimensions(redSlider, 125, 25);
        setDimensions(greenSlider, 125, 25);
        setDimensions(blueSlider, 125, 25);

        toolSelector = new JComboBox(toolNames);
        toolSelector.setMaximumSize(new Dimension(9999, 25));

        frameIndexLabel = new JLabel();
        updateFrameIndexLabel();
    }

    // MODIFIES: this
    // EFFECTS: updates the frame index label
    private void updateFrameIndexLabel() {
        frameIndexLabel.setText("Frame " + (frameIndex + 1) + " / " + sprite.getFramesLength());
    }

    // MODIFIES: this
    // EFFECTS: draws the editor menu
    @Override
    public void draw() {
        drawCanvas();
        drawToolBar();
        drawUtilityBar();
    }

    // MODIFIES: this
    // EFFECTS: draws the toolbar & back button
    public void drawToolBar() {
        JPanel toolBar = new JPanel();
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.Y_AXIS));
        setDimensions(toolBar, 150, 9999);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(smlBuilder.createActionListener(MenuType.COLLECTION_MENU, this));
        toolBar.add(backBtn);
        toolBar.add(Box.createRigidArea(new Dimension(5, 20)));
        toolBar.add(toolSelector);

        add(toolBar, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: draws the color selector & frame manipulator
    public void drawUtilityBar() {
        JPanel utilityBarWrapper = new JPanel();
        utilityBarWrapper.setLayout(new BorderLayout());

        JPanel utilityBar = new JPanel();
        utilityBar.setLayout(new BoxLayout(utilityBar, BoxLayout.Y_AXIS));

        utilityBar.add(makeColorPanel("R ", redSlider));
        utilityBar.add(makeColorPanel("G ", greenSlider));
        utilityBar.add(makeColorPanel("B ", blueSlider));

        utilityBarWrapper.add(utilityBar, BorderLayout.PAGE_START);
        utilityBarWrapper.add(makeSpriteManipulationPanel(), BorderLayout.PAGE_END);
        add(utilityBarWrapper, BorderLayout.EAST);
    }

    // EFFECTS: creates the sprite manipulation panel
    public JPanel makeSpriteManipulationPanel() {
        JPanel spriteBtnsPanel = new JPanel();
        spriteBtnsPanel.setBorder(new EmptyBorder(15, 0, 45, 15));
        spriteBtnsPanel.setLayout(new BoxLayout(spriteBtnsPanel, BoxLayout.Y_AXIS));

        JButton prevBtn = new JButton("Previous");
        JButton nextBtn = new JButton("Next");
        JButton createBtn = new JButton("Create");
        JButton delBtn = new JButton("Delete");

        prevBtn.addActionListener(new PreviousFrameAction());
        nextBtn.addActionListener(new NextFrameAction());
        createBtn.addActionListener(new CreateFrameAction());
        delBtn.addActionListener(new DeleteFrameAction());

        spriteBtnsPanel.add(frameIndexLabel);
        spriteBtnsPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        spriteBtnsPanel.add(prevBtn);
        spriteBtnsPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        spriteBtnsPanel.add(nextBtn);
        spriteBtnsPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        spriteBtnsPanel.add(createBtn);
        spriteBtnsPanel.add(Box.createRigidArea(new Dimension(15, 15)));
        spriteBtnsPanel.add(delBtn);

        return spriteBtnsPanel;
    }

    // EFFECTS: creates a color panel for specific color
    public JPanel makeColorPanel(String prefix, JSlider colorSlider) {
        JPanel colorPanel = new JPanel();
        colorPanel.add(new JLabel(prefix));
        colorPanel.add(colorSlider);
        return colorPanel;
    }

    // MODIFIES: this
    // EFFECTS: draws the canvas
    public void drawCanvas() {
        JPanel canvasPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        for (int x = 0; x < sprite.getWidth(); x++) {
            for (int y = 0; y < sprite.getHeight(); y++) {
                c.gridx = x;
                c.gridy = y;
                canvasPanel.add(createCanvasCell(x, y), c);
            }
        }

        c.gridwidth = sprite.getWidth();
        c.gridheight = sprite.getHeight();

        add(canvasPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: updates the canvas
    public void updateCanvas() {
        for (int x = 0; x < sprite.getWidth(); x++) {
            for (int y = 0; y < sprite.getHeight(); y++) {
                Pixel pixel = sprite.getFrame(frameIndex).getPixel(x, y);
                fillCell(x, y, pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getTransparent());
            }
        }
        repaint();
    }

    // REQUIRES: x and y are in bounds
    // EFFECTS: creates a single cell of the canvas
    public JButton createCanvasCell(int x, int y) {
        int sizeLimiterX = 350;
        int sizeLimiterY = 100;
        int size = Math.min((getHeight() - sizeLimiterY) / sprite.getHeight(),
                (getWidth() - sizeLimiterX) / sprite.getWidth() - 1);
        JButton cellBtn = new JButton();
        cellBtn.setPreferredSize(new Dimension(size, size));
        cellBtn.setMaximumSize(new Dimension(size, size));
        cellBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
        cellBtn.addActionListener(new CanvasCellAction(x, y));

        Pixel pixel = sprite.getFrame(frameIndex).getPixel(x, y);
        canvasCells[x][y] = cellBtn;
        fillCell(x, y, pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getTransparent());
        return cellBtn;
    }

    // REQUIRES: x and y are in bounds
    // MODIFIES: this
    // EFFECTS: fills a cell with some color
    public void fillCell(int x, int y, int r, int g, int b, boolean transparent) {
        if (transparent) {
            canvasCells[x][y].setBackground(new Color(255, 255, 255));
        } else {
            canvasCells[x][y].setBackground(new Color(r, g, b));
        }
    }

    // EFFECTS: returns this menu type
    @Override
    public MenuType getMenuType() {
        return MenuType.EDITOR_MENU;
    }
}

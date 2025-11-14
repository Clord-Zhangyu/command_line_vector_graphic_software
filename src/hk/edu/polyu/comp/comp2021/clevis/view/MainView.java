package hk.edu.polyu.comp.comp2021.clevis.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main view class for the Clevis Geometry System MVC application.
 * Handles the primary GUI layout and component initialization.
 */
public class MainView {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CANVAS_SIZE = 600;

    private final JFrame mainFrame;
    private final DrawingCanvas drawingCanvas;
    private final JTextArea outputArea;
    private final JTextField commandInput;
    private final JButton sendButton;
    private final JMenuItem exitItem;
    private final JMenuItem undoItem;
    private final JMenuItem redoItem;
    private final JMenuItem aboutItem;

    /**
     * Constructs the main view and initializes all GUI components.
     */
    public MainView() {
        mainFrame = new JFrame("Clevis");
        mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingCanvas = new DrawingCanvas();
        drawingCanvas.setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
        mainFrame.add(drawingCanvas, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea(20, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel commandPanel = new JPanel(new BorderLayout());
        commandInput = new JTextField();
        sendButton = new JButton("Execute");
        commandPanel.add(commandInput, BorderLayout.CENTER);
        commandPanel.add(sendButton, BorderLayout.EAST);
        rightPanel.add(commandPanel, BorderLayout.SOUTH);

        mainFrame.add(rightPanel, BorderLayout.EAST);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Edit");
        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");
        editMenu.add(undoItem);
        editMenu.add(redoItem);

        JMenu helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        mainFrame.setJMenuBar(menuBar);
    }

    /**
     * Returns the main application frame.
     * @return the main JFrame instance
     */
    public JFrame getMainFrame() { return mainFrame; }

    /**
     * Returns the drawing canvas component.
     * @return the DrawingCanvas instance
     */
    public DrawingCanvas getDrawingCanvas() { return drawingCanvas; }

    /**
     * Returns the output text area.
     * @return the JTextArea for output display
     */
    public JTextArea getOutputArea() { return outputArea; }

    /**
     * Returns the command input field.
     * @return the JTextField for command input
     */
    public JTextField getCommandInput() { return commandInput; }

    /**
     * Returns the execute button.
     * @return the JButton for executing commands
     */
    public JButton getSendButton() { return sendButton; }

    /**
     * Returns the exit menu item.
     * @return the JMenuItem for exiting the application
     */
    public JMenuItem getExitItem() { return exitItem; }

    /**
     * Returns the undo menu item.
     * @return the JMenuItem for undo operations
     */
    public JMenuItem getUndoItem() { return undoItem; }

    /**
     * Returns the redo menu item.
     * @return the JMenuItem for redo operations
     */
    public JMenuItem getRedoItem() { return redoItem; }

    /**
     * Returns the about menu item.
     * @return the JMenuItem for about information
     */
    public JMenuItem getAboutItem() { return aboutItem; }

    /**
     * Displays the main application window.
     */
    public void show() {
        mainFrame.setVisible(true);
    }
}
package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.view.MainView;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

/**
 * Main controller for the Clevis Geometry System.
 * Handles user interactions and coordinates between view and model.
 */
public class MainController {
    private static final double SCALE_SPEED = 1.1;
    private final MainView view;
    private int lastMouseX;
    private int lastMouseY;
    private boolean isDragging = false;

    /**
     * Constructs the main controller and initializes the application.
     */
    public MainController() {
        view = new MainView();
        setupEventHandlers();
        view.show();
    }

    private void setupEventHandlers() {
        // Command execution
        view.getSendButton().addActionListener(e -> handleCommand());
        view.getCommandInput().addActionListener(e -> handleCommand());

        // Menu items
        view.getExitItem().addActionListener(e -> System.exit(0));
        view.getUndoItem().addActionListener(e -> handleUndo());
        view.getRedoItem().addActionListener(e -> handleRedo());
        view.getAboutItem().addActionListener(e -> handleAbout());

        // Mouse zoom
        view.getDrawingCanvas().addMouseWheelListener(e -> {
            double zoomFactor = e.getWheelRotation() < 0 ? SCALE_SPEED : 1.0/SCALE_SPEED;
            view.getDrawingCanvas().zoom(zoomFactor);
        });

        // Mouse drag panning
        view.getDrawingCanvas().addMouseListener(new CanvasMouseAdapter());

        view.getDrawingCanvas().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging && SwingUtilities.isLeftMouseButton(e)) {
                    int currentX = e.getX();
                    int currentY = e.getY();
                    int deltaX = currentX - lastMouseX;
                    int deltaY = currentY - lastMouseY;
                    lastMouseX = currentX;
                    lastMouseY = currentY;
                    double modelDeltaX = deltaX * view.getDrawingCanvas().getPixelScale();
                    double modelDeltaY = -deltaY * view.getDrawingCanvas().getPixelScale();
                    view.getDrawingCanvas().pan(modelDeltaX, modelDeltaY);
                }
            }
        });
    }

    private void handleCommand() {
        String command = view.getCommandInput().getText();
        if (command != null && !command.trim().isEmpty()) {
            String result = Clevis.Execute(command);
            appendOutput("> " + command);
            appendOutput(result);
            view.getCommandInput().setText("");
            view.getDrawingCanvas().repaint();
        }
    }

    private void handleUndo() {
        String result = Clevis.Execute("undo");
        appendOutput("> undo");
        appendOutput(result);
        view.getDrawingCanvas().repaint();
    }

    private void handleRedo() {
        String result = Clevis.Execute("redo");
        appendOutput("> redo");
        appendOutput(result);
        view.getDrawingCanvas().repaint();
    }

    private void handleAbout() {
        JOptionPane.showMessageDialog(view.getMainFrame(),
                "Clevis\n" +
                        "Author: Group 99\n" +
                        "    Haoyue ZHANG\n" +
                        "    Mingyue XU\n" +
                        "    Shuxiu JIA\n" +
                        "    Enlong DONG\n" +
                        "Version: 1.0\nMVC Architecture\n" +
                        "    You can type help to see all commands.",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void appendOutput(String text) {
        view.getOutputArea().append(text + "\n");
    }

    /**
     * Application entry point.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController());
        Clevis.Boost(args);
    }

    private class CanvasMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                isDragging = true;
                view.getDrawingCanvas().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                isDragging = false;
                view.getDrawingCanvas().setCursor(Cursor.getDefaultCursor());
            }
        }
    }
}
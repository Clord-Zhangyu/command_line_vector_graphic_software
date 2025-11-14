package hk.edu.polyu.comp.comp2021.clevis.view;

import hk.edu.polyu.comp.comp2021.clevis.controller.Clevis;
import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.*;

import java.awt.*;
import javax.swing.*;

/**
 * Custom drawing canvas for displaying geometric shapes and grid.
 * Handles coordinate transformations, zooming, and panning functionality.
 */
public class DrawingCanvas extends JPanel {
    private static final double PIXELS_PER_UNIT = 40.0;
    private static final double MAJOR_UNIT = 1.0;
    private static final double MIN_SCALE = 0.05;
    private static final double MAX_SCALE = 20.0;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color GRID_LIGHT_COLOR = new Color(230, 230, 230);
    private static final Color GRID_DARK_COLOR = new Color(153, 153, 153);
    private static final Color AXIS_COLOR = new Color(102, 102, 102);
    private static final Color CIRCLE_FILL_COLOR = new Color(173, 216, 230, 64);
    private static final Color CIRCLE_BORDER_COLOR = Color.BLUE;
    private static final Color SQUARE_FILL_COLOR = new Color(144, 238, 144, 64);
    private static final Color SQUARE_BORDER_COLOR = Color.GREEN.darker();
    private static final Color LINE_COLOR = new Color(128, 0, 128);
    private static final Color RECTANGLE_FILL_COLOR = new Color(175, 238, 238, 64);
    private static final Color RECTANGLE_BORDER_COLOR = Color.CYAN.darker();
    private static final Color GROUP_BORDER_COLOR = Color.ORANGE.darker();
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final double DEFAULT_SCALE = 1.0;
    private static final double DEFAULT_PAN = 0.0;
    private static final float BORDER_STROKE_WIDTH = 2.0f;
    private static final float AXIS_STROKE_WIDTH = 1.5f;
    private static final double HALF = 0.5;
    private static final int REVERSE_OFFSET = 12;
    private static final int OFFSET = 3;

    private double scale = DEFAULT_SCALE;
    private double panX = DEFAULT_PAN;
    private double panY = DEFAULT_PAN;

    /**
     * Constructs the drawing canvas with default settings.
     */
    public DrawingCanvas() {
        setBackground(BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2);
        for (Object geo : Data.Geometries.values().toArray()) {
            drawGeometry(g2, geo);
        }
    }

    private double toCanvasX(double x) {
        return getWidth() * HALF + (x + panX) * PIXELS_PER_UNIT * scale;
    }

    private double toCanvasY(double y) {
        return getHeight() * HALF - (y + panY) * PIXELS_PER_UNIT * scale;
    }

    private double toCanvasLen(double len) {
        return len * PIXELS_PER_UNIT * scale;
    }

    private void drawGrid(Graphics2D g2) {
        final int width = getWidth();
        final int height = getHeight();
        final double centerX = width / 2.0;
        final double centerY = height / 2.0;
        final double modelLeft = (0 - centerX) / (PIXELS_PER_UNIT * scale) - panX;
        final double modelRight = (width - centerX) / (PIXELS_PER_UNIT * scale) - panX;
        final double modelTop = -(0 - centerY) / (PIXELS_PER_UNIT * scale) - panY;
        final double modelBottom = -(height - centerY) / (PIXELS_PER_UNIT * scale) - panY;
        final double startX = Math.floor(modelLeft / MAJOR_UNIT) * MAJOR_UNIT;
        final double endX = Math.ceil(modelRight / MAJOR_UNIT) * MAJOR_UNIT;
        final double startY = Math.floor(modelBottom / MAJOR_UNIT) * MAJOR_UNIT;
        final double endY = Math.ceil(modelTop / MAJOR_UNIT) * MAJOR_UNIT;
        g2.setColor(GRID_LIGHT_COLOR);
        for (double x = startX; x <= endX; x += MAJOR_UNIT) {
            final int cx = (int) toCanvasX(x);
            g2.drawLine(cx, 0, cx, height);
        }
        for (double y = startY; y <= endY; y += MAJOR_UNIT) {
            final int cy = (int) toCanvasY(y);
            g2.drawLine(0, cy, width, cy);
        }
        g2.setColor(GRID_DARK_COLOR);
        g2.setStroke(new BasicStroke(AXIS_STROKE_WIDTH));
        final int axisY = Math.clamp( (int) toCanvasY(0),0,height);
        g2.drawLine(0, axisY, width, axisY);
        final int axisX = Math.clamp( (int) toCanvasX(0),0,width);
        g2.drawLine(axisX, 0, axisX, height);
        g2.setColor(AXIS_COLOR);
        final int tickPx = (int) Math.max(4.0, 6.0 * Math.sqrt(scale));
        for (double x = startX; x <= endX; x += MAJOR_UNIT) {
            if (Clevis.Compare(x, 0) == 0) continue;
            final int cx = (int) toCanvasX(x);
            if (cx >= 0 && cx <= width) {
                g2.drawLine(cx, axisY - tickPx, cx, axisY + tickPx);
                int labelY = (axisY > centerY) ? axisY - tickPx - OFFSET : axisY + tickPx + REVERSE_OFFSET;
                g2.drawString(String.valueOf((int) x), cx + OFFSET, labelY);
            }
        }
        for (double y = startY; y <= endY; y += MAJOR_UNIT) {
            if (Clevis.Compare(y, 0) == 0) continue;
            final int cy = (int) toCanvasY(y);
            if (cy >= 0 && cy <= height) {
                g2.drawLine(axisX - tickPx, cy, axisX + tickPx, cy);
                int labelX = (axisX > centerX) ? axisX - tickPx - REVERSE_OFFSET : axisX + tickPx + OFFSET;
                g2.drawString(String.valueOf((int) y), labelX, cy - OFFSET);
            }
        }
        final int originX = (int) toCanvasX(0);
        final int originY = (int) toCanvasY(0);
        if (originX >= 0 && originX <= width && originY >= 0 && originY <= height) {
            g2.drawString("0", originX + 5, originY - 2);
        }
    }

    private void drawGeometry(Graphics2D g2, Object geo) {
        if (!(geo instanceof GeoCircle c))
            if (!(geo instanceof GeoSquare s))
                if (!(geo instanceof GeoLine l))
                    if (!(geo instanceof GeoRectangle r)) drawGroup(g2, (GeoGroup) geo);
                    else drawRectangle(g2, r);
                else drawLine(g2, l);
            else drawSquare(g2, s);
        else drawCircle(g2, c);
    }

    private void drawCircle(Graphics2D g2, GeoCircle c) {
        final double centerX = toCanvasX(c.GetX());
        final double centerY = toCanvasY(c.GetY());
        final double radius = toCanvasLen(c.GetR());
        final int x = (int) (centerX - radius);
        final int y = (int) (centerY - radius);
        final int diameter = (int) (2 * radius);
        g2.setColor(CIRCLE_FILL_COLOR);
        g2.fillOval(x, y, diameter, diameter);
        g2.setColor(CIRCLE_BORDER_COLOR);
        g2.setStroke(new BasicStroke(BORDER_STROKE_WIDTH));
        g2.drawOval(x, y, diameter, diameter);
        drawCenteredText(g2, c.GetName(), (int) centerX, (int) centerY);
    }

    private void drawSquare(Graphics2D g2, GeoSquare s) {
        final double x = toCanvasX(s.GetX());
        final double y = toCanvasY(s.GetY());
        final double side = toCanvasLen(s.GetL());
        final int intX = (int) x;
        final int intY = (int) y;
        final int intSide = (int) side;
        g2.setColor(SQUARE_FILL_COLOR);
        g2.fillRect(intX, intY, intSide, intSide);
        g2.setColor(SQUARE_BORDER_COLOR);
        g2.setStroke(new BasicStroke(BORDER_STROKE_WIDTH));
        g2.drawRect(intX, intY, intSide, intSide);
        final int centerX = (int) (x + side / 2);
        final int centerY = (int) (y + side / 2);
        drawCenteredText(g2, s.GetName(), centerX, centerY);
    }

    private void drawLine(Graphics2D g2, GeoLine l) {
        g2.setColor(LINE_COLOR);
        g2.setStroke(new BasicStroke(BORDER_STROKE_WIDTH));
        final double x1 = toCanvasX(l.GetX());
        final double y1 = toCanvasY(l.GetY());
        final double x2 = toCanvasX(l.GetX2());
        final double y2 = toCanvasY(l.GetY2());
        g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        final int centerX = (int) ((x1 + x2) / 2);
        final int centerY = (int) ((y1 + y2) / 2);
        drawCenteredText(g2, l.GetName(), centerX, centerY);
    }

    private void drawRectangle(Graphics2D g2, GeoRectangle r) {
        final double x = toCanvasX(r.GetX());
        final double y = toCanvasY(r.GetY());
        final double width = toCanvasLen(r.GetW());
        final double height = toCanvasLen(r.GetH());
        final int intX = (int) x;
        final int intY = (int) y;
        final int intWidth = (int) width;
        final int intHeight = (int) height;
        g2.setColor(RECTANGLE_FILL_COLOR);
        g2.fillRect(intX, intY, intWidth, intHeight);
        g2.setColor(RECTANGLE_BORDER_COLOR);
        g2.setStroke(new BasicStroke(BORDER_STROKE_WIDTH));
        g2.drawRect(intX, intY, intWidth, intHeight);
        final int centerX = (int) (x + width / 2);
        final int centerY = (int) (y + height / 2);
        drawCenteredText(g2, r.GetName(), centerX, centerY);
    }

    private void drawGroup(Graphics2D g2, GeoGroup g) {
        GeoRectangle bound = g.GetBounding();
        final double x = toCanvasX(bound.GetX());
        final double y = toCanvasY(bound.GetY());
        final double width = toCanvasLen(bound.GetW());
        final double height = toCanvasLen(bound.GetH());
        final int intX = (int) x;
        final int intY = (int) y;
        final int intWidth = (int) width;
        final int intHeight = (int) height;
        g2.setColor(GROUP_BORDER_COLOR);
        g2.setStroke(new BasicStroke(BORDER_STROKE_WIDTH));
        g2.drawRect(intX, intY, intWidth, intHeight);
        final int centerX = (int) (x + width / 2);
        final int centerY = (int) (y + height / 2);
        drawCenteredText(g2, g.GetName(), centerX, centerY);
    }

    private void drawCenteredText(Graphics2D g2, String text, int x, int y) {
        g2.setColor(TEXT_COLOR);
        FontMetrics metrics = g2.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();
        int textX = x - textWidth / 2;
        int textY = y + textHeight / 4;
        g2.setColor(TEXT_COLOR);
        g2.drawString(text, textX, textY);
    }

    /**
     * Zooms the canvas view by the specified factor.
     * @param zoomFactor the factor to zoom by (e.g., 1.1 for 10% zoom in)
     */
    public void zoom(double zoomFactor) {
        scale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale * zoomFactor));
        repaint();
    }

    /**
     * Pans the canvas view by the specified amounts.
     * @param deltaX the horizontal pan amount
     * @param deltaY the vertical pan amount
     */
    public void pan(double deltaX, double deltaY) {
        panX += deltaX;
        panY += deltaY;
        repaint();
    }

    /**
     * Returns the current pixel-to-model coordinate scale factor.
     * This represents how many model units correspond to one pixel on the canvas.
     *
     * @return the scale factor from pixels to model units (model units per pixel)
     */
    public double getPixelScale() {
        return scale / PIXELS_PER_UNIT;
    }
}
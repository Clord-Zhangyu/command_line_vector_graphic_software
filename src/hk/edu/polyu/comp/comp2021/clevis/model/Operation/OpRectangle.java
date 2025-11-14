package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoRectangle;

import java.security.KeyException;

/**
 * The operation object for rectangle command
 */
public class OpRectangle extends OpCreate {

    /**
     * Construct a rectangle operation to be executed
     *
     * @param name the name of the rectangle
     * @param x    the x cordinate of the top-left corner
     * @param y    the y cordinate of the top-left corner
     * @param w    the width of the rectangle
     * @param h    the height of the rectangle
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpRectangle(String name, double x, double y, double w, double h) throws KeyException {
        super(new GeoRectangle(name, x, y, w, h));
    }

    @Override
    public String GetCommand() {
        var geo = (GeoRectangle) Geo;
        return "rectangle " + geo.GetName() + " " + String.format("%.2f", geo.GetX()) + " " + String.format("%.2f", geo.GetY()) +
                " " + String.format("%.2f", geo.GetW()) + " " + String.format("%.2f", geo.GetH());
    }
}

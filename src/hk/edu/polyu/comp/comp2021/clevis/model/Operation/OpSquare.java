package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoSquare;

import java.security.KeyException;

/**
 * The operation object for square command
 */
public class OpSquare extends OpCreate {
    /**
     * Construct a square operation to be executed
     *
     * @param name the name of the square
     * @param x    the x cordinate of the top-left corner
     * @param y    the y cordinate of the top-left corner
     * @param l    the length of the edges
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpSquare(String name, double x, double y, double l) throws KeyException {
        super(new GeoSquare(name, x, y, l));
    }

    @Override
    public String GetCommand() {
        var geo = (GeoSquare) Geo;
        return "square " + geo.GetName() + " " + String.format("%.2f", geo.GetX()) + " " + String.format("%.2f", geo.GetY()) +
                " " + String.format("%.2f", geo.GetL());
    }
}

package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoLine;

import java.security.KeyException;

/**
 * The operation object for line command
 */
public class OpLine extends OpCreate {

    /**
     * Construct a line operation to be executed
     *
     * @param name the name of the line
     * @param x1   the x cordinate of the first point
     * @param y1   the y cordinate of the first point
     * @param x2   the x cordinate of the second point
     * @param y2   the y cordinate of the second point
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpLine(String name, double x1, double y1, double x2, double y2) throws KeyException {
        super(new GeoLine(name, x1, y1, x2, y2));
    }


    @Override
    public String GetCommand() {
        var geo = (GeoLine) Geo;
        return "line " + geo.GetName() + " " + String.format("%.2f", geo.GetX()) + " " + String.format("%.2f", geo.GetY()) +
                " " + String.format("%.2f", geo.GetX2()) + " " + String.format("%.2f", geo.GetY2());
    }
}

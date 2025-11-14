package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoCircle;

import java.security.KeyException;

/**
 * The operation object for circle
 */
public class OpCircle extends OpCreate {
    /**
     * Construct a circle operation to be executed
     *
     * @param name the name of the circle
     * @param x    the x cordinate of the center
     * @param y    the y cordinate of the center
     * @param r    the radius of the circle
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpCircle(String name, double x, double y, double r) throws KeyException {
        super(new GeoCircle(name, x, y, r));
    }

    @Override
    public String GetCommand() {
        var geo = (GeoCircle) Geo;
        return "circle " + geo.GetName() + " " + String.format("%.2f", geo.GetX()) + " " + String.format("%.2f", geo.GetY()) +
                " " + String.format("%.2f", geo.GetR());
    }
}

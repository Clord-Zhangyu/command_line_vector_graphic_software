package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.security.KeyException;

/**
 * The operation object for move command
 */
public class OpMove implements Operation {

    private final Geometry Geo;
    private final double Dx;
    private final double Dy;

    /**
     * Construct a move operation to be executed
     *
     * @param name the name of the object to be moved
     * @param dx   the distance in x coordinate
     * @param dy   the distance in y coordinate
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpMove(String name, double dx, double dy) throws KeyException {
        if (!Data.Geometries.containsKey(name))
            throw new KeyException("\"" + name + "\" does not exist.");
        Geo = Data.Geometries.get(name);
        if (Geo.GetParent() != null) throw new KeyException("\"" + Geo.GetName() + "\" unaccessible.");
        Dx = dx;
        Dy = dy;
    }

    @Override
    public void Execute() {
        Geo.SetX(Geo.GetX() + Dx);
        Geo.SetY(Geo.GetY() + Dy);
    }

    @Override
    public void Recover() {
        Geo.SetX(Geo.GetX() - Dx);
        Geo.SetY(Geo.GetY() - Dy);
    }

    @Override
    public String GetCommand() {
        return "move " + Geo.GetName() + " " + String.format("%.2f", Dx) + " " + String.format("%.2f", Dy);
    }
}

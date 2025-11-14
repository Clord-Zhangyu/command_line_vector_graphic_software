package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoRectangle;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.security.KeyException;

/**
 * The query object for boundingbox
 */
public class OpBoundingbox implements Query {
    private final Geometry Geo;

    /**
     * Contruct a boundingbox query to be executed
     *
     * @param name the name of the geometry
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpBoundingbox(String name) throws KeyException {
        if (!Data.Geometries.containsKey(name))
            throw new KeyException("\"" + name + "\" does not exist.");
        Geo = Data.Geometries.get(name);
        if (Geo.GetParent() != null) throw new KeyException("\"" + Geo.GetName() + "\" unaccessible.");
    }

    @Override
    public QureyResult Execute() {
        var bounding = (GeoRectangle) Geo.GetBounding();
        return new QureyResult(bounding,
                bounding.GetX() + " " + bounding.GetY() + " " + bounding.GetW() + " " + bounding.GetH());
    }

    @Override
    public String GetCommand() {
        return "boundingbox " + Geo.GetName();
    }
}

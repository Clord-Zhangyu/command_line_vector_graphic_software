package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoRectangle;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.security.KeyException;

/**
 * The query object for intersect
 */
public class OpIntersect implements Query {
    private final Geometry geo1;
    private final Geometry geo2;

    /**
     * Contruct an intersect query to be executed
     *
     * @param name1 the name of the first geometry
     * @param name2 the name of the second geometry
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpIntersect(String name1, String name2) throws KeyException {
        if (!Data.Geometries.containsKey(name1))
            throw new KeyException("\"" + name1 + "\" does not exist.");
        if (!Data.Geometries.containsKey(name2))
            throw new KeyException("\"" + name2 + "\" does not exist.");
        geo1 = Data.Geometries.get(name1);
        if (geo1.GetParent() != null) throw new KeyException("\"" + geo1.GetName() + "\" unaccessible.");
        geo2 = Data.Geometries.get(name2);
        if (geo1.GetParent() != null) throw new KeyException("\"" + geo1.GetName() + "\" unaccessible.");
    }

    @Override
    public QureyResult Execute() {
        var bounding1 = (GeoRectangle) geo1.GetBounding();
        var bounding2 = (GeoRectangle) geo2.GetBounding();
        double x1 = bounding1.GetX(), y1 = bounding1.GetY(), w1 = bounding1.GetW(), h1 = bounding1.GetH();
        double x2 = bounding2.GetX(), y2 = bounding2.GetY(), w2 = bounding2.GetW(), h2 = bounding2.GetH();
        double r1 = x1 + w1, b1 = y1 - h1;
        double r2 = x2 + w2, b2 = y2 - h2;
        boolean separated = r1 < x2 || r2 < x1 || b1 > y2 || b2 > y1;
        if (!separated) return new QureyResult(true, "True");
        else return new QureyResult(false, "False");
    }

    @Override
    public String GetCommand() {
        return "intersect " + geo1.GetName() + " " + geo2.GetName();
    }
}

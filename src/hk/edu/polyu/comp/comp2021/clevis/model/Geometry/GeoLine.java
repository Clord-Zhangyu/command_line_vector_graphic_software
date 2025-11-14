package hk.edu.polyu.comp.comp2021.clevis.model.Geometry;

import hk.edu.polyu.comp.comp2021.clevis.controller.Clevis;

import java.util.LinkedHashMap;

/**
 * The line geometry object
 */
public class GeoLine extends Geometry {

    private final double Dx;
    private final double Dy;

    /**
     * Create a line geometry
     *
     * @param name the name of the line
     * @param x1   the x cordinate of the first point
     * @param y1   the y cordinate of the first point
     * @param x2   the x cordinate of the second point
     * @param y2   the y cordinate of the second point
     */
    public GeoLine(String name, double x1, double y1, double x2, double y2) {
        super(name, x1, y1);
        Dx = x2 - x1;
        Dy = y2 - y1;
    }

    @Override
    public LinkedHashMap<String, Object> GetInfo() {
        var res = new LinkedHashMap<String, Object>();
        res.put("Type", "Line");
        res.put("Name", GetName());
        if (GetParent() != null) res.put("Group", GetParent().GetName());
        res.put("X1", GetX());
        res.put("Y1", GetY());
        res.put("X2", GetX2());
        res.put("Y2", GetY2());
        return res;
    }

    @Override
    public GeoRectangle GetBounding() {
        return new GeoRectangle("Bounding",
                Math.min(GetX(), GetX2()), Math.max(GetY(), GetY2()),
                Math.abs(GetX() - GetX2()), Math.abs(GetY() - GetY2()));
    }

    @Override
    public boolean Contains(double x, double y) {
        var x1 = GetX();
        var y1 = GetY();
        var lineLengthSquared = Dx * Dx + Dy * Dy;
        var t = ((x - x1) * Dx + (y - y1) * Dy) / lineLengthSquared;
        t = Math.clamp(t, 0, 1);
        var closex = x1 + t * Dx;
        var closey = y1 + t * Dy;
        var dx = x - closex;
        var dy = y - closey;
        return Clevis.Compare(Math.sqrt(dx * dx + dy * dy), 0) == 0;
    }

    /**
     * Get the x coordinate of the second point
     *
     * @return the x cordinate of the second point
     */
    public double GetX2() {
        return GetX() + Dx;
    }

    /**
     * Get the x coordinate of the second point
     *
     * @return the y cordinate of the second point
     */
    public double GetY2() {
        return GetY() + Dy;
    }
}

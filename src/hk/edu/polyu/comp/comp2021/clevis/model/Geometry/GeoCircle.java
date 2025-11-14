package hk.edu.polyu.comp.comp2021.clevis.model.Geometry;

import hk.edu.polyu.comp.comp2021.clevis.controller.Clevis;

import java.util.LinkedHashMap;

/**
 * The circle geometry object
 */
public class GeoCircle extends Geometry {

    private final double R;

    /**
     * Create a circle geometry
     *
     * @param _name the name of the circle
     * @param x     the x cordinate of the center
     * @param y     the y cordinate of the center
     * @param r     the radius of the circle
     */
    public GeoCircle(String _name, double x, double y, double r) {
        super(_name, x, y);
        R = r;
    }

    @Override
    public LinkedHashMap<String, Object> GetInfo() {
        var res = super.GetInfo();
        if (res == null) return null;
        res.put("Radius", R);
        return res;
    }

    @Override
    public GeoRectangle GetBounding() {
        double cx = GetX(), cy = GetY(), r = GetR();
        return new GeoRectangle("Bounding", cx - r, cy + r, r * 2, r * 2);
    }

    @Override
    public boolean Contains(double x, double y) {
        var dx = GetX() - x;
        var dy = GetY() - y;
        return Clevis.Compare(Math.sqrt(dx * dx + dy * dy), GetR()) <= 0;
    }

    /**
     * Get the radius of the circle
     *
     * @return the radius of the circle
     */
    public double GetR() {
        return R;
    }
}

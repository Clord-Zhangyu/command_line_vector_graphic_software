package hk.edu.polyu.comp.comp2021.clevis.model.Geometry;

import java.util.LinkedHashMap;

/**
 * The geometry object for square
 */
public class GeoSquare extends Geometry {

    private final double L;

    /**
     * Create a square geometry
     *
     * @param name the name of the square
     * @param x    the x cordinate of the top-left corner
     * @param y    the y cordinate of the top-left corner
     * @param l    the length of the edges
     */
    public GeoSquare(String name, double x, double y, double l) {
        super(name, x, y);
        L = l;
    }

    @Override
    public LinkedHashMap<String, Object> GetInfo() {
        var res = super.GetInfo();
        if (res == null) return null;
        res.put("Length", L);
        return res;
    }

    @Override
    public GeoRectangle GetBounding() {
        return new GeoRectangle("Bounding", GetX(), GetY(), GetL(), GetL());
    }

    @Override
    public boolean Contains(double x, double y) {
        return GetBounding().Contains(x, y);
    }

    /**
     * Get the length of the edges
     *
     * @return the length of the edges
     */
    public double GetL() {
        return L;
    }

}

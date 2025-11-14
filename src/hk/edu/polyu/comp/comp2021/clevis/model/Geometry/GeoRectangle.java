package hk.edu.polyu.comp.comp2021.clevis.model.Geometry;

import hk.edu.polyu.comp.comp2021.clevis.controller.Clevis;

import java.util.LinkedHashMap;

/**
 * The rectangle geometry object
 */
public class GeoRectangle extends Geometry {

    private double W;
    private double H;

    /**
     * Create a rectangle geometry
     *
     * @param name the name of the rectangle
     * @param x    the x cordinate of the top-left corner
     * @param y    the y cordinate of the top-left corner
     * @param w    the width of the rectangle
     * @param h    the height of the rectangle
     */
    public GeoRectangle(String name, double x, double y, double w, double h) {
        super(name, x, y);
        SetW(w);
        SetH(h);
    }

    @Override
    public LinkedHashMap<String, Object> GetInfo() {
        var res = super.GetInfo();
        if (res == null) return null;
        res.put("Width", W);
        res.put("Height", H);
        return res;
    }

    @Override
    public GeoRectangle GetBounding() {
        return new GeoRectangle("Bounding", GetX(), GetY(), GetW(), GetH());
    }

    @Override
    public boolean Contains(double x, double y) {
        var rx = GetX();
        var ry = GetY();
        var closex = Math.clamp(x, rx, rx + GetW());
        var closey = Math.clamp(y, ry - GetH(), ry);
        var dx = closex - x;
        var dy = closey - y;
        return Clevis.Compare(Math.sqrt(dx * dx + dy * dy), 0) == 0;
    }

    /**
     * Get the width of the second point
     *
     * @return the width of the rectangle
     */
    public double GetW() {
        return W;
    }

    /**
     * Set the width of the second point
     *
     * @param w the width of the rectangle
     */
    void SetW(double w) {
        W = w;
    }

    /**
     * Get the height of the second point
     *
     * @return the height of the rectangle
     */
    public double GetH() {
        return H;
    }

    /**
     * Set the height of the second point
     *
     * @param h the height of the rectangle
     */
    void SetH(double h) {
        H = h;
    }
}

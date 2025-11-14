package hk.edu.polyu.comp.comp2021.clevis.model.Geometry;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * The abstract class for all geometry
 */
public abstract class Geometry {
    private Geometry Parent;
    private final String Name;
    private double X;
    private double Y;

    /**
     * Super create for all geometry
     *
     * @param name the name of the geometry
     * @param x    the x coordinate of the geometry
     * @param y    the y coordinate of the geometry
     */
    protected Geometry(String name, double x, double y) {
        Name = name;
        X = x;
        Y = y;
    }

    /**
     * Get an info in the form of LinkedHashMap&lt;String, Object&gt;
     *
     * @return the full information
     */
    public LinkedHashMap<String, Object> GetInfo() {
        var res = new LinkedHashMap<String, Object>();
        res.put("Type", getClass().getSimpleName().substring(3));
        res.put("Name", Name);
        res.put("X", GetX());
        res.put("Y", GetY());
        return res;
    }

    /**
     * Calculate the bounding box
     *
     * @return the bounding box of the geometry
     */
    public abstract Geometry GetBounding();

    /**
     * Check if a point is inside the geometry
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return whether the point is inside the geometry
     */
    public abstract boolean Contains(double x, double y);

    /**
     * Get the group geometry that this geometry belongs to
     *
     * @return the parent of the geometry
     */
    public Geometry GetParent() {
        return Parent;
    }

    /**
     * Set the parent group gemometry
     *
     * @param parent the parent of the geometry
     */
    public void SetParent(Geometry parent) {
        Parent = parent;
    }

    /**
     * Get the name of the geometry
     *
     * @return the name of the geometry
     */
    public String GetName() {
        return Name;
    }

    /**
     * Get the x cordinate of the geometry
     *
     * @return the x cordinate of the geometry
     */
    public double GetX() {
        if (Parent != null)
            return X + Parent.GetX();
        return X;
    }

    /**
     * Set the x cordinate of the geometry
     *
     * @param x the x cordinate of the geometry
     */
    public void SetX(double x) {
        this.X = x;
    }

    /**
     * Get the y cordinate of the geometry
     *
     * @return the y cordinate of the geometry
     */
    public double GetY() {
        if (Parent != null)
            return Y + Parent.GetY();
        return Y;
    }

    /**
     * Set the y cordinate of the geometry
     *
     * @param y the y cordinate of the geometry
     */
    public void SetY(double y) {
        this.Y = y;
    }
}

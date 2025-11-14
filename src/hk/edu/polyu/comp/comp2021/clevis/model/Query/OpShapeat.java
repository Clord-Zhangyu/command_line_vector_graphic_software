package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * The query object fot shapeat
 */
public class OpShapeat implements Query {
    private final double X;
    private final double Y;

    /**
     * Contruct a shapeAt query to be executed
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public OpShapeat(double x, double y) {
        X = x;
        Y = y;
    }

    @Override
    public QureyResult Execute() {
        List<Map.Entry<String, Geometry>> entries = new ArrayList<>(Data.Geometries.entrySet());
        ListIterator<Map.Entry<String, Geometry>> it = entries.listIterator(entries.size());
        while (it.hasPrevious()) {
            Map.Entry<String, Geometry> entry = it.previous();
            var res = entry.getValue().Contains(X, Y);
            if (res) {
                return new QureyResult(entry.getValue(), entry.getKey());
            }
        }
        return new QureyResult(null, "No result");
    }

    @Override
    public String GetCommand() {
        return "shapeAt " + X + " " + Y;
    }
}

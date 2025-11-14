package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.util.*;

/**
 * The query object fot listall
 */
public class OpListall implements Query {
    /**
     * Construct an intersect query to be executed
     */
    public OpListall() {

    }

    @Override
    public QureyResult Execute() {
        var res = new LinkedHashMap<String, LinkedHashMap<String, Object>>();
        var discription = new StringBuilder();
        List<Map.Entry<String, Geometry>> entries = new ArrayList<>(Data.Geometries.entrySet());
        ListIterator<Map.Entry<String, Geometry>> it = entries.listIterator(entries.size());
        var first = true;
        while (it.hasPrevious()) {
            Map.Entry<String, Geometry> entry = it.previous();
            if (entry.getValue().GetParent() != null) continue;
            var info = entry.getValue().GetInfo();
            res.put(entry.getKey(), info);
            if (first) first = false;
            else discription.append("\n\n");
            discription.append(OpList.GetDescription(info, 0));
        }
        return new QureyResult(res, discription.toString());
    }

    @Override
    public String GetCommand() {
        return "listAll";
    }
}

package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.security.KeyException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * The query object fot list
 */
public class OpList implements Query {

    private final Geometry Geo;

    /**
     * Contruct a list query to be executed
     *
     * @param name the name of the object
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpList(String name) throws KeyException {
        if (!Data.Geometries.containsKey(name))
            throw new KeyException("\"" + name + "\" does not exist.");
        Geo = Data.Geometries.get(name);
        if(Geo.GetParent()!=null) throw new KeyException("\"" + Geo.GetName() + "\" unaccessible.");
    }
    /**
     * Translate the information in LinkedHashMap&lt;String, Object&gt; form to string with indentations
     *
     * @param information the information get from the Geometry
     * @param indentation the indentation before each line
     * @return the description of the Geometry
     */

    public static String GetDescription(LinkedHashMap<String, Object> information, int indentation) {

        var description = new StringBuilder();
        var first = true;
        for (var info : information.entrySet()) {
            if (first) first = false;
            else description.append("\n");
            description.append("\t".repeat(indentation));
            description.append(info.getKey()).append(":");
            var value = info.getValue();
            if (value instanceof LinkedList) {
                @SuppressWarnings("unchecked")
                var information1 = (LinkedList<LinkedHashMap<String, Object>>) value;
                var first1 = true;
                for (var info1 : information1) {
                    if (first1) first1 = false;
                    else description.append("\n");
                    description.append("\n");
                    description.append(GetDescription(info1, indentation + 1));
                }
            } else {
                description.append(" ");
                if (value instanceof Double)
                    description.append(String.format("%.2f", value));
                else description.append(value);
            }
        }
        return description.toString();
    }

    @Override
    public QureyResult Execute() throws KeyException {
        if (Geo.GetParent() != null) throw new KeyException("\"" + Geo.GetName() + "\" unaccessible.");
        var res = Geo.GetInfo();
        return new QureyResult(res, GetDescription(res, 0));
    }

    @Override
    public String GetCommand() {
        return "list" + Geo.GetName();
    }
}

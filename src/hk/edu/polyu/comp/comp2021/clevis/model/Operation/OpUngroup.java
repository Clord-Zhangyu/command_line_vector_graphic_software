package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoGroup;

import java.security.KeyException;

/**
 * The operation object for ungroup
 */
public class OpUngroup implements Operation {
    private final GeoGroup Geo;

    /**
     * Construct an ungroup operation to be executed
     *
     * @param name name of the group
     * @throws KeyException thrown when key is invalid
     */
    public OpUngroup(String name) throws KeyException {
        if (!Data.Geometries.containsKey(name))
            throw new KeyException("\"" + name + "\" does not exist.");
        var geo = Data.Geometries.get(name);
        if (geo.getClass() != GeoGroup.class)
            throw new KeyException("\"" + name + "\" is not a group.");
        Geo = (GeoGroup)geo;
        if (Geo.GetParent() != null) throw new KeyException("\"" + Geo.GetName() + "\" unaccessible.");
    }

    @Override
    public void Execute() {
        for (var son : Geo.GetSons()) {
            son.SetParent(null);
            son.SetX(son.GetX() + Geo.GetX());
            son.SetY(son.GetY() + Geo.GetY());
        }
        Data.Geometries.remove(Geo.GetName());
    }

    @Override
    public void Recover() {
        for (var son : Geo.GetSons()) {
            son.SetParent(Geo);
            son.SetX(son.GetX() - Geo.GetX());
            son.SetY(son.GetY() - Geo.GetY());
        }
        Data.Geometries.put(Geo.GetName(), Geo);
    }

    @Override
    public String GetCommand() {
        StringBuilder command = new StringBuilder("ungroup " + Geo.GetName());
        for (var son : Geo.GetSons())
            command.append(" ").append(son.GetName());
        return command.toString();
    }
}

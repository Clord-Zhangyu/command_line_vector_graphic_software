package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.security.KeyException;

/**
 * The operation object for delete
 */
public class OpDelete implements Operation {
    private final Geometry Geo;

    /**
     * Construct a delete operation to be executed
     *
     * @param name name of the shape to be deleted
     * @throws KeyException thrown when key is invalid
     */
    public OpDelete(String name) throws KeyException {
        if (!Data.Geometries.containsKey(name))
            throw new KeyException("\"" + name + "\" does not exist.");
        Geo = Data.Geometries.get(name);
        if (Geo.GetParent() != null) throw new KeyException("\"" + Geo.GetName() + "\" unaccessible.");
    }

    @Override
    public void Execute() {
        Data.Geometries.remove(Geo.GetName());
    }

    @Override
    public void Recover() {
        Data.Geometries.put(Geo.GetName(), Geo);
    }

    @Override
    public String GetCommand() {
        return "delete " + Geo.GetName();
    }
}

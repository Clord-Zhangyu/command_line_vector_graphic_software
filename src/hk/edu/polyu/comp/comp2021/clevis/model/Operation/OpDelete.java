package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoGroup;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

/**
 * The operation object for delete
 */
public class OpDelete implements Operation {
    private final Geometry Geo;
    private final List<Geometry> Geos;

    /**
     * Construct a delete operation to be executed
     *
     * @param name name of the shape to be deleted
     * @throws KeyException thrown when duplicate or inaccessable keys exists
     */
    public OpDelete(String name) throws KeyException {
        if (!Data.Geometries.containsKey(name))
            throw new KeyException("\"" + name + "\" does not exist.");
        Geo = Data.Geometries.get(name);
        if (Geo.GetParent() != null) throw new KeyException("\"" + Geo.GetName() + "\" unaccessible.");
        Geos = new ArrayList<>();
    }
    private void Delete(Geometry geo) {
        Geos.add(geo);
        Data.Geometries.remove(geo.GetName());
        Data.GeometryZOrder.remove(geo.getZ());
        if(geo instanceof GeoGroup group)
            for (var son : group.GetSons())
                Delete(son);
    }

    @Override
    public void Execute() {
        Delete(Geo);
    }

    @Override
    public void Recover() {
        for (Geometry geo : Geos) {
            Data.Geometries.put(geo.GetName(), geo);
            Data.GeometryZOrder.put(geo.getZ(),geo);
        }
    }

    @Override
    public String GetCommand() {
        return "delete " + Geo.GetName();
    }
}

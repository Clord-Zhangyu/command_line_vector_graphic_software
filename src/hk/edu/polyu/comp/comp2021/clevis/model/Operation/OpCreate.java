package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.security.KeyException;

/**
 * The base class for operations that creates geometry
 */
public abstract class OpCreate implements Operation {

    /**
     * the geometry created
     */
    protected final Geometry Geo;

    /**
     * Construct a creation type operation to be executed
     *
     * @param geo the geometry that the operation creates
     * @throws KeyException thrown when duplicate keys exists
     */
    protected OpCreate(Geometry geo) throws KeyException {
        if (Data.Geometries.containsKey(geo.GetName()))
            throw new KeyException("Key \"" + geo.GetName() + "\" already exists.");
        Geo = geo;
    }

    @Override
    public void Execute() {
        Data.Geometries.put(Geo.GetName(), Geo);
    }

    @Override
    public void Recover() {
        Data.Geometries.remove(Geo.GetName());
    }
}


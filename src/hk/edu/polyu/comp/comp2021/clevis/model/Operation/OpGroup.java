package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.GeoGroup;

import java.security.KeyException;

/**
 * The operation object for group command
 */
public class OpGroup extends OpCreate {

    /**
     * Construct a group operation to be executed
     *
     * @param name       the name of the group
     * @param collection the collection that should form a group
     * @throws KeyException thrown when duplicate keys exists
     */
    public OpGroup(String name, String[] collection) throws KeyException {
        super(new GeoGroup(name, collection));
        var geo = (GeoGroup) Geo;
        if (geo.GetSons().size() == 1)
            throw new KeyException("one member can not form a group.");
        for (var son : geo.GetSons()) {
            if (son.GetParent() != null) throw new KeyException("\"" + son.GetName() + "\" unaccessible.");
        }
    }

    @Override
    public void Execute() {
        for (var son : ((GeoGroup) Geo).GetSons()) {
            son.SetParent(Geo);
        }
        super.Execute();
    }

    @Override
    public void Recover() {
        for (var son : ((GeoGroup) Geo).GetSons()) {
            son.SetParent(null);
        }
        super.Recover();
    }

    @Override
    public String GetCommand() {
        var geo = (GeoGroup) Geo;
        StringBuilder command = new StringBuilder("group " + geo.GetName());
        for (var son : geo.GetSons())
            command.append(" ").append(son.GetName());
        return command.toString();
    }
}

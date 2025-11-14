package hk.edu.polyu.comp.comp2021.clevis.model.Geometry;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;

import java.util.*;

/**
 * The group geometry object
 */
public class GeoGroup extends Geometry {

    private final List<Geometry> Sons;

    /**
     * Create a group geometry
     *
     * @param name       the name of the group
     * @param collection the collections contained in the group
     */
    public GeoGroup(String name, String[] collection) {
        super(name, 0, 0);
        Sons = new ArrayList<>();
        var sortingHashset = new HashSet<String>();
        for (var son : collection) {
            if (son.compareTo("") == 0) continue;
            if (!Data.Geometries.containsKey(son))
                throw new RuntimeException("\"" + son + "\" does not exist.");
            sortingHashset.add(son);
        }
        List<Map.Entry<String, Geometry>> entries = new ArrayList<>(Data.Geometries.entrySet());
        ListIterator<Map.Entry<String, Geometry>> it = entries.listIterator(entries.size());
        while (it.hasPrevious()) {
            Map.Entry<String, Geometry> entry = it.previous();
            if (sortingHashset.contains(entry.getKey()))
                Sons.add(entry.getValue());
        }
    }

    @Override
    public LinkedHashMap<String, Object> GetInfo() {
        var res = new LinkedHashMap<String, Object>();
        res.put("Type", "Group");
        res.put("Name", GetName());
        var members = new LinkedList<LinkedHashMap<String, Object>>();
        for (var son : Sons)
            members.add(son.GetInfo());
        res.put("GroupMembers", members);
        return res;
    }

    @Override
    public GeoRectangle GetBounding() {
        GeoRectangle bounding = (GeoRectangle) GetSons().getFirst().GetBounding();
        for (int i = 1; i < GetSons().size(); i++) {
            GeoRectangle additionalBounding = (GeoRectangle) GetSons().get(i).GetBounding();
            if (additionalBounding.GetX() < bounding.GetX()) {
                bounding.SetW(bounding.GetX() - additionalBounding.GetX() + bounding.GetW());
                bounding.SetX(additionalBounding.GetX());
            }
            if (additionalBounding.GetY() > bounding.GetY()) {
                bounding.SetH(additionalBounding.GetY() - bounding.GetY() + bounding.GetH());
                bounding.SetY(additionalBounding.GetY());
            }
            if (additionalBounding.GetX() + additionalBounding.GetW() > bounding.GetX() + bounding.GetW()) {
                bounding.SetW(additionalBounding.GetX() - bounding.GetX() + additionalBounding.GetW());
            }
            if (additionalBounding.GetY() - additionalBounding.GetH() < bounding.GetY() - bounding.GetH()) {
                bounding.SetH(bounding.GetY() - additionalBounding.GetY() + additionalBounding.GetH());
            }
        }
        return bounding;
    }

    @Override
    public boolean Contains(double x, double y) {
        for (var son : Sons)
            if (son.Contains(x, y)) return true;
        return false;
    }

    /**
     * Get the members of the group
     *
     * @return the sons of the group geometry
     */
    public List<Geometry> GetSons() {
        return Sons;
    }
}

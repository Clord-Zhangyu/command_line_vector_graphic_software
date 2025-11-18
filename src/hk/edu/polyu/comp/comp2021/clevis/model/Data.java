package hk.edu.polyu.comp.comp2021.clevis.model;

import hk.edu.polyu.comp.comp2021.clevis.model.Operation.Operation;
import hk.edu.polyu.comp.comp2021.clevis.model.Geometry.Geometry;

import java.util.*;

/**
 * The class for all data
 */
public class Data {
    /**
     * All geometries indexed by name
     */
    public static final Map<String, Geometry> Geometries = new HashMap<>();
    /**
     * All geometries indexed by z-order
     */
    public static final Map<Integer, Geometry> GeometryZOrder = new TreeMap<>();
    /**
     * The undo stack
     */
    public static final Deque<Operation> UndoStack = new LinkedList<>();
    /**
     * The redo stack
     */
    public static final Stack<Operation> RedoStack = new Stack<>();
    private static int CommandCount;
    private static int ZCount;

    /**
     * Get the count for the new command. After executing this the count will be increased automatically
     *
     * @return the count for a new command;
     */
    public static int GetCommandCount() {
        return CommandCount++;
    }

    /**
     * Get the count for the new geometry. All existed geometry will contribute to this count. After executing this the count will be increased automatically
     * @return the count of geometry
     */
    public static int GetZCount() {return ZCount++;}

    /**
     * Clear all data
     */
    public static void Clear() {
        Geometries.clear();
        UndoStack.clear();
        RedoStack.clear();
        CommandCount = 0;
        ZCount = 0;
    }
}

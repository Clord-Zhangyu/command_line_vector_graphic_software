package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;

/**
 * The query object fot redo
 */
public class OpRedo implements Query {
    /**
     * Contruct a redo query to be executed
     */
    public OpRedo() {

    }

    @Override
    public QureyResult Execute() throws IllegalAccessException {
        if (Data.RedoStack.isEmpty())
            throw new IllegalAccessException("There is no command to redo.");
        var command = Data.RedoStack.pop();
        command.Execute();
        Data.UndoStack.add(command);
        return new QureyResult(true, "Succeed");
    }

    @Override
    public String GetCommand() {
        return "redo";
    }
}

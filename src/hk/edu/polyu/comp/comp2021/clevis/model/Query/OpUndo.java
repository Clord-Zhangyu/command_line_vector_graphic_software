package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;

/**
 * The query object fot undo
 */
public class OpUndo implements Query {
    /**
     * Contruct an undo query to be executed
     */
    public OpUndo() {

    }

    @Override
    public QureyResult Execute() throws IllegalAccessException {
        if (Data.UndoStack.isEmpty())
            throw new IllegalAccessException("There is no command to undo.");
        var command = Data.UndoStack.removeLast();
        command.Recover();
        Data.RedoStack.push(command);
        return new QureyResult(true, "Succeed");
    }

    @Override
    public String GetCommand() {
        return "undo";
    }
}

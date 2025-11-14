package hk.edu.polyu.comp.comp2021.clevis.model.Query;

/**
 * The query object fot quit
 */
public class OpQuit implements Query {
    /**
     * Contruct a quit query to be executed
     */
    public OpQuit() {

    }

    @Override
    public QureyResult Execute() {
        System.exit(0);
        return null;
    }

    @Override
    public String GetCommand() {
        return "quit";
    }
}

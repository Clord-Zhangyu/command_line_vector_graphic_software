package hk.edu.polyu.comp.comp2021.clevis.model.Query;

/**
 * The query object for help, will not be recorded in the log
 */
public class OpHelp implements Query {
    /**
     * Contruct a help query to be executed
     */
    public OpHelp() {

    }

    @Override
    public QureyResult Execute(){
        var allCommand = """
                All commands are as follows, you can enter the head to get the detailed parameters:
                boundingbox name
                circle      name x y r
                delete      name
                group       name names...
                help
                intersect   name name
                line        name x1 y1 x2 y2
                list        name
                listAll
                move        name x y
                quit
                rectangle   name x y w h
                redo
                shapeAt     x y
                square      name x y l
                undo
                ungroup     name
                """;
        return new QureyResult(null, allCommand);
    }

    @Override
    public String GetCommand() {
        return null;
    }
}

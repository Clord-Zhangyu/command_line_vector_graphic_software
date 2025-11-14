package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.model.Operation.Operation;
import hk.edu.polyu.comp.comp2021.clevis.model.Query.Query;
import hk.edu.polyu.comp.comp2021.clevis.model.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.KeyException;
import java.util.*;

/**
 * javadoc -d doc -sourcepath src -subpackages Clevis
 * <p>
 * The entry class for Clevis
 * <p>API usage example:</p>
 * <pre><code>
 *     public static void main() throws KeyException, IllegalAccessException {
 *         Clevis.Boost(null);
 *         // make an operation
 *         Clevis.RunOperation(new OpCircle("a", 0, 0, 1));
 *         // get structualized data
 *         // make a query
 *         var res = Clevis.RunQuery(new OpList("a"));
 *         System.out.println(res.GetDescription());
 *         //@SuppressWarnings("unchecked")
 *         var data = ((LinkedHashMap&lt;String, Object>) res.GetResult());
 *         // Check the type name to makesure you get the right type
 *         if (("Circle").compareTo((String) data.get("Type")) != 0) System.out.println("This is not a circle");
 *         else {
 *             var Radius = (double) data.get("Radius");
 *             System.out.println("The area is: " + Radius * Radius * Math.PI);
 *         }
 *         // get single data
 *         var geo = Data.Geometries.get("a");
 *         // Check the type to makesure you get the right type
 *         if (!(geo instanceof GeoCircle)) System.out.println("This is not a circle");
 *         else {
 *             var Radius = ((GeoCircle) geo).GetR();
 *             System.out.println("The area is: " + Radius * Radius * Math.PI);
 *         }
 *         // make new geometry and create a group
 *         Clevis.RunOperation(new OpSquare("b", 1, 1, 1));
 *         Clevis.RunOperation(new OpGroup("c", new String[]{"a", "b"}));
 *         // see the original graph
 *         res = Clevis.RunQuery(new OpListall());
 *         System.out.println(res.GetDescription());
 *         // move the group
 *         Clevis.RunOperation(new OpMove("c", 1, 1));
 *         res = Clevis.RunQuery(new OpList("c"));
 *         System.out.println(res.GetDescription());
 *         // ungroup them
 *         Clevis.RunOperation(new OpUngroup("c"));
 *         res = Clevis.RunQuery(new OpList("a"));
 *         System.out.println(res.GetDescription());
 *         // undo ungrouping moving and creating group
 *         Clevis.RunQuery(new OpUndo());
 *         Clevis.RunQuery(new OpUndo());
 *         Clevis.RunQuery(new OpUndo());
 *         res = Clevis.RunQuery(new OpListall());
 *         System.out.println(res.GetDescription());
 *         // redo grouping
 *         Clevis.RunQuery(new OpRedo());
 *         res = Clevis.RunQuery(new OpList("c"));
 *         System.out.println(res.GetDescription());
 *         // quit
 *         Clevis.RunQuery(new OpQuit());
 *     }
 * </code></pre>
 */
public class Clevis {

    private static Logger Log = null;
    private static final double EPS_DEFULT = 0.05;
    private static final int HIST_DEFULT = 100;
    private static double Eps = EPS_DEFULT;
    private static int HistoryLength = HIST_DEFULT;
    private static String HtmlDirectory;
    private static String TxtDirectory;
    private static boolean rerun = false;

    private static final HashMap<String, Constructor<?>> commandConstructors = new HashMap<>();
    private static final HashMap<String, Class<?>[]> commandParameters = new HashMap<>();

    /**
     * Initialize the Clevis
     *
     * @param args boosting options
     */
    public static void Boost(String[] args) {
        if (args == null || args.length == 0) return;
        int index = 0;
        if (args[0].compareTo("rerun") == 0) {
            rerun = true;
            index++;
        }
        try {
            while (index < args.length) {
                switch (args[index]) {
                    case "-html":
                        if (index + 1 < args.length)
                            HtmlDirectory = args[index + 1];
                        else
                            throw new InputMismatchException("Missing argument after -html");
                        break;
                    case "-txt":
                        if (index + 1 < args.length)
                            TxtDirectory = args[index + 1];
                        else
                            throw new InputMismatchException("Missing argument after -txt");
                        break;
                    case "-eps":
                        if (index + 1 < args.length)
                            Eps = Double.parseDouble(args[index + 1]);
                        else
                            throw new InputMismatchException("Missing argument after -eps");
                        break;
                    case "-hist":
                        if (index + 1 < args.length)
                            HistoryLength = Integer.parseInt(args[index + 1]);
                        else
                            throw new InputMismatchException("Missing argument after -hist");
                        break;
                    default:
                        throw new InputMismatchException("Unknown argument: " + args[index]);
                }
                index += 2;
            }
            Queue<String> commands = new LinkedList<>();
            if (rerun) {
                var scanner = (new Scanner(new File(TxtDirectory)));
                while (scanner.hasNext()) {
                    var command = scanner.nextLine();
                    System.out.println(command);
                    commands.add(command);
                    Execute(command);
                }
            }
            Log = new Logger(new FileWriter(HtmlDirectory), new FileWriter(TxtDirectory));
            if (rerun) {
                while (!commands.isEmpty()) {
                    Log.Log(commands.remove());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (InputMismatchException e) {
            System.out.print("""
                    Correct format are as follows:
                    -html your\\target\\address\\log.html
                    -txt your\\target\\address\\log.txt
                    -eps <double> the persition
                    -hist <int> the history length
                    """);
            System.exit(1);
        }
    }

    /**
     * @param command the command inputed by user
     * @return the execution result.
     */
    public static String Execute(String command) {
        var parameter = new Scanner(command);
        if (command.compareTo("") == 0) return "Empty command";
        try {
            Constructor<?> constructor;
            Class<?>[] paramTypes;
            var operation = parameter.next().toLowerCase();
            if (commandConstructors.containsKey(operation)) {
                constructor = commandConstructors.get(operation);
                paramTypes = commandParameters.get(operation);
            } else {
                var op = operation.substring(0, 1).toUpperCase() + operation.substring(1);
                Class<?> commandClass = null;
                try {
                    commandClass = Class.forName("hk.edu.polyu.comp.comp2021.clevis.model.Operation.Op" + op);
                } catch (ClassNotFoundException ignored) {
                }
                if (commandClass == null)
                    commandClass = Class.forName("hk.edu.polyu.comp.comp2021.clevis.model.Query.Op" + op);
                constructor = commandClass.getConstructors()[0];
                paramTypes = constructor.getParameterTypes();
                commandConstructors.put(operation, constructor);
                commandParameters.put(operation, paramTypes);
            }
            Object[] params = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                try {
                    if (i == paramTypes.length - 1)
                        params[i] = Convert(parameter.nextLine().substring(1), paramTypes[i]);
                    else params[i] = Convert(parameter.next(), paramTypes[i]);
                } catch (Exception ignored) {
                    var message = new StringBuilder("Parameters are not matched. Correct format is:\n");
                    message.append(operation);
                    for (Class<?> paramType : paramTypes) {
                        var type = paramType.getSimpleName();
                        message.append(" <").append(type).append(">");
                    }
                    throw new ClassCastException(message.toString());
                }
            }
            var cmd = constructor.newInstance(params);
            if (Operation.class.isAssignableFrom(cmd.getClass())) {
                Clevis.RunOperation((Operation) cmd);
                return "Succeed";
            } else if (Query.class.isAssignableFrom(cmd.getClass())) {
                return Clevis.RunQuery((Query) cmd).GetDescription();
            }
        } catch (ClassNotFoundException e) {
            return "Command does not exist. enter \"help\" for help.";
        } catch (InvocationTargetException e) {
            return e.getTargetException().getMessage();
        } catch (ClassCastException | IllegalAccessException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Unknown Error: " + e.getMessage();
        }
        return null;
    }
    /**
     * Execute the operation command
     *
     * @param op the operation needs to be executed
     */
    public static void RunOperation(Operation op) {
        op.Execute();
        Data.RedoStack.clear();
        Data.UndoStack.add(op);
        if (Data.UndoStack.size() > GetHistoryLength())
            Data.UndoStack.removeFirst();
        if (Log != null)
            Log.Log(op.GetCommand());
    }

    /**
     * Execute the query command
     *
     * @param qr the query needs to be executed
     * @return the result of the query
     * @throws IllegalAccessException thrown when query is illegal
     */
    public static Query.QureyResult RunQuery(Query qr) throws IllegalAccessException, KeyException {
        var res = qr.Execute();
        if (Log != null)
            Log.Log(qr.GetCommand());
        return res;
    }

    /**
     * The longest reserved history length
     *
     * @return the history length
     */
    public static int GetHistoryLength() {
        return HistoryLength;
    }

    /**
     * compare 2 double value with epsilon persition
     *
     * @param a value a
     * @param b value b
     * @return -1 if a &lt; b. 0 if a ~ b. 1 if a &gt; b.
     */
    public static int Compare(double a, double b) {
        if (a < b - Eps) return -1;
        if (a > b + Eps) return 1;
        return 0;
    }
    /**
     * Converts a string to target type.
     *
     * @param value      the value in string format
     * @param targetType the target value type
     * @return the converted value
     */
    public static Object Convert(String value, Class<?> targetType) {
        if (targetType != double.class) {
            if (targetType != String.class) {
                if (targetType != String[].class) {
                    return null;
                } else return value.split(" ");
            } else return value;
        } else return Double.parseDouble(value);
    }
}
package hk.edu.polyu.comp.comp2021.clevis.model.Operation;

/**
 * The operation command
 */
public interface Operation {

    /**
     * Execute the operation
     */
    void Execute();

    /**
     * Recover the operation
     */
    void Recover();

    /**
     * Get the string format of the command
     *
     * @return The string format of the command
     */
    String GetCommand();
}

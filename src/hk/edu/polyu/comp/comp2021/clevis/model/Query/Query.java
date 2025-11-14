package hk.edu.polyu.comp.comp2021.clevis.model.Query;

import java.security.KeyException;

/**
 * The query command
 */
public interface Query {
    /**
     * Execute the Query
     *
     * @return the execution result
     */
    QureyResult Execute() throws KeyException, IllegalAccessException;

    /**
     * Get the command in string format
     *
     * @return the string formmant of the command
     */
    String GetCommand();

    /**
     * The structure for query result
     *
     * @param _result      the object result for the query
     * @param _description the string description for the query
     */
    record QureyResult(Object _result, String _description) {
        /**
         * Create a structure for the results
         *
         * @param _result      the object result
         * @param _description the string description to the result
         */
        public QureyResult {
        }

        /**
         * Get the object result
         *
         * @return the object result for the query
         */
        public Object GetResult() {
            return _result;
        }

        /**
         * Get the description
         *
         * @return the string description to the result
         */
        public String GetDescription() {
            return _description;
        }
    }
}


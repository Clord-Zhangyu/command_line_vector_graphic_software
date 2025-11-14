package hk.edu.polyu.comp.comp2021.clevis.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

import hk.edu.polyu.comp.comp2021.clevis.model.Operation.Operation;
import hk.edu.polyu.comp.comp2021.clevis.model.Query.Query;

/**
 * The console object
 * Take input, resolve command and execute
 * Feed back exceptions
 */

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Entry for console
     *
     * @param args initial inputs
     */
    public static void main(String[] args) {
        Clevis.Boost(args);
        //noinspection InfiniteLoopStatement
        while (true) {
            String Command = scanner.nextLine();
            var res = Clevis.Execute(Command);
            if (res != null && res.compareTo("") != 0)
                System.out.println(res);
        }
    }
}

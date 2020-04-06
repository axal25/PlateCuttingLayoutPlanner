package utils;

public class PrintSystem {
    public static void outBegin(String functionName) {
        System.out.println("\t \\/\\/\\/ " + functionName + " \\/\\/\\/");
    }

    public static void outEnd(String functionName) {
        System.out.println("\t /\\/\\/\\ " + functionName + " /\\/\\/\\");
    }

    public static void errBegin(String functionName) {
        System.err.println("\t \\/\\/\\/ " + functionName + " \\/\\/\\/");
    }

    public static void errEnd(String functionName) {
        System.err.println("\t /\\/\\/\\ " + functionName + " /\\/\\/\\");
    }
}

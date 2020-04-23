package main;

import cutter.Cutter;
import replaced.out.MockSystem;

public class Main {

    // hides System variable of type System
    // used in MainWithReplacedOut - class used for tests
    public static MockSystem System = new MockSystem(java.lang.System.out);

    public static void main(String[] args) {
        try {
            Cutter cutter = new Cutter(args);
            String output = cutter.cut();
            System.out.println(output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

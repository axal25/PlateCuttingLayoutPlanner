package labs.lab1;

import java.io.PrintStream;

public class Introduction {
    public static final String[] DISPLAY_TEXT = {
            "    public static void firstDemonstration() {\n" +
                    "        String empty_String;\n" +
                    "        empty_String = \"\";\n" +
                    "\n" +
                    "        String[] empty_String_tab = new String[0];\n" +
                    "        //empty_String_tab has allocated only memory for 0 Strings so next line will throw error\n" +
                    "        //empty_String_tab[0] = empty_String;\n" +
                    "\n" +
                    "        String[] not_empty_String_tab = new String[3];\n" +
                    "        // first cell has index 0\n" +
                    "        not_empty_String_tab[0] = empty_String;\n" +
                    "        not_empty_String_tab[1] = \"Second cell\";\n" +
                    "        not_empty_String_tab[2] = \"Third cell\";\n" +
                    "\n" +
                    "        firstDemonstration_main(not_empty_String_tab);\n" +
                    "    }\n" +
                    "\n" +
                    "    private static void firstDemonstration_main(String[] args) {\n" +
                    "        for(int i = 0; i < args.length; i++) {\n" +
                    "            System.out.println(\"\\\"\" + args[i] + \"\\\"\");\n" +
                    "        }\n" +
                    "    }",

            "    public static void secondDemonstration() {\n" +
                    "        int[] intArray = new int[6];\n" +
                    "        intArray[0] = 1;\n" +
                    "        intArray[1] = new Integer(1); // deprecated\n" +
                    "        intArray[2] = Integer.valueOf(1);\n" +
                    "        intArray[3] = 2;\n" +
                    "        intArray[4] = new Integer(2); // deprecated\n" +
                    "        intArray[5] = Integer.valueOf(2);\n" +
                    "\n" +
                    "        for (int i = 0; i < intArray.length; i++) {\n" +
                    "            System.out.println(\"intArray[\" + i + \"]: \" + intArray[i]);\n" +
                    "            intArray[i] = 3;\n" +
                    "            System.out.println(\"intArray[\" + i + \"]: \" + intArray[i]);\n" +
                    "            intArray[i] = new Integer(3);\n" +
                    "            System.out.println(\"intArray[\" + i + \"]: \" + intArray[i]);\n" +
                    "            intArray[i] = Integer.valueOf(3);\n" +
                    "            System.out.println(\"intArray[\" + i + \"]: \" + intArray[i]);\n" +
                    "            // i2 = i1.clone(); // You can't clone primitive types\n" +
                    "        }\n" +
                    "    }",

            "public class Abc {\n" +
                    "    // Hiding System variable\n" +
                    "    private static MySystem System = new MySystem(null);\n" +
                    "\n" +
                    "    public Abc(PrintStream printStream) {\n" +
                    "        System = new MySystem(printStream);\n" +
                    "    }\n" +
                    "\n" +
                    "    public Abc clone() {\n" +
                    "        System.out.println(\"abc.clone()\");\n" +
                    "        return new Abc(System.out);\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "    public static void thirdDemonstration() {\n" +
                    "        Object[] objects = new Object[3];\n" +
                    "        objects[0] = new Object();\n" +
                    "        objects[1] = null;\n" +
                    "        objects[2] = objects[0];\n" +
                    "        // o2 = o2.clone(); // Method clone is not visible from type Object\n" +
                    "        for(Object object:objects) System.out.println(\"object: \" + object);\n" +
                    "\n" +
                    "        Abc[] abcs = new Abc[5];\n" +
                    "        abcs[0] = null;\n" +
                    "        abcs[1] = new Abc(System.out);\n" +
                    "        abcs[2] = abcs[1];\n" +
                    "        abcs[3] = abcs[2].clone();\n" +
                    "        for(Abc abc:abcs) System.out.println(\"abc: \" + abc);\n" +
                    "    }",
    };

    private static MySystem System = new MySystem(null); // Hiding System variable

    public Introduction(PrintStream printStream) { System = new MySystem(printStream); }

    public static void firstDemonstration() {
        String empty_String;
        empty_String = "";

        String[] empty_String_tab = new String[0];
        //empty_String_tab has allocated only memory for 0 Strings so next line will throw error
        //empty_String_tab[0] = empty_String;

        String[] not_empty_String_tab = new String[3];
        // first cell has index 0
        not_empty_String_tab[0] = empty_String;
        not_empty_String_tab[1] = "Second cell";
        not_empty_String_tab[2] = "Third cell";

        firstDemonstration_main(not_empty_String_tab);
    }

    private static void firstDemonstration_main(String[] args) {
        for(int i = 0; i < args.length; i++) {
            System.out.println("\"" + args[i] + "\"");
        }
    }

    public static void secondDemonstration() {
        int[] intArray = new int[6];
        intArray[0] = 1;
        intArray[1] = new Integer(1); // deprecated
        intArray[2] = Integer.valueOf(1);
        intArray[3] = 2;
        intArray[4] = new Integer(2); // deprecated
        intArray[5] = Integer.valueOf(2);

        for (int i = 0; i < intArray.length; i++) {
            System.out.println("intArray[" + i + "]: " + intArray[i]);
            intArray[i] = 3;
            System.out.println("intArray[" + i + "]: " + intArray[i]);
            intArray[i] = new Integer(3);
            System.out.println("intArray[" + i + "]: " + intArray[i]);
            intArray[i] = Integer.valueOf(3);
            System.out.println("intArray[" + i + "]: " + intArray[i]);
            // i2 = i1.clone(); // You can't clone primitive types
        }
    }

    public static void thirdDemonstration() {
        Object[] objects = new Object[3];
        objects[0] = new Object();
        objects[1] = null;
        objects[2] = objects[0];
        // o2 = o2.clone(); // Method clone is not visible from type Object
        for(Object object:objects) System.out.println("object: " + object);

        Abc[] abcs = new Abc[5];
        abcs[0] = null;
        abcs[1] = new Abc(System.out);
        abcs[2] = abcs[1];
        abcs[3] = abcs[2].clone();
        for(Abc abc:abcs) System.out.println("abc: " + abc);
    }
}

package recruitment.mayeryn.exercise3.part2;

public class Part2 {
    public static void main() {
        final String functionName = "main()";
        final String location = Part2.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        printPart2ClassStructure();
        doPart2();

        utils.PrintSystem.outEnd( location );
    }

    private static void doPart2() {
        System.out.println("" +
                "\n" +
                "        C c = new C();\n" +
                "        c.printObjectType();" +
                "");
        C c = new C();
        c.printObjectType();

        System.out.println("" +
                "\n" +
                "        D d = new D();\n" +
                "        d.printObjectType();\n" +
                "        d.printImplements();" +
                "");
        D d = new D();
        d.printObjectType();
        d.printImplements();

        System.out.println("" +
                "\n" +
                "        c = new D();\n" +
                "        c.printObjectType();\n" +
                "        // c.printImplements(); // C class doesn't have method printImplements();" +
                "");
        c = new D();
        c.printObjectType();
        // c.printImplements(); // C class doesn't have method printImplements();

        System.out.println("" +
                "\n" +
                "        I i = new D();\n" +
                "        i.printObjectType();\n" +
                "        i.printImplements();" +
                "");
        I i = new D();
        i.printObjectType();
        i.printImplements();

        System.out.println("" +
                "\n" +
                "        c = new E();\n" +
                "        c.printObjectType();\n" +
                "        // c.printImplements(); // C class doesn't have method printImplements();" +
                "");
        c = new E();
        c.printObjectType();
        // c.printImplements(); // C class doesn't have method printImplements();

        System.out.println("" +
                "\n" +
                "        i = new E();\n" +
                "        i.printObjectType();\n" +
                "        i.printImplements();" +
                "");
        i = new E();
        i.printObjectType();
        i.printImplements();
    }

    private static void printPart2ClassStructure() {
        System.out.println("Class structure:" + "\n");
        printIInterfaceStructure();
        printCClassStructure();
        printDClassStructure();
        printEClassStructure();
    }

    private static void printIInterfaceStructure() {
        System.out.println("" +
                "public interface I {\n" +
                "    public void printObjectType();\n" +
                "    default void printImplements() { System.out.println(\"implements interface I\"); }\n" +
                "}" +
                "\n");
    }

    private static void printCClassStructure() {
        System.out.println("" +
                "public class C {\n" +
                "    public void printObjectType() {\n" +
                "        System.out.println(\"object of class C\");\n" +
                "    }\n" +
                "}" +
                "\n");
    }

    private static void printDClassStructure() {
        System.out.println("" +
                "public class D extends C implements I {\n" +
                "}" +
                "\n");
    }

    private static void printEClassStructure() {
        System.out.println("" +
                "public class E extends C implements I {\n" +
                "    public void printObjectType() { System.out.println(\"object of class E\"); }\n" +
                "}" +
                "\n");
    }
}

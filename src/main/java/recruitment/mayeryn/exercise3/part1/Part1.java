package recruitment.mayeryn.exercise3.part1;

public class Part1 {
    public static void main() {
        final String functionName = "main()";
        final String location = Part1.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        printPart1ClassStructure();
        doPart1();

        utils.PrintSystem.outEnd( location );
    }private static void doPart1() {
        System.out.println("" +
                "        A a = new A();\n" +
                "        a.printObjectType();" +
                "");
        A a = new A();
        a.printObjectType();

        System.out.println("" +
                "\n" +
                "        B b = new B();\n" +
                "        b.printObjectType();" +
                "");
        B b = new B();
        b.printObjectType();

        System.out.println("" +
                "\n" +
                "        a = new B();\n" +
                "        a.printObjectType();" +
                "");
        a = new B();
        a.printObjectType();
    }

    private static void printPart1ClassStructure() {
        System.out.println("Class structure:" + "\n");
        printAClassStructure();
        printBClassStructure();
    }

    private static void printAClassStructure() {
        System.out.println("" +
                "public class A {" + "\n" +
                "    public A() { System.out.println(\"Constructor of class A\"); }" + "\n" +
                "    public void printObjectType() {" + "\n" +
                "        System.out.println(\"object of class A\");" + "\n" +
                "    }" + "\n" +
                "}" +
                "\n");
    }

    private static void printBClassStructure() {
        System.out.println("" +
                "public class B extends A {" + "\n" +
                "    public B() {" + "\n" +
                "        super();" + "\n" +
                "        System.out.println(\"Constructor of class B\");" + "\n" +
                "    }" + "\n" +
                "    @Override" + "\n" +
                "    public void printObjectType() {" + "\n" +
                "        System.out.println(\"object of class B\");" + "\n" +
                "    }" + "\n" +
                "}" +
                "\n");
    }
}

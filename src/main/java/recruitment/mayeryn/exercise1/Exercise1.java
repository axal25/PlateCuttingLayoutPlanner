package recruitment.mayeryn.exercise1;

import java.util.ArrayDeque;
import java.util.Deque;

public class Exercise1 {
    public static void main() {
        final String functionName = "exercise1()";
        final String location = Exercise1.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        Deque<B> stack = new ArrayDeque<>();
        B b = new B();
        stack.add( b );
        A a = new A();
        // stack.add((B) a); // exception
        C c = new C();
        stack.add( c );
        D d = new D();
        stack.add( d );

        System.out.println("Collection:" + "\n");
        System.out.println("" +
                "        Deque<B> stack = new ArrayDeque<>(); " + "\n" +
                "        B b = new B(); " + "\n" +
                "        stack.add( b ); " + "\n" +
                "        A a = new A(); " + "\n" +
                "        // stack.add((B) a); // exception " + "\n" +
                "        C c = new C(); " + "\n" +
                "        stack.add( c ); " + "\n" +
                "        D d = new D(); " + "\n" +
                "        stack.add( d ); " + "\n" +
                "");

        System.out.println("Class hierarchy:" + "\n");
        System.out.println("public class A {}" + "\n");
        System.out.println("public class B extends A {}" + "\n");
        System.out.println("public class C extends B {}" + "\n");
        System.out.println("public class D extends C {}" + "\n");

        utils.PrintSystem.outEnd( location );
    }
}

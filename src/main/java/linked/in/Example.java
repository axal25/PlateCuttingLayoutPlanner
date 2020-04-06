package linked.in;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Example {
    public static void main() {
        Example.one();
        Example.two();
        Example.three();
        Example.four();
        Example.five();
        Example.six();
        Example.seven();
        Example.eight();
    }

    public static void one() {
        System.out.println("Hello World");
        try {
            for (int i = 0; i == 2; i = i++) System.out.println("i");
        } catch(ArithmeticException e) { // not the other way around
            System.out.println();
        } catch(Exception e) {
            System.out.println();
        } finally {
            System.out.println("!");
        }
    }

    public static void two() {
        String s = "strawberries";
        System.out.println(s.substring(2,5));
    }

    public static void three() {
        System.out.println("apple".compareTo("banana"));
    }

    public static void four() {
        int i;
        for (i=0; i < 1; i++) { // nie bylo =0

        }
    }

    public static void five() {
//        int a = 1231312321312321321312; // za dlugie
        List a = new ArrayList();
        a.add("string");
        a.add(2);
        System.out.println();
    }

    public static void six() {
        System.out.println("schwift".getClass().getSimpleName() == "String");
        // nie ma .getType()


//        System.out.println("s".substring(5,0)); // out of bounds
    }

    public static void seven() {
        try {
            ExceptionMethod();
        } catch(Error e) { // nie moze byc Exception
            System.out.println("ERROR caught");
        }
    }

    public static void eight() {
        int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 2;
        for (int cell : array) {
            System.out.println("cell: " + cell);
        }

        new ArrayList<>(Arrays.asList(array)).forEach((node) -> {
            System.out.println("node: " + node);
        });

        new ArrayList<>(Arrays.asList(array)).forEach(System.out::println);

        Collection<Integer> input = Arrays.asList(10, 20, 30, 40, 50);

        Collector<Object, ?, List<Object>> hex2 = Collectors.toList();
        Collection<String> hex = input.stream()
                .map(Integer::toHexString)
                .collect(Collectors.toList());
    }

    public static void ExceptionMethod() {
        throw new Error();
    }
}

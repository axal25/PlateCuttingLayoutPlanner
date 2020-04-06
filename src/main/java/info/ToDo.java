package info;

public class ToDo {
    public static final String[] whatIsLeftToDo = {
            "4. ogarnac (zaczete)",
            "5. ogarnac (cale)",
            "6. solid - olać",
            "7. grafika - olać",
            "8. wątki - ogarnąć",
            "9. aplikacje sieciowe - ogarnac (zaczete)",
            "10. SQL/BD - ogarnac (zaczete)",
            "11. calc - nie obowiazuje",
            "12. refleksja - olać",
            "13. more Java8 functionallities",
            "14. design pattern - visitor" +
                    "\n\r\t 14.1. https://www.nurkiewicz.com/2009/03/wzorzec-visitor-realny-przykad.html?fbclid=IwAR3OerX5bsjnswO0oXf-bm1DkpBlrF-Nb8SA-j1K7xM9pjFyq6sY5g2P4gI" +
                    "\n\r\t 14.2. https://refactoring.guru/design-patterns/visitor-double-dispatch" +
                    "\n\r\t 14.3. https://stackoverflow.com/questions/6762256/how-does-double-dispatch-work-in-visitor-pattern?fbclid=IwAR1Wg1EMfP1FmcMcshHdczTHO-6QV2SakTjGWB-78AidDQ96WBln1IJ0L-4",
            "15. Java String Recruitment Questions" +
                    "\n\r\t 15.1. https://www.java67.com/2014/08/difference-between-string-literal-and-new-String-object-Java.html?fbclid=IwAR2SzRMG4wYDeoPoruQUBBU6cOdt-XVFhhFsNSVlZ27IEFjeg1uEO500LNw",
    };

    public static String toFinalString() {
        String builtString = null;
        for ( String entry : whatIsLeftToDo ) {
            if((builtString == null) || builtString.isEmpty()) {
                builtString = "{ ";
            }
            else {
                builtString += ", ";
            }
            builtString += "\"" + entry + "\"";
        }
        builtString += " }";
        return builtString;
    }

    public static void printWhatIsLefttoDo() {
        System.out.println( toFinalString() );
    }
}

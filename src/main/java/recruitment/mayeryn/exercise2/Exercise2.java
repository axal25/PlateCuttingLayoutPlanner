package recruitment.mayeryn.exercise2;

public class Exercise2 {
    public static void main() {
        final String functionName = "exercise2()";
        final String location = Exercise2.class.getName() + " >>> " + functionName;
        utils.PrintSystem.outBegin( location );

        SingletonDbConnection db = SingletonDbConnection.getInstance();
        System.out.println("Singleton use:" + "\n");
        System.out.println("SingletonDbConnection db = SingletonDbConnection.getInstance();" + "\n");

        System.out.println("Singleton implementation:" + "\n");
        System.out.println("" +
                "public class SingletonDbConnection {" + "\n" +
                "    private static SingletonDbConnection singletonDbConnection;" + "\n" +
                "\n" +
                "    private SingletonDbConnection() {}" + "\n" +
                "\n" +
                "    public static SingletonDbConnection getInstance() {" + "\n" +
                "        if( singletonDbConnection == null ) singletonDbConnection = new SingletonDbConnection();" + "\n" +
                "        return singletonDbConnection;" + "\n" +
                "    }" + "\n" +
                "}" +
                "\n");

        utils.PrintSystem.outEnd( location );
    }
}

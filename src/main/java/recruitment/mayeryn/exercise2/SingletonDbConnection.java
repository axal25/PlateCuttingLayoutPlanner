package recruitment.mayeryn.exercise2;

public class SingletonDbConnection {
    private static SingletonDbConnection singletonDbConnection;

    private SingletonDbConnection() {}

    public static SingletonDbConnection getInstance() {
        if( singletonDbConnection == null ) singletonDbConnection = new SingletonDbConnection();
        return singletonDbConnection;
    }
}

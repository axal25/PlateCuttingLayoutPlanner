package recruitment.mayeryn.exercise3.part2;

public interface I {
    public void printObjectType();
    default void printImplements() { System.out.println("implements interface I"); }
}

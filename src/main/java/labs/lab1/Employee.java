package labs.lab1;

public class Employee
{

    public String name;
    public String position;
    public String department;
    public int salary;

    Employee()
    {
        name ="default_name";
        position = "default_position";
        department = "default_department";
        salary = 0;
    }

    Employee(String _name, String _position, String _department, int _salary)
    {
        name = _name;
        position = _position;
        department = _department;
        salary = _salary;
    }

    public static void sort_tab(Employee[] tab)
    {
        Employee temp;
        for(int i = tab.length-1; i > 0; i--)
        {
            if( tab[i].salary > tab[i-1].salary )
            {
                temp = tab[i-1];
                tab[i-1] = tab[i];
                tab[i] = temp;
            }
        }
    }

    public static void print_tab(Employee[] tab)
    {
        String formated;
        formated = String.format("tab[  X] | Lp.  | %30s | %30s | %15s | %8s |", "Name", "Position Title", "Department", "Salary");
        System.out.println(formated);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for(int i = 0; i < tab.length; i++)
        {
            formated = String.format("tab[%3d] | %3d. | %30s | %30s | %15s | $ %d |", i, i, tab[i].name, tab[i].position, tab[i].department, tab[i].salary);
            System.out.println(formated);
        }
    }

}
import java.util.ArrayList;

public class Employee implements Comparable<Employee>{
    private String name;
    private int annualLeaves;

    public Employee(String n, int y){
        name = n;
        annualLeaves = y;
    }
    public String getName(){ return name;}
    public int getAnnualLeaves(){return annualLeaves;}
    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch){
        for(Employee emp: list){
            if(emp.getName().equals(nameToSearch)){
                return emp;
            }
        }
        return null;
    }
    @Override
    public int compareTo(Employee another)  {
        return this.name.compareTo(another.name);
    }
    public static void list(ArrayList<Employee> allEmployees){
        for(Employee emp: allEmployees){
            System.out.printf("%s (Entitled Annual Leaves: %d days)\n", emp.getName(), emp.getAnnualLeaves());
        }
    }
}
 
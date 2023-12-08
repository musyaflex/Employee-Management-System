import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee>{
    private String name;
    private int annualLeaves;
    private ArrayList<DatesPair> leaves;
    private Team team;

    public Employee(String n, int y) {
        name = n;
        annualLeaves = y;
        leaves = new ArrayList<>();
        team = null;
    }

    @Override
    public int compareTo(Employee another)  {
        return this.name.compareTo(another.name);
    }
    
    // Get employee name
    public String getName() { return name;}

    // Get the team name, in which employee is
    public Team getTeam() {return team;}

    // check if employee is in team
    public boolean isInTeam() {
        if (team == null) return true;
        return false;
    }

    // Get the annual leave amount of employee
    public int getAnnualLeaves() {return annualLeaves;}
    
    // // Calculate the number of annual leaves left for the current year
    public int getAnnualLeavesLeft() {
        int leavesLeft = annualLeaves;
        SystemDate systemDate = SystemDate.getInstance();
        int year = systemDate.getYear();
        Day firstDayOfCurrentYear = new Day(year, 1, 1);
        Day lastDayOfCurrentYear = new Day(year, 12, 31);

        for (DatesPair leave: leaves) {
            if (leave.getEnd().getYear() == year) {
                if (leave.getStart().compareTo(firstDayOfCurrentYear) < 0) {
                    leavesLeft -= Day.getPeriod(firstDayOfCurrentYear, leave.getEnd());
                }
                else{
                    leavesLeft -= Day.getPeriod(leave.getStart(), leave.getEnd());
                }
            }
            else if (leave.getStart().getYear() == year) {
                leavesLeft -= Day.getPeriod(leave.getStart(), lastDayOfCurrentYear);
            }
        }
        return leavesLeft;
    }

    // Get a string of all leaves in current year 
    public String getListOfLeaves() {
        String res = "";
        Day systemDate = SystemDate.getInstance();
        int year = systemDate.getYear();
        int numOfleavesThisYear = 0;
        for (DatesPair leave: leaves) {
            if (leave.getEnd().getYear() == year || leave.getStart().getYear() == year) {
                if ((leave.getEnd()).compareTo(systemDate) >= 0) {
                    numOfleavesThisYear++;
                }
            }
        }
        if (numOfleavesThisYear == 0) {
            res = res + "--";
        }
        else{
            for (DatesPair leave: leaves) {
                if (leave.getEnd().getYear() == year || leave.getStart().getYear() == year) {
                    if ((leave.getEnd()).compareTo(systemDate) >= 0) {
                        res += " " + leave.getStart() + " to " + leave.getEnd() + ",";
                    }
                }
            }
            res = res.substring(1, res.length() - 1);
        }
        return res;
    }

    // Search for an employee in the provided list by name
    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (Employee emp: list) {
            if (emp.getName().equals(nameToSearch)) {
                return emp;
            }
        }
        return null;
    }

    // Print a list of all employees with their entitled annual leaves
    public static void list(ArrayList<Employee> allEmployees) {
        for (Employee emp: allEmployees) {
            System.out.printf("%s (Entitled Annual Leaves: %d days)\n", emp.getName(), emp.getAnnualLeaves());
        }
    }
    
    // Take leave for the specified start and end dates
    public void takeLeave(Day startDate, Day endDate) throws ExInsufficientLeavesLeft, ExLeavePeriodOverlap, ExProjectInFinalStage{
        int leavePeriod = Day.getPeriod(startDate, endDate);
        DatesPair newLeave = new DatesPair(startDate, endDate);
        int leavesLeft = getAnnualLeavesLeft();

        // Check if the leave conflicts with a project period in team
        if (team != null && team.getConflictingProject(newLeave) != null) {
            throw new ExProjectInFinalStage(team.getConflictingProject(newLeave));
        }
        // Check if there are sufficient leaves left
        if (leavePeriod > leavesLeft) {
            throw new ExInsufficientLeavesLeft(leavesLeft);
        }
        // Check if the new leave overlaps with any existing leaves
        for (DatesPair leave: leaves) {
            if (Day.overlap(leave, newLeave)) {
                throw new ExLeavePeriodOverlap(leave.getStart(), leave.getEnd());
            }
        }
        leaves.add(new DatesPair(startDate, endDate));
        Collections.sort(leaves);
    }

    // Delete a leave for the specified start and end dates
    public void deleteLeave(Day startDate, Day endDate) {
        for (DatesPair leave: leaves) {
            if (leave.getStart() == startDate && leave.getEnd() == endDate) {
                leaves.remove(leave);
                break;
            }
        }
    }

    // Print the list of leaves for the employee in the current year
    public void listLeaves() {
        SystemDate systemDate = SystemDate.getInstance();
        int year = systemDate.getYear();
        int numOfleavesThisYear = 0;
        for (DatesPair leave: leaves) {
            if (leave.getEnd().getYear() == year || leave.getStart().getYear() == year) {
                if ((leave.getEnd()).compareTo(systemDate) >= 0) {
                    numOfleavesThisYear++;
                }
            }
        }
        if (numOfleavesThisYear == 0) {
            System.out.println(this.getName() + ": --");
        }
        else{
            System.out.print(this.getName() + ": ");
            int count = 1;
            for (DatesPair leave: leaves) {
                if (leave.getEnd().getYear() == year || leave.getStart().getYear() == year) {
                    if ((leave.getEnd()).compareTo(systemDate) >= 0) {
                        if (count == numOfleavesThisYear) {
                            System.out.printf("%s to %s\n", leave.getStart(), leave.getEnd());
                            break;
                        }
                        System.out.printf("%s to %s, ", leave.getStart(), leave.getEnd());
                        count++;
                    }
                }
            }
        }
    }

    // Set a team for employee
    public void setTeam(Team t) {
        team = t;
    }

    // Unset a team for employee
    public void unsetTeam() {
        team = null;
    }

    // Calculate the average manpower for an employee in the team
    public double calculateMP(Project p) {
        DatesPair period = p.getFullPeriod();
        double periodLen = Day.getPeriod(period.getStart(), period.getEnd());
        for (DatesPair leave: leaves) {
            if (Day.overlap(leave, period)) {
                periodLen -= Day.getOverlapPeriod(leave, period);
            }
        }
        double res = periodLen/Day.getPeriod(period.getStart(), period.getEnd());
        return res;
    }

}
 
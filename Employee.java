import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee>{
    private String name;
    private int annualLeaves;
    private ArrayList<DatesPair> leaves;
    private Team team;

    public Employee(String n, int y){
        name = n;
        annualLeaves = y;
        leaves = new ArrayList<>();
        team = null;
    }
    public String getName(){ return name;}
    public Team getTeam(){return team;}
    public boolean isInTeam(){
        if (team == null) return true;
        return false;
    }

    public int getAnnualLeaves(){return annualLeaves;}
    public int getAnnualLeavesLeft(){
        int leavesLeft = annualLeaves;
        SystemDate systemDate = SystemDate.getInstance();
        int year = systemDate.getYear();
        Day firstDayOfCurrentYear = new Day(year, 1, 1);
        Day lastDayOfCurrentYear = new Day(year, 12, 31);

        for(DatesPair pair: leaves){
            if (pair.getEnd().getYear() == year){
                if (pair.getStart().compareTo(firstDayOfCurrentYear) < 0){
                    leavesLeft -= Day.getPeriod(firstDayOfCurrentYear, pair.getEnd());
                }
                else{
                    leavesLeft -= Day.getPeriod(pair.getStart(), pair.getEnd());
                }
            }
            else if (pair.getStart().getYear() == year){
                leavesLeft -= Day.getPeriod(pair.getStart(), lastDayOfCurrentYear);
            }
        }

        // for(DatesPair pair: leaves){
        //     if (year == pair.getEnd().getYear()){
        //         if (pair.getStart().compareTo(firstDayOfCurrentYear) < 0){
        //             leavesLeft -= Day.getPeriod(firstDayOfCurrentYear, pair.getEnd());
        //         }
        //         else{
        //             leavesLeft -= Day.getPeriod(pair.getStart(), pair.getEnd());
        //         }
        //     }
        // }
        return leavesLeft;
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch){
        for(Employee emp: list){
            if (emp.getName().equals(nameToSearch)){
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
    
    public void takeLeave(Day startDate, Day endDate) throws ExInsufficientLeavesLeft, ExLeavePeriodOverlap, ExProjectInFinalStage{
        int leavePeriod = Day.getPeriod(startDate, endDate);
        DatesPair newLeave = new DatesPair(startDate, endDate);
        int leavesLeft = getAnnualLeavesLeft();

        if (team != null && team.getConflictingProject(newLeave) != null){
            throw new ExProjectInFinalStage(team.getConflictingProject(newLeave));
        }
        if (leavePeriod > leavesLeft){
            throw new ExInsufficientLeavesLeft(leavesLeft);
        }
        for(DatesPair pair: leaves){
            if (Day.overlap(pair, newLeave)){
                throw new ExLeavePeriodOverlap(pair.getStart(), pair.getEnd());
            }
        }
        leaves.add(new DatesPair(startDate, endDate));
        Collections.sort(leaves);
    }

    public void deleteLeave(Day startDate, Day endDate) {
        for(DatesPair pair: leaves){
            if (pair.getStart() == startDate && pair.getEnd() == endDate){
                leaves.remove(pair);
                break;
            }
        }
    }
    public void listLeaves(){
        SystemDate systemDate = SystemDate.getInstance();
        int year = systemDate.getYear();
        int numOfleavesThisYear = 0;
        for(DatesPair pair: leaves){
            if (pair.getEnd().getYear() == year || pair.getStart().getYear() == year){
                if ((pair.getEnd()).compareTo(systemDate) >= 0){
                    numOfleavesThisYear++;
                }
            }
        }
        if (numOfleavesThisYear == 0){
            System.out.println(this.getName() + ": --");
        }
        else{
            System.out.print(this.getName() + ": ");
            int count = 1;
            for(DatesPair pair: leaves){
                if (pair.getEnd().getYear() == year || pair.getStart().getYear() == year){
                    if ((pair.getEnd()).compareTo(systemDate) >= 0){
                        if (count == numOfleavesThisYear){
                            System.out.printf("%s to %s\n", pair.getStart(), pair.getEnd());
                            break;
                        }
                        System.out.printf("%s to %s, ", pair.getStart(), pair.getEnd());
                        count++;
                    }
                }
            }
            // Day lastStartDate = leaves.get(leaves.size()-1).getStart();
            // Day lastEndDate = leaves.get(leaves.size()-1).getEnd(); 
            // System.out.printf("%s to %s\n", lastStartDate, lastEndDate);
        }
    }
    
    public String getListOfLeaves(){
        String res = "";
        Day systemDate = SystemDate.getInstance();
        int year = systemDate.getYear();
        int numOfleavesThisYear = 0;
        for(DatesPair pair: leaves){
            if (pair.getEnd().getYear() == year || pair.getStart().getYear() == year){
                if ((pair.getEnd()).compareTo(systemDate) >= 0){
                    numOfleavesThisYear++;
                }
            }
        }
        if (numOfleavesThisYear == 0){
            res = res + "--";
        }
        else{
            for(DatesPair pair: leaves){
                if (pair.getEnd().getYear() == year || pair.getStart().getYear() == year){
                    if ((pair.getEnd()).compareTo(systemDate) >= 0){
                        res += " " + pair.getStart() + " to " + pair.getEnd() + ",";
                    }
                }
            }
            res = res.substring(1, res.length() - 1);
        }
        return res;
    }

    public void setTeam(Team t){
        team = t;
    }

    public void unsetTeam(){
        team = null;
    }

    public double calculateMP(Project p){
        DatesPair period = p.getFullPeriod();
        double periodLen = Day.getPeriod(period.getStart(), period.getEnd());
        for(DatesPair leave: leaves){
            if (Day.overlap(leave, period)){
                periodLen -= Day.getOverlapPeriod(leave, period);
            }
        }
        double res = periodLen/Day.getPeriod(period.getStart(), period.getEnd());
        return res;
    }

}
 
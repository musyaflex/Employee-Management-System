import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team> {
    private String teamName; 
    private Employee head;
    private Day dateSetup;
    private ArrayList<Employee> members;
    private ArrayList<Project> projects;

    // Team constructor
    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        this.dateSetup = SystemDate.getInstance().clone(); // Set the setup date to the current system date
        members = new ArrayList<Employee>();
        projects = new ArrayList<Project>();
    }

    // Method to list all teams
    public static void list(ArrayList<Team> list) {
        System.out.printf("%-15s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team t : list)
            System.out.printf("%-15s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup);
    }

    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName); // Compare teams based on their names
    }

    // Getter for team name
    public String getName() {
        return teamName;
    }

    // Getter for team leader
    public Employee getHead() {
        return head;
    }

    // Get a conflicting project based on leave dates
    public Project getConflictingProject(DatesPair leave) {
        for (Project p : projects) {
            DatesPair finalStage = p.getFinalStage();
            if (Day.overlap(finalStage, leave)) {
                return p;
            }
        }
        return null;
    }

    // Check if an employee is a member of the team
    public boolean isMember(Employee e) {
        String name = e.getName();
        if (name.equals(head.getName())) {
            return true;
        } else {
            for (Employee member : members) {
                if (member.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Search for a team in a list of teams
    public static Team searchTeam(ArrayList<Team> teams, String teamName) {
        for (Team t : teams) {
            if (t.getName().equals(teamName)) {
                return t;
            }
        }
        return null; // Return null if team is not found
    }

    // Add a member to the team
    public void addMember(Employee e) {
        members.add(e);
        Collections.sort(members); 
    }

    // Remove a member from the team
    public void removeMember(Employee e) {
        members.remove(e);
    }

    // List all members of the team
    public void listMembers() {
        System.out.printf("%-10s%-10s%-13s\n", "Role", "Name", "Current / coming leaves");
        System.out.printf("%-10s%-10s%-13s\n", "Leader", head.getName(), head.getListOfLeaves());
        for (Employee e : members) {
            System.out.printf("%-10s%-10s%-13s\n", "Member", e.getName(), e.getListOfLeaves());
        }
    }

    // Add a project to the team
    public void addProject(Project p) {
        projects.add(p);
    }

    // Remove a project from the team
    public void removeProject(Project p) {
        projects.remove(p);
    }

    // Get a string representation of all members
    public String getMembersStr() {
        String str = head.getName();
        for (Employee e : members) {
            str = str + ", " + e.getName();
        }
        return str;
    }

    // Calculate the average manpower for a project in the team
    public double calculateAverageManpower(Project p) {
        double res = head.calculateMP(p);
        for (Employee member : members) {
            res += member.calculateMP(p);
        }
        return res;
    }

    // Calculate the average project count for a project in the team
    public double calculateAverageProjCount(Project p) {
        double sum = 0;
        DatesPair period = p.getFullPeriod();
        double periodLen = Day.getPeriod(period.getStart(), period.getEnd());
        for (Project project : projects) {
            if (Day.overlap(project.getFullPeriod(), period)) {
                sum += Day.getOverlapPeriod(project.getFullPeriod(), period);
            }
        }
        return sum / periodLen;
    }

    // Calculate the predicted LF (Load Factor) for a project in the team
    public double predictedLF(Project p) {
        return (1 + calculateAverageProjCount(p)) / calculateAverageManpower(p);
    }
}
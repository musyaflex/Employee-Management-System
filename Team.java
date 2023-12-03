import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team>{
    private String teamName;
	private Employee head;
	private Day dateSetup;
    private ArrayList<Employee> members;
    private ArrayList<Project> projects;

    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        this.dateSetup = SystemDate.getInstance().clone();
        members = new ArrayList<Employee>();
        projects = new ArrayList<Project>();
    }

    public static void list(ArrayList<Team> list) {
        //Learn: "-" means left-aligned
        System.out.printf("%-15s%-10s%-13s\n", "Team Name", "Leader", "Setup Date" );
        for (Team t : list)
            System.out.printf( "%-15s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup); //display t.teamName, etc..
    }
    @Override
    public int compareTo(Team another)  {
        return this.teamName.compareTo(another.teamName);
    }
    public String getName(){
        return teamName;
    }
    public Employee getHead(){
        return head;
    }
    public boolean isMember(Employee e){
        String name = e.getName();
        if(name.equals(head.getName()))
            return true;
        else{
            for(Employee member: members){
                if(member.getName().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }
    // public static Team searchTeam(ArrayList<Team> teams, String teamName) throws ExTeamNotFound{
    public static Team searchTeam(ArrayList<Team> teams, String teamName){
        for(Team t: teams){
            if(t.getName().equals(teamName)){
                return t;
            }
        }
        return null;
    }
    

    public void addMember(Employee e){
        members.add(e);
        Collections.sort(members);
    }

    public void removeMember (Employee e){
        members.remove(e);
    }

    public void listMembers(){
        System.out.printf("%-10s%-10s%-13s\n", "Role", "Name", "Current / coming leaves" );
        System.out.printf("%-10s%-10s%-13s\n", "Leader", head.getName(), head.getListOfLeaves() );
        for(Employee e: members){
            System.out.printf("%-10s%-10s%-13s\n", "Member", e.getName(), e.getListOfLeaves());
        }
    }

    public void addProject(Project p){
        projects.add(p);
    }

    public void removeProject(Project p) {
        projects.remove(p);
    }

    public String getMembersStr(){
        String str = head.getName();
        for(Employee e: members){
            str = str + ", " + e.getName();
        }
        return str;
    }
}

import java.util.ArrayList;
import java.util.Collections;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;
    private static Company instance = new Company();    

    private Company(){
        allEmployees = new ArrayList<Employee>();
        allTeams = new ArrayList<Team>();
        allProjects = new ArrayList<Project>();
    }
    public static Company getInstance(){
        return instance;
    }
    public void listTeams() {
        Team.list(allTeams);
    }
    public void listEmployees(){
        Employee.list(allEmployees);
    }
    public Employee searchEmployee(String name){
        return Employee.searchEmployee(allEmployees, name);
    }
    public Team searchTeam (String teamName){
        return Team.searchTeam(allTeams, teamName);
    }
    public Employee createEmployee(String name, int l) // See how it is called in main()
    {
        Employee e = new Employee(name, l);
        allEmployees.add(e);
        Collections.sort(allEmployees); //allEmployees
        return e;
    }
    public void addEmployee(Employee e){
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }
    public void removeEmployee(Employee e){
        allEmployees.remove(e);
        Collections.sort(allEmployees);
    }
    public void addTeam(Team t){
        allTeams.add(t);
        Collections.sort(allTeams);
    }
    public void removeTeam(Team t){
        allTeams.remove(t);
        Collections.sort(allTeams);
    }
    public Team createTeam(String g, String name) // See how it is called in main()
    {
        Employee e = Employee.searchEmployee(allEmployees, name);
        Team t = new Team(g, e);
        allTeams.add(t);
        Collections.sort(allTeams); //allTeams
        return t; //Why return?  Ans: Later you'll find it useful for undoable comments.
    }

    public void joinTeam(Team t, Employee e){
        t.addMember(e);
    }

    public void removeMemberFromTeam(Employee e, Team t){
        t.removeMember(e);
    }

    public void listTeamMembers(Team t){
        t.listMembers();
    }

    public void addProject(Project p){
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    public void removeProject(Project p){
        allProjects.remove(p);
        Collections.sort(allProjects);
    }
    
    public Project searchProject (String projectCode){
        return Project.searchProject(allProjects, projectCode);
    }

    public void assignProject(Team t, Project p){
        t.addProject(p);
        p.setTeam(t);
    }

    public void unassignProject(Team t, Project p){
        t.removeProject(p);
        p.unsetTeam(t);
    }

    public void listProjects(){
        Project.list(allProjects);
    }
}

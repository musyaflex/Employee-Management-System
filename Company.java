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
    public Employee searchEmployee(String name) throws ExEmployeeNotFound{
        Employee e = Employee.searchEmployee(allEmployees, name);
        if (e != null) {
            return e;
        }
        throw new ExEmployeeNotFound();
        
    }
    public Team searchTeam (String teamName) throws ExTeamNotFound {
        Team t = Team.searchTeam(allTeams, teamName);
        if (t != null){
            return t;
        }
        throw new ExTeamNotFound();
    }
    // public Employee createEmployee(String name, int l) // See how it is called in main()
    // {
    //     Employee e = new Employee(name, l);
    //     allEmployees.add(e);
    //     Collections.sort(allEmployees); //allEmployees
    //     return e;
    // }
    public void addEmployee(Employee e) throws ExEmployeeAlreadyExists{
        for(Employee emp: allEmployees){
            if (emp.getName().equals(e.getName())){
                throw new ExEmployeeAlreadyExists();
            }
        }
        allEmployees.add(e);
        Collections.sort(allEmployees);
        
    }
    public void removeEmployee(Employee e){
        allEmployees.remove(e);
        Collections.sort(allEmployees);
    }
    public void addTeam(Team t) throws ExTeamAlreadyExists, ExEmployeeAlreadyInTeam {
        for(Team team: allTeams){
            if (team.getName().equals(t.getName())){
                throw new ExTeamAlreadyExists();
            }
            if (team.isMember(t.getHead())){
                throw new ExEmployeeAlreadyInTeam(team, t.getHead());
            }
        }
        allTeams.add(t);
        t.getHead().setTeam(t);
        Collections.sort(allTeams);
    }
    public void removeTeam(Team t){
        t.getHead().unsetTeam();
        allTeams.remove(t);
        Collections.sort(allTeams);
    }
    // public Team createTeam(String g, String name) // See how it is called in main()
    // {
    //     Employee e = Employee.searchEmployee(allEmployees, name);
    //     Team t = new Team(g, e);
    //     allTeams.add(t);
    //     Collections.sort(allTeams); //allTeams
    //     return t; //Why return?  Ans: Later you'll find it useful for undoable comments.
    // }

    public void joinTeam(Team t, Employee e) throws ExEmployeeAlreadyInTeam {
        for(Team team: allTeams){
            if (team.isMember(e)){
                throw new ExEmployeeAlreadyInTeam(team, e);
            }
        }
        e.setTeam(t);
        t.addMember(e);
    }

    public void removeMemberFromTeam(Employee e, Team t){
        t.removeMember(e);
        e.unsetTeam();
    }

    public void listTeamMembers(Team t){
        t.listMembers();
    }

    public void addProject(Project p) throws ExProjectAlreadyExists{
        for(Project project: allProjects){
            if (project.getCode().equals(p.getCode())){
                throw new ExProjectAlreadyExists();
            }
        }
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    public void removeProject(Project p){
        allProjects.remove(p);
        Collections.sort(allProjects);
    }
    
    public Project searchProject (String projectCode) throws ExProjectNotFound{
        Project p = Project.searchProject(allProjects, projectCode);
        if (p != null) {
            return p;
        }
        throw new ExProjectNotFound();
    }

    public void assignProject(Team t, Project p) throws ExProjectAlreadyAssigned {
        if (p.getTeam() != null){
            throw new ExProjectAlreadyAssigned(p.getTeam());
        }
        else{
            t.addProject(p);
            p.setTeam(t);
        }
    }

    public void unassignProject(Team t, Project p){
        t.removeProject(p);
        p.unsetTeam();
    }

    public void listProjects(){
        Project.list(allProjects);
    }

    public void listLeaves(){
        for(Employee e: allEmployees){
            e.listLeaves();
        }
    }

    public void suggestTeam(Project p){
        System.out.printf("During the period of project %s (%s to %s):\n", p.getCode(), p.getStartDay(), p.getEndDay());
        System.out.print("  Average manpower (m) and count of existing projects (p) of each team:\n");
        for(Team team: allTeams){
            System.out.printf("    %s: m=%.2f workers, p=%.2f projects\n", team.getName(), team.calculateAverageManpower(p), team.calculateAverageProjCount(p));
        }
        System.out.printf("  Projected loading factor when a team takes this project %s:\n", p.getCode());
        double minLf = Double.POSITIVE_INFINITY;
        Team minLfTeam = null;
        for(Team team: allTeams){
            double lf = team.predictedLF(p);
            if (lf < minLf){
                minLf = lf;
                minLfTeam = team;
            }
            System.out.printf("    %s: (p+1)/m = %.2f\n", team.getName(), lf);
        }
        System.out.printf("Conclusion: %s should be assigned to %s for best balancing of loading\n", p.getCode(), minLfTeam.getName());
    }
}

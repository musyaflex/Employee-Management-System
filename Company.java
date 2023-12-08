import java.util.ArrayList;
import java.util.Collections;

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;
    private ArrayList<Project> allProjects;
    private static Company instance = new Company();    

    private Company() {
        allEmployees = new ArrayList<Employee>();
        allTeams = new ArrayList<Team>();
        allProjects = new ArrayList<Project>();
    }
    
    // Returns the instance of the Company class
    public static Company getInstance() {
        return instance;
    }
    
    // Lists all the teams in the company
    public void listTeams() {
        Team.list(allTeams);
    }
    
    // Lists all the employees in the company
    public void listEmployees() {
        Employee.list(allEmployees);
    }
    
    // Searches for an employee by name and returns the Employee object if found
    // Throws an ExEmployeeNotFound exception if the employee is not found
    public Employee searchEmployee(String name) throws ExEmployeeNotFound{
        Employee e = Employee.searchEmployee(allEmployees, name);
        if (e != null) {
            return e;
        }
        throw new ExEmployeeNotFound();
    }
    
    // Searches for a team by name and returns the Team object if found
    // Throws an ExTeamNotFound exception if the team is not found
    public Team searchTeam (String teamName) throws ExTeamNotFound {
        Team t = Team.searchTeam(allTeams, teamName);
        if (t != null) {
            return t;
        }
        throw new ExTeamNotFound();
    }
    
    // Adds an employee to the company
    // Throws an ExEmployeeAlreadyExists exception if an employee with the same name already exists
    public void addEmployee(Employee e) throws ExEmployeeAlreadyExists{
        for (Employee emp: allEmployees) {
            if (emp.getName().equals(e.getName())) {
                throw new ExEmployeeAlreadyExists();
            }
        }
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }
    
    // Removes an employee from the company
    public void removeEmployee(Employee e) {
        allEmployees.remove(e);
        Collections.sort(allEmployees);
    }
    
    // Adds a team to the company
    // Throws an ExTeamAlreadyExists exception if a team with the same name already exists
    // Throws an ExEmployeeAlreadyInTeam exception if the team's head is already a member of another team
    public void addTeam(Team t) throws ExTeamAlreadyExists, ExEmployeeAlreadyInTeam {
        for (Team team: allTeams) {
            if (team.getName().equals(t.getName())) {
                throw new ExTeamAlreadyExists();
            }
            if (team.isMember(t.getHead())) {
                throw new ExEmployeeAlreadyInTeam(team, t.getHead());
            }
        }
        allTeams.add(t);
        t.getHead().setTeam(t);
        Collections.sort(allTeams);
    }
    
    // Removes a team from the company
    public void removeTeam(Team t) {
        t.getHead().unsetTeam();
        allTeams.remove(t);
        Collections.sort(allTeams);
    }

    // Assigns an employee to a team
    // Throws an ExEmployeeAlreadyInTeam exception if the employee is already a member of another team
    public void joinTeam(Team t, Employee e) throws ExEmployeeAlreadyInTeam {
        for (Team team: allTeams) {
            if (team.isMember(e)) {
                throw new ExEmployeeAlreadyInTeam(team, e);
            }
        }
        e.setTeam(t);
        t.addMember(e);
    }

    // Removes a member from a team
    public void removeMemberFromTeam(Employee e, Team t) {
        t.removeMember(e);
        e.unsetTeam();
    }

    // Lists all the members of a team
    public void listTeamMembers(Team t) {
        t.listMembers();
    }

    // Adds a project to the company
    // Throws an ExProjectAlreadyExists exception if a project with the same code already exists
    public void addProject(Project p) throws ExProjectAlreadyExists {
        for (Project project: allProjects) {
            if (project.getCode().equals(p.getCode())) {
                throw new ExProjectAlreadyExists();
            }
        }
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    // Removes a project from the company
    public void removeProject(Project p) {
        allProjects.remove(p);
        Collections.sort(allProjects);
    }
    
    // Searches for a project by code and returns the Project object if found
    // Throws an ExProjectNotFound exception if the project is not found
    public Project searchProject (String projectCode) throws ExProjectNotFound {
        Project p = Project.searchProject(allProjects, projectCode);
        if (p != null) {
            return p;
        }
        throw new ExProjectNotFound();
    }

    // Assigns a project to a team
    //Throws an ExProjectAlreadyAssigned exception if the project is already assigned to a team
    public void assignProject(Team t, Project p) throws ExProjectAlreadyAssigned {
        if (p.getTeam() != null) {
            throw new ExProjectAlreadyAssigned(p.getTeam());
        }
        else{
            t.addProject(p);
            p.setTeam(t);
        }
    }

    // Unassigns a project from a team
    public void unassignProject(Team t, Project p) {
        t.removeProject(p);
        p.unsetTeam();
    }

    // Lists all the projects in the company
    public void listProjects() {
        Project.list(allProjects);
    }

    // Lists all the leaves taken by employees in the company
    public void listLeaves() {
        for (Employee e: allEmployees) {
            e.listLeaves();
        }
    }

    // Suggests a team for a project based on balancing the loading factor
    public void suggestTeam(Project p) {
        System.out.printf("During the period of project %s (%s to %s):\n", p.getCode(), p.getStartDay(), p.getEndDay());
        System.out.print("  Average manpower (m) and count of existing projects (p) of each team:\n");
        for (Team team: allTeams) {
            System.out.printf("    %s: m=%.2f workers, p=%.2f projects\n", team.getName(), team.calculateAverageManpower(p), team.calculateAverageProjCount(p));
        }
        System.out.printf("  Projected loading factor when a team takes this project %s:\n", p.getCode());
        double minLf = Double.POSITIVE_INFINITY;
        Team minLfTeam = null;
        for (Team team: allTeams) {
            double lf = team.predictedLF(p);
            if (lf < minLf) {
                minLf = lf;
                minLfTeam = team;
            }
            System.out.printf("    %s: (p+1)/m = %.2f\n", team.getName(), lf);
        }
        System.out.printf("Conclusion: %s should be assigned to %s for best balancing of loading\n", p.getCode(), minLfTeam.getName());
    }
}
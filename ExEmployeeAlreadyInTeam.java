public class ExEmployeeAlreadyInTeam extends Exception{
    public ExEmployeeAlreadyInTeam() {super("Project already assigned!");}
    public ExEmployeeAlreadyInTeam(String m) {super(m);}
    public ExEmployeeAlreadyInTeam(Team t, Employee e) {
        super(e.getName() + " has already joined another team: " + t.getName());
    } 
}

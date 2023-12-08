public class ExProjectAlreadyAssigned extends Exception{
    private String teamName;
    public String getTeamName() {return teamName;}
    public ExProjectAlreadyAssigned() {super("Project already assigned!");}
    public ExProjectAlreadyAssigned(String m) {super(m);}
    public ExProjectAlreadyAssigned(Team t) {
        super("This project is already assigned to the team: ");
        teamName = t.getName();
    }
}

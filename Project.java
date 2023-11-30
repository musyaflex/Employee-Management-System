import java.util.ArrayList;

public class Project implements Comparable<Project>{
    private String code;
    private Day startDay;
    private Day endDay;
    private Team team;

    public Project(String code, Day sDay, int period){
        this.code = code;
        startDay = sDay;
        endDay = Day.calculateDay(sDay, period);
        team = null;
    }
    @Override
    public int compareTo(Project another)  {
        return this.code.compareTo(another.code);
    }
    public static Project searchProject(ArrayList<Project> projects, String projectCode){
        for(Project p: projects){
            if(p.getCode().equals(projectCode)){
                return p;
            }
        }
        return null;
    }
    
    public String getCode(){
        return code;
    }
    public String getStartDay(){
        return startDay.toString();
    }
    public String getEndDay(){
        return endDay.toString();
    }
    public String getTeamInfo(){
        return team.getName() + " (" + team.getMembersStr() + ")";
    }
    public Team getTeam(){
        return team;
    }
    public static void list(ArrayList<Project> projects){
        System.out.printf("%-9s%-13s%-13s%-13s\n", "Project", "Start Day", "End Day", "Team");
        for (Project project: projects){
            String teamInfo = "--";
            if(project.getTeam() != null){
                teamInfo = project.getTeamInfo();
            }
            System.out.printf( "%-9s%-13s%-13s%-13s\n", project.getCode(), project.getStartDay(), project.getEndDay(), teamInfo); 
        }
    }
    
    public void setTeam(Team t){
        team = t;
    }
    public void unsetTeam(){
        team = null;
    }
}

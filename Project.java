import java.util.ArrayList;

public class Project implements Comparable<Project> {
    private String code;
    private Day startDay;
    private Day endDay;
    private Team team;

    public Project(String code, Day sDay, int period) {
        this.code = code;
        startDay = sDay;
        endDay = Day.calculateDay(sDay, period);
        team = null;
    }

    @Override
    public int compareTo(Project another) {
        return this.code.compareTo(another.code);
    }

    // Search for a project in the given list based on the project code
    public static Project searchProject(ArrayList<Project> projects, String projectCode) {
        for (Project p : projects) {
            if (p.getCode().equals(projectCode)) {
                return p;
            }
        }
        return null;
    }

    // Get the project code
    public String getCode() {
        return code;
    }

    // Get the start day of the project as string
    public String getStartDay() {
        return startDay.toString();
    }

    // Get the end day of the project as string
    public String getEndDay() {
        return endDay.toString();
    }

    // Get the team information associated with the project
    public String getTeamInfo() {
        return team.getName() + " (" + team.getMembersStr() + ")";
    }

    // Get the full period of the project (start day to end day) as DatesPair
    public DatesPair getFullPeriod() {
        return new DatesPair(startDay, endDay);
    }

    // Get the team associated with the project
    public Team getTeam() {
        return team;
    }

    // Get the final stage of the project (based on end day and a 5-day reverse calculation)
    public DatesPair getFinalStage() {
        Day endD = endDay.clone();
        Day finalStageStartDay = Day.calculateDayReverse(endD, 5);
        if (startDay.compareTo(finalStageStartDay) > 0) {
            finalStageStartDay = startDay;
        }
        return new DatesPair(finalStageStartDay, endD);
    }

    // Print a list of projects with their details
    public static void list(ArrayList<Project> projects) {
        System.out.printf("%-9s%-13s%-13s%-13s\n", "Project", "Start Day", "End Day", "Team");
        for (Project project : projects) {
            String teamInfo = "--";
            if (project.getTeam() != null) {
                teamInfo = project.getTeamInfo();
            }
            System.out.printf("%-9s%-13s%-13s%-13s\n", project.getCode(), project.getStartDay(), project.getEndDay(),
                    teamInfo);
        }
    }

    // Set the team associated with the project
    public void setTeam(Team t) {
        team = t;
    }

    // Unset the team associated with the project
    public void unsetTeam() {
        team = null;
    }
}
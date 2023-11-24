public class ListTeamMembers extends RecordedCommand {
    
    @Override
	public void execute(String[] cmdParts)
	{
        String teamName = cmdParts[1];
        Company company = Company.getInstance();
        Team t = company.searchTeam(teamName);
        company.listTeamMembers(t);

	}
    @Override
	public void undoMe()
	{

	}
	
	@Override
	public void redoMe()
	{
	}
}

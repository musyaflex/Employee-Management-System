public class CmdListTeamMembers extends RecordedCommand {
    
    @Override
	public void execute(String[] cmdParts)
	{   try{
            if (cmdParts.length<2)
                throw new ExInsufficientArguments();
            String teamName = cmdParts[1];
            Company company = Company.getInstance();
            Team t = company.searchTeam(teamName);
            company.listTeamMembers(t);
        } catch(ExTeamNotFound e) {
            System.out.println(e.getMessage());
        } catch(ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        }
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

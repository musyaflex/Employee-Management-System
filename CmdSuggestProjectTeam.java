public class CmdSuggestProjectTeam extends RecordedCommand{
    @Override
	public void execute(String[] cmdParts)
	{
        try{
            if (cmdParts.length<2)
				throw new ExInsufficientArguments();
            
            Company company = Company.getInstance();
            Project p = company.searchProject(cmdParts[1]);
            company.suggestTeam(p);
        } catch(ExProjectNotFound e) {
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

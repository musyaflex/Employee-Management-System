public class SetupTeam extends RecordedCommand {
    Team t;
    @Override
	public void execute(String[] cmdParts)
	{
        // String teamName = cmdParts[1];
        // String teamLeader = cmdParts[2];
        Company company = Company.getInstance();
        Employee e = company.searchEmployee(cmdParts[2]);
        t = new Team(cmdParts[1], e);
        company.addTeam(t);
        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
	}
    @Override
	public void undoMe()
	{
        Company company = Company.getInstance();
        company.removeTeam(t);
        addRedoCommand(this);
	}
	
	@Override
	public void redoMe()
	{
        Company company = Company.getInstance();
        company.addTeam(t);
        addUndoCommand(this);
	}
}

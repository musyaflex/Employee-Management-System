public class CreateProject extends RecordedCommand{
    Project project;
    @Override
	public void execute(String[] cmdParts)
	{
        String code = cmdParts[1];
        Day startDay = new Day(cmdParts[2]);
        int period = Integer.parseInt(cmdParts[3]);
        project = new Project(code, startDay, period);
        Company company = Company.getInstance();
        company.addProject(project);
        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
	}
    @Override
	public void undoMe()
	{
        Company company = Company.getInstance();
        company.removeProject(project);
        addRedoCommand(this);
	}
	
	@Override
	public void redoMe()
	{
        Company company = Company.getInstance();
        company.addProject(project);
        addUndoCommand(this);
	}
}

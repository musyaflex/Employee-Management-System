public class CmdAssign extends RecordedCommand{
    private Project project;
    private Team t;

    @Override
	public void execute(String[] cmdParts)
	{   try{
            if(cmdParts.length<3)
                throw new ExInsufficientArguments();
            String projectCode = cmdParts[1];
            String teamName = cmdParts[2];
            Company company = Company.getInstance();
            project = company.searchProject(projectCode);
            Team t = company.searchTeam(teamName);
            company.assignProject(t, project);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch(NumberFormatException e){
			System.out.println("Wrong number format.");
		} catch(ExTeamNotFound e){
			System.out.println(e.getMessage());
		} catch(ExInsufficientArguments e){
			System.out.println(e.getMessage());
		}
        
	}
    @Override
	public void undoMe()
	{
		Company company = Company.getInstance();
        company.unassignProject(t, project);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		Company company = Company.getInstance();
        company.assignProject(t, project);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}

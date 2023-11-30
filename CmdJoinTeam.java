public class CmdJoinTeam extends RecordedCommand{
    private Employee e;
    private Team t;

    @Override
	public void execute(String[] cmdParts)
	{   try{
            if(cmdParts.length<3)
                throw new ExInsufficientArguments();
            String name = cmdParts[1];
            String teamName = cmdParts[2];
            Company company = Company.getInstance();
            Employee e = company.searchEmployee(name);
            Team t = company.searchTeam(teamName);
            company.joinTeam(t, e);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch(ExEmployeeNotFound e){
			System.out.println(e.getMessage());
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
        company.removeMemberFromTeam(e, t);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		Company company = Company.getInstance();
        company.joinTeam(t, e);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}


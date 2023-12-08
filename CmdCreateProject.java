public class CmdCreateProject extends RecordedCommand{
    private Project project;
    @Override
	public void execute(String[] cmdParts)
	{   try{
            if (cmdParts.length<4)
                throw new ExInsufficientArguments();
            String code = cmdParts[1];
            Day startDay = new Day(cmdParts[2]);
            int period = Integer.parseInt(cmdParts[3]);
            project = new Project(code, startDay, period);
            Company company = Company.getInstance();
            company.addProject(project);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch(NumberFormatException e){
			System.out.println("Wrong number format for project duration!");
		} catch(ExProjectAlreadyExists e){
            System.out.println(e.getMessage());
        } catch(ExInsufficientArguments e){
            System.out.println(e.getMessage());
        }
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
	{   try{
            Company company = Company.getInstance();
            company.addProject(project);
            addUndoCommand(this);
        } catch(ExProjectAlreadyExists e){
            System.out.println(e.getMessage());
        }
        
	}
}

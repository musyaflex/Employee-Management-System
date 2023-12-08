public class CmdHire extends RecordedCommand{
    private Employee e;

    @Override
	public void execute(String[] cmdParts)
	{ 	try{
			if (cmdParts.length<3)
				throw new ExInsufficientArguments();
			String name = cmdParts[1];
			int leaves = Integer.parseInt(cmdParts[2]);
			e = new Employee(name, leaves);
			Company company = Company.getInstance();
			company.addEmployee(e);
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		} catch(NumberFormatException e) {
			System.out.println("Wrong number format for annual leaves!");
		} catch(ExEmployeeAlreadyExists e) {
			System.out.println(e.getMessage());
		} catch(ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		}
        
	}
    @Override
	public void undoMe()
	{
		Company company = Company.getInstance();
        company.removeEmployee(e);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{	try{
		Company company = Company.getInstance();
        company.addEmployee(e);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
		} catch(ExEmployeeAlreadyExists e) {
			System.out.println(e.getMessage());
		}
	}
}

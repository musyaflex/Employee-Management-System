public class CmdListLeaves extends RecordedCommand{
    @Override
	public void execute(String[] cmdParts)
	{
        Company company = Company.getInstance();
        if (cmdParts.length == 1) {
            company.listLeaves();
        }
        else{
            try{    
                String name = cmdParts[1];
                Employee e = company.searchEmployee(name);
                e.listLeaves();
            } catch(ExEmployeeNotFound e) {
                System.out.println(e.getMessage());
            }
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

public class CmdSetupTeam extends RecordedCommand {
        private Team t;
        @Override
        public void execute(String[] cmdParts)
        {       try{
                        if(cmdParts.length<3)
                                throw new ExInsufficientArguments();
                        Company company = Company.getInstance();
                        Employee e = company.searchEmployee(cmdParts[2]);
                        t = new Team(cmdParts[1], e);
                        company.addTeam(t);
                        addUndoCommand(this);
                        clearRedoList();
                System.out.println("Done.");
                } catch(ExTeamAlreadyExists e){
                        System.out.println(e.getMessage());
                } catch(ExEmployeeNotFound e){
                        System.out.println(e.getMessage());
                } catch(ExEmployeeAlreadyInTeam e){
                        System.out.println(e.getMessage());
                } catch(ExInsufficientArguments e){
                        System.out.println(e.getMessage());
                }      
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
	{       try{
                        Company company = Company.getInstance();
                        company.addTeam(t);
                        addUndoCommand(this);
                } catch(ExTeamAlreadyExists e){
                        System.out.println(e.getMessage());
                } catch(ExEmployeeAlreadyInTeam e){
                        System.out.println(e.getMessage());
                } 
                
	}
}

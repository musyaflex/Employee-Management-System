public class CmdTakeLeave extends RecordedCommand {
    private Employee e;
    private Day startDate;
    private Day endDate;

    @Override
    public void execute(String[] cmdParts)
    {       try{
                if(cmdParts.length<4)
                    throw new ExInsufficientArguments();
                String name = cmdParts[1];
                startDate = new Day(cmdParts[2]);
                endDate = new Day(cmdParts[3]);

                Company company = Company.getInstance();
                e = company.searchEmployee(name);
                e.takeLeave(startDate, endDate);
                addUndoCommand(this);
                clearRedoList();
                System.out.printf("Done.  %s's remaining annual leave: %d days\n", e.getName(), e.getAnnualLeavesLeft());
            } catch(ExEmployeeNotFound e){
                System.out.println(e.getMessage());
            } catch(ExInsufficientLeavesLeft e){
                System.out.println(e.getMessage());
            } catch(ExProjectInFinalStage e){
                System.out.println(e.getMessage());
            } catch(ExLeavePeriodOverlap e){
                System.out.println(e.getMessage());
            } catch(ExInsufficientArguments e){
                System.out.println(e.getMessage());
            }      
    }
        
    @Override
	public void undoMe()
	{
        e.deleteLeave(startDate, endDate);
        addRedoCommand(this);
	}
	
	@Override
	public void redoMe()
	{       
        try{
            e.takeLeave(startDate, endDate);
            addUndoCommand(this);
        } catch(ExInsufficientLeavesLeft e){
            System.out.println(e.getMessage());
        } catch(ExProjectInFinalStage e){
            System.out.println(e.getMessage());
        } catch(ExLeavePeriodOverlap e){
            System.out.println(e.getMessage());
        }   
	}
}

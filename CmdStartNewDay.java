public class CmdStartNewDay extends RecordedCommand{
    SystemDate datePrev;
    SystemDate date;
    @Override
	public void execute(String[] cmdParts)
	{
                try{
                        if(cmdParts.length<2)
                                throw new ExInsufficientArguments();
                        datePrev = SystemDate.getInstance();
                        SystemDate.setInstance(cmdParts[1]);
                        date = SystemDate.getInstance();
                        addUndoCommand(this);
                        clearRedoList();
                        System.out.println("Done.");
                } catch(ExInsufficientArguments e){
                        System.out.println(e.getMessage());
                }
	}
    @Override
	public void undoMe()
	{
        SystemDate.setInstance(datePrev);
        addRedoCommand(this);
	}
	
	@Override
	public void redoMe()
	{
        SystemDate.setInstance(date);
        addUndoCommand(this);
	}
}

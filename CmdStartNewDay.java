public class CmdStartNewDay extends RecordedCommand{
    SystemDate datePrev;
    SystemDate date;
    @Override
	public void execute(String[] cmdParts)
	{
        datePrev = SystemDate.getInstance();
        SystemDate.setInstance(cmdParts[1]);
        date = SystemDate.getInstance();
        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
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

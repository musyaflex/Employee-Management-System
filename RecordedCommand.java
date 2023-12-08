import java.util.ArrayList;

public abstract class RecordedCommand implements Command {
    public static ArrayList<RecordedCommand> undoList = new ArrayList<>();
    public static ArrayList<RecordedCommand> redoList = new ArrayList<>();

    // Add a command to the undo list
    protected static void addUndoCommand(RecordedCommand cmd) {
        undoList.add(cmd);
    }

    // Add a command to the redo list
    protected static void addRedoCommand(RecordedCommand cmd) {
        redoList.add(cmd);
    }

    // Clear the redo list
    protected static void clearRedoList() {
        redoList.clear();
    }

    // Undo the most recent command
    public static void undoOneCommand() {
        if (undoList.size() > 0) {
            RecordedCommand r = undoList.remove(undoList.size() - 1);
            r.undoMe();
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    // Redo the most recent undone command
    public static void redoOneCommand() {
        if (redoList.size() > 0) {
            RecordedCommand r = redoList.remove(redoList.size() - 1);
            r.redoMe();
        } else {
            System.out.println("Nothing to redo.");
        }
    }

    // Abstract method to undo the command
    public abstract void undoMe();

    // Abstract method to redo the command
    public abstract void redoMe();
}
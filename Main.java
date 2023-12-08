import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(System.in);

		// Prompt user for the file pathname
		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = null;
		try {
			// Read the input file
			inFile = new Scanner(new File(filepathname));
			
			// Read the first line of the file to create the SystemDate instance
			String cmdLine1 = inFile.nextLine();
			String[] cmdLine1Parts = cmdLine1.split("\\|");
			System.out.println("\n> " + cmdLine1);
			SystemDate.createTheInstance(cmdLine1Parts[1]);
			
			// Process each line of the file
			while (inFile.hasNext()) {
				String cmdLine = inFile.nextLine().trim();
				
				// Blank lines exist in data file as separators. Skip them.
				if (cmdLine.equals("")) continue;  

				System.out.println("\n> " + cmdLine);
				
				// Splitting the words in cmdLine => create an array of word strings
				String[] cmdParts = cmdLine.split("\\|"); 
				
				// Execute the appropriate command based on the first word
				if (cmdParts[0].equals("hire"))
					(new CmdHire()).execute(cmdParts);
				else if (cmdParts[0].equals("listEmployees"))
					(new CmdListEmployees()).execute(cmdParts);
				else if (cmdParts[0].equals("setupTeam"))
					(new CmdSetupTeam()).execute(cmdParts);
				else if (cmdParts[0].equals("startNewDay"))
					(new CmdStartNewDay()).execute(cmdParts);
				else if (cmdParts[0].equals("listTeams"))
					(new CmdListTeams()).execute(cmdParts);
				else if (cmdParts[0].equals("joinTeam"))
					(new CmdJoinTeam()).execute(cmdParts);
				else if (cmdParts[0].equals("createProject"))
					(new CmdCreateProject()).execute(cmdParts);
				else if (cmdParts[0].equals("assign"))
					(new CmdAssign()).execute(cmdParts);
				else if (cmdParts[0].equals("listProjects"))
					(new CmdListProjects()).execute(cmdParts);
				else if (cmdParts[0].equals("listTeamMembers"))
					(new CmdListTeamMembers()).execute(cmdParts);
				else if (cmdParts[0].equals("takeLeave"))
					(new CmdTakeLeave()).execute(cmdParts);
				else if (cmdParts[0].equals("listLeaves"))
					(new CmdListLeaves()).execute(cmdParts);
				else if (cmdParts[0].equals("suggestProjectTeam"))
					(new CmdSuggestProjectTeam()).execute(cmdParts);
				else if (cmdParts[0].equals("undo"))
					RecordedCommand.undoOneCommand();
				else if (cmdParts[0].equals("redo"))
					RecordedCommand.redoOneCommand();
				else 
					throw new ExWrongCommand();
			}
		} catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (InputMismatchException e) {
            System.out.println("File content problem!");
        } catch (ExWrongCommand e) {
            System.out.println("Unknown command - ignored!");
        } finally {
            if (inFile != null) inFile.close();
            in.close();
        }
		
		inFile.close();
		in.close();
	}
}
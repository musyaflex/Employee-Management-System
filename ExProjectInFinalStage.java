public class ExProjectInFinalStage extends Exception{
    public ExProjectInFinalStage(){super("Project in final stage!");}
    public ExProjectInFinalStage(String m){super(m);}
    public ExProjectInFinalStage(Project p){
        // super("The leave is invalid.  Reason: Project " + p.getCode() + " will be in its final stage during " + p.getFinalStage().getStart() + " to " + p.getFinalStage().getEnd() +".");
        super("The leave is invalid.  Reason: Project " + p.getCode() + " will be in its final stage during " + p.getFinalStage() +".");
    }
}

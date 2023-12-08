public class ExInsufficientLeavesLeft extends Exception{
    public ExInsufficientLeavesLeft() {super("Insufficient balance of annual leave.");}
    public ExInsufficientLeavesLeft(String m) {super(m);}
    public ExInsufficientLeavesLeft(int days) {
        super("Insufficient balance of annual leave. " + days + " days left only!");
    }
}

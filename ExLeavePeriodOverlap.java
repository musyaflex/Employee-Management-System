public class ExLeavePeriodOverlap extends Exception{
    public ExLeavePeriodOverlap() {super("Insufficient balance of annual leave.");}
    public ExLeavePeriodOverlap(String m) {super(m);}
    public ExLeavePeriodOverlap(Day startDate, Day endDate) {
        super("Leave overlapped: The leave period " + startDate + " to " + endDate + " is found!");
    }
}

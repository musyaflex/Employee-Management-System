public class DatesPair implements Comparable<DatesPair>{
    Day startDate, endDate;

    public DatesPair(Day start, Day end) {
        startDate = start;
        endDate = end;
    }
    @Override
    public int compareTo(DatesPair another) {
        return this.getStart().compareTo(another.getStart());
    } 

    public Day getStart() {return startDate;}

    public Day getEnd() {return endDate;}

    public String toString() {
        return startDate + " to " + endDate;
    }
}

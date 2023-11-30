public class Day implements Cloneable, Comparable<Day>{
	
	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	public Day(String sDay){
        set(sDay);
    }
	@Override
    public int compareTo(Day another)  {
        return this.getCompareString().compareTo(another.getCompareString());
    }
    public void set(String sDay){
        String[] sDayParts = sDay.split("-");
        this.day  =  Integer.parseInt(sDayParts[0]);  //Apply  Integer.parseInt  for  sDayParts[0]; 
        this.year = Integer.parseInt(sDayParts[2]);
        this.month  =  MonthNames.indexOf(sDayParts[1])/3+1;

    }
	public void set(Day d){
		this.day = d.getDay();
		this.month = d.getMonth();
		this.year = d.getYear();
	}
	public int getYear(){return year;}
	public int getMonth(){return month;}
	public int getDay(){return day;}
    @Override
    public Day clone(){
        Day copy = null;
        try{
            copy = (Day) super.clone();
        }
        catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return copy;
    }
	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	// Return a string for the day like dd MMM yyyy
	public String toString() {
		return day+"-"+ MonthNames.substring(3*(month-1), 3*(month-1)+3) + "-" + year;
	}
	public String getCompareString(){
		return year+""+month+day;
	}
	public Day takeNextDay(){
		int year = this.getYear();
		int month = this.getMonth();
		int day = this.getDay();
		if(Day.valid(year, month, day+1)){
			return new Day(year, month, day + 1);
		}
		else if(Day.valid(year, month + 1, 1)){
			return new Day(year, month + 1, 1);
		}
		else if(Day.valid(year + 1, 1, 1)){
			return new Day(year + 1, 1, 1);
		}
		return null;
	}

	public static Day calculateDay(Day startDay, int period){
		for(int i = 1; i < period; i++){
			startDay = startDay.takeNextDay();
		}
		return startDay;
	}
}
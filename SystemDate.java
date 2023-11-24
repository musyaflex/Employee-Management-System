public class SystemDate extends Day{
    private static SystemDate instance;
    private SystemDate(String sDay){
        super(sDay);
    }
    public static SystemDate getInstance(){
        return instance;
    }
    public static void setInstance(SystemDate d){
        instance = d;
    }
    public static void setInstance(String d){
        instance = new SystemDate(d);
    }
    public static void removeDate(){
        instance = null;
    }
    public static void createTheInstance(String sDay){
        if(instance == null){
            instance = new SystemDate(sDay);
        }
        else
            System.out.println("Cannot create one more system data instance.");
    }
}
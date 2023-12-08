public class SystemDate extends Day {
    private static SystemDate instance;

    // Private constructor to prevent direct instantiation
    private SystemDate(String sDay) {
        super(sDay);
    }

    // Get the instance of SystemDate
    public static SystemDate getInstance() {
        return instance;
    }

    // Set the instance of SystemDate
    public static void setInstance(SystemDate d) {
        instance = d;
    }

    // Set the instance of SystemDate with a string parameter
    public static void setInstance(String d) {
        instance = new SystemDate(d);
    }

    // Remove the instance of SystemDate
    public static void removeDate() {
        instance = null;
    }

    // Create the instance of SystemDate
    public static void createTheInstance(String sDay) {
        if (instance == null) {
            instance = new SystemDate(sDay);
        } else {
            System.out.println("Cannot create one more system date instance.");
        }
    }
}
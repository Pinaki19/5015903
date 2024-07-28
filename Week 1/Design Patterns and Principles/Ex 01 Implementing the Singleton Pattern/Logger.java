public class Logger {
    private static volatile Logger instance;

    private Logger() {
        // private constructor to prevent instantiation
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }
    
    // Example method for logging
    public void log(String message) {
        System.out.println("Log: [ " + message+ " ]");
    }
}

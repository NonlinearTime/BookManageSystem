package Views.data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataContainer {
    private static DataContainer instance = new DataContainer();

    private DataContainer() {}

    public static DataContainer getInstance() {
        return instance;
    }

    public static ArrayList<String> profile = new ArrayList<>();

    public static ArrayList<ArrayList<String>> books = new ArrayList<>();

    public static ArrayList<ArrayList<String>> rents = new ArrayList<>();

    public static ArrayList<ArrayList<String>> fines = new ArrayList<>();

    public static Timestamp getTimeStamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static Timestamp addTimesStamp(Timestamp timestamp) {
        Date newDate = new Date(timestamp.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(newDate);
        c.add(Calendar.DAY_OF_MONTH,30);
        return new Timestamp(c.getTime().getTime());
    }
}

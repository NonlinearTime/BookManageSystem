package Views.data;

import java.util.ArrayList;

public class DataContainer {
    private static DataContainer instance = new DataContainer();

    private DataContainer() {}

    public static DataContainer getInstance() {
        return instance;
    }

    public static ArrayList<String> profile = new ArrayList<>();


}

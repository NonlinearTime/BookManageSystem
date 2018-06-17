package Views.data;

public class DataContainer {
    private static DataContainer instance = new DataContainer();

    private DataContainer() {}

    public static DataContainer getInstance() {
        return instance;
    }


}

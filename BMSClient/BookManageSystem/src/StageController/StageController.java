package StageController;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

public class StageController {
    //to store all stages
    private HashMap<String, Stage> stages = new HashMap<String, Stage>();
    private HashMap<String, ControlledStage> controllers = new HashMap<String, ControlledStage>();


    //add stage mathod
    public void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }

    //get stage mathod
    public Stage getStage(String name) {
        return stages.get(name);
    }

    //add primary Stage
    public void setPrimaryStage(String primaryStageName, Stage primaryStage) {
        this.addStage(primaryStageName, primaryStage);
    }

    //load stages
    public boolean loadStage(String name, String resources, StageStyle... styles) {
        try {
            //get FXML resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resources));
            Pane tmpPane = (Pane) loader.load();

            //get controller
            ControlledStage controlledStage = (ControlledStage) loader.getController();
            controlledStage.setStageController(this);
            controllers.put(name, controlledStage);

            //construct stage
            Scene tmpScene = new Scene(tmpPane);
            Stage tmpStage = new Stage();
            tmpStage.setScene(tmpScene);

            //set style
            for (StageStyle style: styles) {
                tmpStage.initStyle(style);
            }

            //add to hash map
            addStage(name, tmpStage);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //set sage
    public boolean setStage(String show) {
        Stage tmp = getStage(show);
        if (tmp != null) {
            tmp.show();
            return true;
        }
        else return false;
    }

    //close current stage and set next one
    public boolean setStage(String show, String close) {
        Stage tmp = getStage(close);
        if (tmp == null) return false;
        else tmp.close();
        return setStage(show);
    }

    //delete one stage in hash map
    public boolean unloadStage(String name) {
        if (stages.remove(name) == null) {
            System.out.println("stage does not exit!");
            return false;
        } else return true;
    }

    //get controller by stage name
    public ControlledStage getController(String name) {
        return controllers.get(name);
    }

    public void closeStage(String name) {
        getStage(name).close();
    }
}

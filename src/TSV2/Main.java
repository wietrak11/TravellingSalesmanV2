package TSV2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Controller ControllerHandle;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
        Parent root = loader.load();

        ControllerHandle = (Controller)loader.getController();
        ControllerHandle.setStage(primaryStage);
        ControllerHandle.setGrid();
        ControllerHandle.setMouseClickedListener();
        ControllerHandle.setMouseDraggedListener();

        primaryStage.setTitle("Travelling Salesman");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

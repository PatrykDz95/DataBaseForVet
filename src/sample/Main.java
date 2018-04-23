package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;


public class Main extends Application {

    Controller controller = new Controller();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Vet DataBase");
        primaryStage.setScene(new Scene(root, 950, 500));
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        if(!Database.getInstance().open()){
            System.out.println("FATAL ERROR: Couldn't connect to database!");
            Platform.exit();
           }
//           controller.pieChart.setData(Database.getInstance().piechartdata);
    }

    @Override
    public void stop() throws Exception {
        Database.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

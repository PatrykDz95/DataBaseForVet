package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.crypto.Data;
import java.sql.*;

import static sample.Database.*;

public class Controller {

    @FXML
    private TableView vetTable;
    @FXML
    private TableColumn animalColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn breedColumn;
    @FXML
    private TableColumn yearsColumn;
    @FXML
    private TableColumn ownerColumn;

    private Connection connection;

    @FXML
    public void listAnimals(){
        Task<ObservableList<Animals>> task = new GetAllArtistsTask();
        vetTable.itemsProperty().bind(task.valueProperty());
       // animalColumn.setCellValueFactory(Database.getInstance());
        new Thread(task).start();

    }


    class GetAllArtistsTask extends Task{
        @Override
        protected ObservableList<Animals> call() {
            return FXCollections.observableArrayList(
                    Database.getInstance().queryAnimal());

        }
    }



}

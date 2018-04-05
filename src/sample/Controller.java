package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

import static sample.Database.CONNECTION_STRING;
import static sample.Database.QUERY_VETDATABASE;

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
    public void listAnimals() {
        Task<ObservableList<Animals>> task = new GetAllArtistsTask();
        vetTable.itemsProperty().bind(task.valueProperty());
      //  animalColumn.cellFactoryProperty().bind(Database.getInstance().cos());
//        nameColumn.cellFactoryProperty().bind(task.valueProperty());
//        breedColumn.cellFactoryProperty().bind(task.valueProperty());
//        yearsColumn.cellFactoryProperty().bind(task.valueProperty());
//        ownerColumn.cellFactoryProperty().bind(task.valueProperty());
        new Thread(task).start();
//
//        animalColumn.setCellFactory( new PropertyValueFactory<Animals,String>
//                (Database.getInstance().COLUMN_ANIMAL));

    }

    class GetAllArtistsTask extends Task{
        @Override
        protected ObservableList<Animals> call() {
            return FXCollections.observableArrayList(
                    Database.getInstance().queryAnimals(Database.ORDER_BY_ASC));

        }
    }



}

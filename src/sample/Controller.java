package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

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
    @FXML
    private PieChart pieChart;

    @FXML
    public void PieChart(){
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        PieChart.Data zwierze = new PieChart.Data("pies", 50);

        list.add(zwierze);

        pieChart.setData(list);

    }

    @FXML
    public void listAnimals(){

        Task<ObservableList<Animals>> task = new GetAllArtistsTask();
        vetTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    @FXML
    public void updateAnimal(){
        final Animals animal = (Animals) vetTable.getSelectionModel().getSelectedItem();
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return Database.getInstance().updateAnimalName(animal.getId(), "COS");
            }
        };

        task.setOnSucceeded(e -> {
            if(task.valueProperty().get()){
                animal.setName("COS");
                vetTable.refresh();
            }
        });
        new Thread(task).start();
    }

    @FXML
    public void deleteAnimalRow() {
        try {
            final Animals animal = (Animals) vetTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete");
            alert.setHeaderText("Want to delete " + ((Animals) vetTable.getSelectionModel().getSelectedItem()).getName());
            alert.setContentText("Are you sure? Click OK to confirm, or cancel to Back out.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() throws Exception {
                       return Database.getInstance().deleteAnimal(animal.getName());
                    }
                };

//                task.setOnSucceeded(e -> {
//                    if(task.valueProperty().get()){
//                        vetTable.refresh();
//                    }
//                });
                new Thread(task).start();
            }
        } catch (Exception e) {
            Alert NotChosenPerson = new Alert(Alert.AlertType.ERROR);
            NotChosenPerson.setHeaderText("You must first select a person to delete");
            Optional<ButtonType> smallAlert = NotChosenPerson.showAndWait();


        }
    }

}

class GetAllArtistsTask extends Task{
    @Override
    protected ObservableList<Animals> call() {
        return FXCollections.observableArrayList(
                Database.getInstance().queryAnimal());
    }
}
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
                return Database.getInstance().updateAnimalName(animal.getId(), "AC/DC");
            }
        };

        task.setOnSucceeded(e -> {
            if(task.valueProperty().get()){
                animal.setName("AC/DC");
                vetTable.refresh();
            }
        });
        new Thread(task).start();
    }

}

class GetAllArtistsTask extends Task{
    @Override
    protected ObservableList<Animals> call() {
        return FXCollections.observableArrayList(
                Database.getInstance().queryAnimal());

    }
}
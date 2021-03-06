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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static sample.Database.ANIMAL_COUNT;

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
    public PieChart pieChart;




    @FXML
    public void PieChart(){
        Database.getInstance().countAnimals();
//        piechartdata=FXCollections.observableArrayList();
//        try(Statement statement = Database.getInstance().connection.createStatement();
//            ResultSet result = statement.executeQuery(ANIMAL_COUNT)){
//            ArrayList<Integer> cell = new ArrayList<>();
//           // ArrayList<String> animal= new ArrayList<>();
//
//            while (result.next()){
//                piechartdata.add(new PieChart.Data(result.getString(1),2));
//                cell.add(result.getInt(1));
//
//            }
//
//        }catch (SQLException e){
//            System.out.println("Update failed: " + e.getMessage());
//        }
//
    }
   // public void PieChart(){
//        TableColumn<Animals, String> column = animalColumn ; // column you want
//
//        List<String> columnData = new ArrayList<>();
//        for (String item : vetTable.getItems()) {
//            columnData.add(column.getCellObservableValue(item).getValue());
//        }
//Database.getInstance().countAnimals();
//      //  int animalCount = Integer.parseInt();
//        final Animals animal = (Animals) vetTable.getSelectionModel().getSelectedItem();
      //  ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
//
        //PieChart.Data zwierze = new PieChart.Data(animal.getAnimal(), 14);
//
//        list.add(zwierze);
//      //  Database.getInstance().queryAnimal().
      //  pieChart.setData(animalColumn);
   // }

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
                Database.getInstance().deleteAnimal(((Animals) vetTable.getSelectionModel().getSelectedItem()).getName());
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
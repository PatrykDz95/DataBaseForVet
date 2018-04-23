package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static Database instance = new Database();

    public static Database getInstance() {
        return instance;
    }

    private Database() {
    }

    public static final String DB_NAME = "VetDatabase.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
    public static final String TABLE_VETDATABASE = "vetdatabase";
    public static final String COLUMN_ANIMAL_ID = "_id";
    public static final String COLUMN_ANIMAL = "animal";
    public static final String COLUMN_ANIMAL_NAME = "name";
    public static final String COLUMN_BREED = "breed";
    public static final String COLUMN_YEARS = "years";
    public static final String COLUMN_OWNER = "owner";

    public static final int INDEX_VETDATABASE_ID = 1;
    public static final int INDEX_VETDATABASE_ANIMAL = 2;
    public static final int INDEX_VETDATABASE_NAME = 3;
    public static final int INDEX_VETDATABASE_BREED = 4;
    public static final int INDEX_VETDATABASE_YEARS = 5;
    public static final int INDEX_VETDATABASE_OWNER = 6;

    public static final int ORDER_BY_NONE=1;
    public static final int ORDER_BY_ASC = 2; // sorts the result set in ascending order by expression
    public static final int ORDER_BY_DESC = 3; // sorts the result set in descending order by expression.

    public static final String UPDATE_ANIMAL_NAME = "UPDATE " + TABLE_VETDATABASE + " SET " +
            COLUMN_ANIMAL_NAME + " = ? WHERE " + COLUMN_ANIMAL_ID + " = ?";

    public static final String QUERY_VETDATABASE = "SELECT * FROM " +
            TABLE_VETDATABASE + " ORDER BY " + TABLE_VETDATABASE + "." +
            COLUMN_ANIMAL + " COLLATE NOCASE ";

    public static final String DELETE_ANIMAL_ROW = "SELECT * FROM " + TABLE_VETDATABASE +
            " WHERE " + COLUMN_ANIMAL_NAME +" =?";

    public static final String QUERY_ANIMAL = "SELECT " + COLUMN_ANIMAL + " FROM " + TABLE_VETDATABASE;

    public static final String ANIMAL_COUNT = "SELECT COUNT(" + COLUMN_ANIMAL + ") FROM " + TABLE_VETDATABASE + " WHERE " + COLUMN_ANIMAL + " =?";
//    public int animalCount = Integer.parseInt(ANIMAL_COUNT);

    public Connection connection;
    private ObservableList<Animals> data;
    private PreparedStatement updateAnimalName;
    private PreparedStatement deleteAnimal;
    private PreparedStatement countAnimals;

    public ObservableList<PieChart.Data> piechartdata;
//    private int animalCountInt = Integer.parseInt(ANIMAL_COUNT );
//    @FXML
//    private PieChart pieChart;


    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            updateAnimalName = connection.prepareStatement(UPDATE_ANIMAL_NAME);
            deleteAnimal = connection.prepareStatement(DELETE_ANIMAL_ROW);
            countAnimals = connection.prepareStatement(ANIMAL_COUNT);

            return true;

        } catch (SQLException e) {
            System.out.println("Couldnt connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (updateAnimalName != null) {
                updateAnimalName.close();
            }
            if (deleteAnimal != null) {
                deleteAnimal.close();
            }
            if (countAnimals != null) {
                countAnimals.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
    }


    public void countAnimals(){
        piechartdata=FXCollections.observableArrayList();
        try(Statement statement = Database.getInstance().connection.createStatement();
            ResultSet result = statement.executeQuery(QUERY_VETDATABASE)){
            ArrayList<Integer> cell = new ArrayList<>();
            // ArrayList<String> animal= new ArrayList<>();

            while (result.next()){
                piechartdata.add(new PieChart.Data(result.getString(1),2));
                cell.add(result.getInt(1));

            }

        }catch (SQLException e){
            System.out.println("Update failed: " + e.getMessage());
        }

    }

    public List<Animals> queryAnimal(){

        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_VETDATABASE)){

            List<Animals> animals = new ArrayList<>();
            while(result.next()){
                Animals animal = new Animals();
                animal.setId(result.getInt(INDEX_VETDATABASE_ID));
                animal.setAnimal(result.getString(INDEX_VETDATABASE_ANIMAL));
                animal.setName(result.getString(INDEX_VETDATABASE_NAME));
                animal.setBreed(result.getString(INDEX_VETDATABASE_BREED));
                animal.setYears(result.getInt(INDEX_VETDATABASE_YEARS));
                animal.setOwner(result.getString(INDEX_VETDATABASE_OWNER));
                animals.add(animal);
            }

            return animals;

        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }

    }



    public boolean updateAnimalName(int id, String newName){
        try{
            //parameterIndex = the '=?' int the psfs UPDATE_ANIMAL_NAME
            updateAnimalName.setString(1, newName);
            updateAnimalName.setInt(2, id);

            int affectedRecords = updateAnimalName.executeUpdate();

            return affectedRecords == 1; //return if one record is selected and updated

        }catch (SQLException e){
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAnimal(String Name){
        try {
            deleteAnimal.setString(1, Name);

        }catch (SQLException e){
            System.out.println("Something went wrong: " + e.getMessage());
            return false;
        }

        return data.remove(Name);
    }


    }





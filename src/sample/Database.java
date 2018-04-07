package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.*;
import java.util.ArrayList;
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
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ANIMAL = "animal";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BREED = "breed";
    public static final String COLUMN_YEARS = "years";
    public static final String COLUMN_OWNER = "owner";

    public static final int INDEX_VETDATABASE_ID = 1;
    public static final int INDEX_VETDATABASE_ANIMAL = 2;
    public static final int INDEX_VETDATABASE_NAME = 3;
    public static final int INDEX_VETDATABASE_BREED = 4;
    public static final int INDEX_VETDATABASE_YEARS = 5;
    public static final int INDEX_VETDATABASE_OWNER = 6;

    public static final String QUERY_VETDATABASE = "SELECT * FROM " +
            TABLE_VETDATABASE + " ORDER BY " + TABLE_VETDATABASE + "." +
            COLUMN_ANIMAL + " COLLATE NOCASE ";

    public static final String QUERY_ANIMAL = "SELECT animal FROM " + TABLE_VETDATABASE;

    private Connection connection;

    public boolean open() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;

        } catch (SQLException e) {
            System.out.println("Couldnt connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
    }

    public List<Animals> queryAnimal(){

        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_VETDATABASE)){

            List<Animals> animals = new ArrayList<>();
            while(result.next()){
                Animals animal = new Animals();
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
    }





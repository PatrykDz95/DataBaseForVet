package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public static final String COLUMN_NAME= "name";
    public static final String COLUMN_BREED = "breed";
    public static final String COLUMN_YEARS = "years";
    public static final String COLUMN_OWNER = "owner";

    public static final int INDEX_VETDATABASE_ID = 1;
    public static final int INDEX_VETDATABASE_ANIMAL = 2;
    public static final int INDEX_VETDATABASE_NAME = 3;
    public static final int INDEX_VETDATABASE_BREED = 4;
    public static final int INDEX_VETDATABASE_YEARS = 5;
    public static final int INDEX_VETDATABASE_OWNER = 6;

    public static final int ORDER_BY_ASC = 1;

    public static final String QUERY_VETDATABASE = "SELECT * FROM " +
            TABLE_VETDATABASE + " ORDER BY " + TABLE_VETDATABASE + "." +
            COLUMN_ANIMAL + " COLLATE NOCASE ";

    private Connection connection;

    public boolean open(){
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;

        }catch (SQLException e){
            System.out.println("Couldnt connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close(){
        try{

            if(connection != null){
                connection.close();
            }
        }catch (SQLException e){
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
    }

}

package qca.functionality;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

public class Database {
    Connection connection;
    Statement statement;

    Boolean startConnection(){
        try{
            // neccesary for maven
            Class.forName("org.sqlite.JDBC");
            // starting the connection to the specific DB "Database"
            this.connection = DriverManager.getConnection("jdbc:sqlite:projects/Database.db");            
            this.statement = connection.createStatement();
            return true; 
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    Boolean closeConnection(){
        try{
            this.connection.close();
            this.statement = null;
            this.connection = null;
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Statement getStatement(){
        return this.statement;
    }

    public Connection getConnection(){
        return this.connection;
    }

    /**
     * This function searches the DB for the given collumn in the given table and returns the entrys 
     * @param table
     * @param collumn
     * @return entrys for the given table and collumn as String[]
     */
    public String[] getEntrys(String table, String collumn){
        String ret[];
        ArrayList<String> list = new ArrayList<>();
        try {
            this.startConnection();
            ResultSet rsYears = this.statement.executeQuery("select "+ collumn +" from "+table+";");
            
            while(rsYears.next()){
                list.add(rsYears.getString(collumn));
            }
            this.closeConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ret = new String[list.size()];
        for(int i = 0; i<ret.length;i++){
            ret[i] = list.get(i);
        }
        return ret;
    }
}

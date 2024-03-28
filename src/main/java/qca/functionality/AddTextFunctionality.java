package qca.functionality;

import java.sql.ResultSet;

public class AddTextFunctionality extends Database {
    public void addText(String documentName, String textContent){
        this.startConnection();
        try {
            ResultSet rsDocID = this.statement.executeQuery("select Id from Document where Name='"+documentName+"';");
            int DocId = rsDocID.getInt("Id");
            this.statement.executeQuery("insert into Text(Document,Content,Mood) values("+DocId+",'"+textContent+"', null);"); 
        } catch (Exception e) {}
        this.closeConnection();
    }
    
    public String[] getDocuments(){
        return getEntrys("Document", "Name");
    }
}

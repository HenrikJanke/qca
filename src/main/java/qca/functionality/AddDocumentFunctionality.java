package qca.functionality;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AddDocumentFunctionality extends Database {
    
    public void addNewDocument(String year, String institution, String documentName){
        this.startConnection();
        try{
            ResultSet rsYear = this.statement.executeQuery("select Id from Year where Date="+year+";");
            int yearId = rsYear.getInt("Id");
            ResultSet rsInstitution = this.statement.executeQuery("select Id from Institution where Name='"+institution+"';");
            int institutionId = rsInstitution.getInt("Id");
            this.statement.executeUpdate("insert into Document (Year, Institution, Name) values ("+Integer.toString(yearId)+","+Integer.toString(institutionId)+","+"'"+documentName+"'"+");");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.closeConnection();
    }
    
    public ArrayList<ArrayList<String>> getDocuments(){
        ArrayList<String> documentName = new ArrayList<>();
        ArrayList<String> institutionName = new ArrayList<>();
        ArrayList<String> yearDate = new ArrayList<>();
        ArrayList<ArrayList<String>> returner = new ArrayList<>();

        this.startConnection();
        try {
            ResultSet rsDocuments = this.statement.executeQuery("select Document.Name As 'DocumentName', Year.Date As 'Date', Institution.Name As 'InstitutionName' from Year join(Document join Institution on Document.Institution=Institution.Id)on Document.Year=Year.Id;");
            while (rsDocuments.next()){
                documentName.add(rsDocuments.getString("DocumentName"));
                institutionName.add(rsDocuments.getString("InstitutionName"));
                yearDate.add(rsDocuments.getString("Date"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        returner.add(documentName);
        returner.add(institutionName);
        returner.add(yearDate);
        this.closeConnection();
        
        return returner;
    }

    public String[] getAllInstitutions(){
        return getEntrys("Institution","Name");
    }

    public String[] getAllYears(){
        return getEntrys("Year","Date");
    }
}

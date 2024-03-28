package qca.functionality;

import java.sql.ResultSet;

public class CodeTextFunctionality extends Database{
    
    /**
     * Gets the latest text that was added
     * @return the latest text added
     */
    public String getText(){
        String ret = "";
        this.startConnection();
        try{
            ResultSet rs = this.statement.executeQuery("Select Content from Text where Text.Id not in (Select TextId from CodeText);");
            ret = rs.getString("Content");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            this.closeConnection();
        }
        return ret;
    }
    
    public int needsCoding(){
        int amount = 0;
        this.startConnection();
        try{
            ResultSet rs = this.statement.executeQuery("Select Count(Content) As 'amount' from Text where Text.Id not in (Select TextId from CodeText); ");
            amount = rs.getInt("amount");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally{
            this.closeConnection();
        }
        return amount;
    }
    public void doCode(String code, String mood, String text){
        this.startConnection();
        try {
            // Inserting Coded Text to CodeText
            statement.executeUpdate("insert into CodeText(CodeId,TextId) select Code.Id,Text.Id from Code,Text where Code.Name='"+code+"' and Text.Content='"+text+"';");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            this.closeConnection();
        }
    }
    public void doMood(String Mood, String Text){
        this.startConnection();
        try {
            // Inserting Coded Text to CodeText
            statement.executeUpdate("update Text set Mood =(select Id from Mood where Mood.Name='"+Mood+"') where Text.Content='"+Text+"';");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally{
            this.closeConnection();
        }
    }
}

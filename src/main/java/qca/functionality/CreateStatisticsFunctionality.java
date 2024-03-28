package qca.functionality;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CreateStatisticsFunctionality extends Database {
    
    public void createCodedTextsOutput(){
        PrintWriter out;
        File f = new File("projects/output/Text with code.txt");
        try {
            out = new PrintWriter(f);
            int[] textIds = this.allTextIds();
            for(int i=0;i<textIds.length;i++){
                printCodedText(out,textIds[i],i);
            }
            out.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        
    }
    public int[] allTextIds(){
        int[] ids;
        ArrayList<Integer> arrList = new ArrayList<>();
        this.startConnection();
        try{
            ResultSet rs = statement.executeQuery("select Id from Text;");
            while(rs.next()){
                arrList.add(rs.getInt("Id"));
            }
        }catch (Exception e){
        }finally{
            this.closeConnection();
        }
        ids = new int[arrList.size()];
        for(int i=0;i<arrList.size();i++){
            ids[i] = arrList.get(i);
        }
        return ids;
    }
    public void printCodedText(PrintWriter out, int textId, int currentNumber){
        String start = "--------------------------------------------"+Integer.toString(1+currentNumber);
        String title = "Title: ";
        String institution = "Institution: ";
        String year = "Year: ";
        String mood = "Mood: ";
        String coding = "Coding: ";
        String content = "Content:\n";
        this.startConnection();
        try{
            ResultSet rs = statement.executeQuery("select Document.Name As 'doc', Institution.Name As 'inst', Year.Date As 'date' from Institution join(Year join(Document join Text on Text.Document=Document.Id)on Document.Year=Year.Id)on Document.Institution=Institution.Id where Text.Id="+textId+";");
            title = title+rs.getString("doc");
            institution = institution+rs.getString("inst");
            year = year+rs.getString("date");
            ResultSet rs2 = statement.executeQuery("select Text.Content As 'text', Mood.Name As 'mood' from Text join Mood on Text.Mood=Mood.Id where Text.Id="+textId+";");
            mood = mood+rs2.getString("mood");
            content = content+rs2.getString("text");
            ResultSet rs3 = statement.executeQuery("select Code.Name as 'code' from Text join(CodeText join Code on CodeText.CodeId=Code.Id)on Text.Id=CodeText.TextId where Text.Id="+textId+";");
            coding = coding+rs3.getString("code");
        }catch (Exception e){
        }finally{
            this.closeConnection();
        }
        out.println(start);
        out.println(title);
        out.println(institution);
        out.println(year);
        out.println(mood);
        out.println(coding);
        out.println(content);
        out.close();
    }
    public void createStatistics(){
        // Executes the python script
        ProcessBuilder processBuilder = new ProcessBuilder("python3","target/classes/qca/graphs/init.py");
        processBuilder.redirectErrorStream(true);
        try{
            Process process = processBuilder.start();
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String lines = null;
            while((lines=bufferedReader.readLine())!=null) System.out.println(lines);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        
    }
}

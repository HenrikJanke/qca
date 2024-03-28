package qca.functionality;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class AddCodingFunctionality extends Database{
    public Boolean noCode(){
        this.startConnection();
        try {
            
            ResultSet result = this.statement.executeQuery("select count(Id) As 'amount' from Code;");
            if(result.getInt("amount")==0){
                return true;
            }else{
                return false;
            }
            
        } catch (Exception e) {}
        finally{
            this.closeConnection();
        }       
        return false;
    }
    /**
     * Inductive creation in amortisized O(n), with n = length of total letters
     */
    public void createCode(){
        this.startConnection();
        try {
            HashMap<String,Integer> codeMap = new HashMap<>();
            
            ResultSet allTexts = this.statement.executeQuery("select Content from Text;");
            
            // Parsing the different texts and adding them to the Hashmap
            while(allTexts.next()){
                String currentText = allTexts.getString("Content").toLowerCase();
                String currentWord = "";
                for(int i=0;i<currentText.length();i++){
                    char currentLetter = currentText.charAt(i);
                    if(currentLetter==' ' || currentLetter=='.' || currentLetter=='\'' || currentLetter=='"' ||currentLetter=='„' || currentLetter=='“' || currentLetter==',' || currentLetter=='?' || currentLetter=='!' || currentLetter==';' || currentLetter==':'||i==currentText.length()-1){
                        if(currentWord!=""){
                            // when the parsing of the current text has finished
                            if(currentText.length()-1==i){
                                currentWord = currentWord+currentLetter;
                            }
                            // if current word does not exist
                            if(codeMap.get(currentWord)==null){
                                codeMap.putIfAbsent(currentWord, 1);

                            }else{
                                codeMap.put(currentWord, 1+((int)codeMap.get(currentWord)));
                            }
                            currentWord = "";
                        }
                    }else{
                        currentWord = currentWord+currentLetter;
                    }
                }
            }
            // commiting the Codes and Occurences to the DB
            for(Map.Entry<String,Integer> set:codeMap.entrySet()){
                String word = set.getKey();
                int amount = set.getValue();
                this.statement.executeUpdate("insert into Code(Name,Occurences) values ('"+word+"',"+Integer.toString(amount)+");");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        finally{
            this.closeConnection();
        }
        
    }
    /**
     * Combination of two or more codes
     * @param values Text wie er geschrieben ist, Die indizes wie darauf zugegriffen wird, der rohcodename
     * @param selectIndices the indices of the codes who are to be combined
     */
    public void combine(returnThree<String[],Integer[],String[]> values, int[] selectIndices){
        String[] realCode = values.third;
        int amount = 0;
        String newName = "";
        
        for(int i=0;i<selectIndices.length;i++){
            int currentIndice = selectIndices[i];
            String currentCode = realCode[currentIndice];
            newName = newName+":"+currentCode;    
            try {
                this.startConnection();
                // Getting the amount of occurences
                ResultSet currentAmount = this.statement.executeQuery("select Occurences from Code where Name='"+currentCode+"';");
                amount += currentAmount.getInt("Occurences");
                this.statement.executeUpdate("DELETE FROM Code where Name='"+(String)currentCode+"';");
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally{
                this.closeConnection();
            }
        }
        try{
            this.startConnection();
            this.statement.executeUpdate("insert into Code(Name,Occurences) values('"+newName+"',"+Integer.toString(amount)+");");
        }catch (Exception e){
            System.err.println(e.getLocalizedMessage());
        }
        finally{
            this.closeConnection();
        }
    }

    /**
     * Deletes the given codes from the DB
     * @param values
     * @param selectIndices written text, corresponding indice, codename
     */
    public void deleteCode(returnThree<String[],Integer[],String[]> values, int[] selectIndices){
        String[] realCode = values.third;
        for(int i=0;i<selectIndices.length;i++){
            int currentIndice = selectIndices[i];
            String currentCode = realCode[currentIndice];
            try {
                this.startConnection();
                this.statement.executeUpdate("DELETE FROM Code where Name='"+(String)currentCode+"';");
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally{
                this.closeConnection();
            }
        }
    }
    /**
     * Adds a given code to the DB
     * @param code
     */
    public void addCode(String code){
        try{
            startConnection();
            statement.executeUpdate("insert into Code(Name,Occurences) values('"+code+"',0);");         
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }finally{
            closeConnection();
        }
    }
    public void editCode(String newCodeName, String oldCodeName){
        startConnection();
        try{
            statement.executeUpdate("Update Code set Name='"+newCodeName+"' where Name='"+oldCodeName+"';");
        }catch (Exception k){

        }finally{
            closeConnection();
        }
    }

    public returnThree<String[],Integer[],String[]> getCodes(){
        String ret[];
        Integer val[];
        String normalNames[];
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> valueIntegers = new ArrayList<>();
        ArrayList<String> normalNameArrayList = new ArrayList<>();
        this.startConnection();
        try {
            ResultSet resultSet = this.statement.executeQuery("select Name,Occurences from Code order by Occurences DESC;");
            
            while(resultSet.next()){
                list.add(resultSet.getString("Name")+" ("+resultSet.getString("Occurences")+")");
                valueIntegers.add(resultSet.getInt("Occurences"));
                normalNameArrayList.add(resultSet.getString("Name"));
            }
            
        } catch (Exception e) {
            
        } finally{
            this.closeConnection();
        }
        ret = new String[list.size()];
        val = new Integer[list.size()];
        normalNames = new String[list.size()];
        for(int i = 0; i<ret.length;i++){
            ret[i] = list.get(i);
            val[i] = valueIntegers.get(i);
            normalNames[i] = normalNameArrayList.get(i);
        }
        returnThree<String[],Integer[],String[]> returner = new returnThree<String[],Integer[],String[]>(ret,val,normalNames);
        return returner;
    }
}

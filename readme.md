# Quantitative Content Analysis (QCA)
A program that helps you create a quantitative content analysis, precisely a tendency-driven analysis with an ordinal scale.
The program utilizes the Model–View–Controller (MVC) design pattern to create a seemingly functional and easily maintainable program.
 
## Impressions of the Program
<!-- Images of the program-->
![startpage of the program](/documentation/graphics/Startpage.png)

<p float="center">
  <img src="/documentation/graphics/Codebook.png" width="300" />
  <img src="/documentation/graphics/Document.png" width="300" /> 
  <img src="/documentation/graphics/Text.png" width="300" />
  <img src="/documentation/graphics/Coding.png" width="300" />
</p>

## Sample plots
<p float="center">
    <img src="/documentation/graphics/Total Occurences.png" width="400" /> 
  <img src="/documentation/graphics/Bewertungsverteilung.png" width="500" />
</p>

# Using the Program
Neccesary Prerequisites:
  * Java Development Kit (for example openjdk)
  * Python3
    * pip
    * sqlite3
    * Matplotlib
  * Sqlite3
  * Maven

Starting the Program:
> mvn clean install

> mvn exec:java -Dexec.mainClass="qca.App"

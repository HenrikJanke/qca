import sqlite3
import os

class database():
    def __init__(self) -> None:
        os.chdir("projects")
        pass
    def getYears(self):
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute("Select Date from Year;")
        yearList =[]
        for x in res.fetchall():
            yearList.append(x[0])
        connection.close()
        return yearList
    
    def getOccurencesOfYears(self):
        yearsList = {}
        # Creating dictionary with year values
        for x in self.getYears():
            yearsList[x]=0

        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute("select Count(Document.Year), Year.Date from Text join(Document join Year on Document.Year=Year.Id) on Text.Document=Document.Id group by Document.Year;")
        for amount,year in res.fetchall():
            yearsList[year] = amount
        connection.close()
        occurences=[]
        for x in yearsList:
            occurences.append(yearsList[x])

        return occurences
    
    def getCodes(self):
        curName = ""
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute("select Code.Name,Year.Date,Count(Code.Id) from Code join(CodeText join (Text join(Document join Year on Document.Year = Year.Id) on Text.Document=Document.Id) on CodeText.TextId=Text.Id) on CodeText.CodeId=Code.Id group by Code.Name, Year.Date order by Code.Name DESC;")
        yearOccurencesOfAllCode ={}
        for code,year,occurences in res.fetchall():
            if code != curName:
                yearOccurencesOfAllCode[code] = {year:occurences}
                curName = code
            else:
                yearOccurencesOfAllCode[code][year] = occurences
        connection.close()
        return yearOccurencesOfAllCode
    
    #select Code.Name,Year.Date,Count(Code.Id) from Code join(CodeText join (Text join(Document join Year on Document.Year = Year.Id) on Text.Document=Document.Id) on CodeText.TextId=Text.Id) on CodeText.CodeId=Code.Id group by Code.Name, Year.Date order by Code.Name DESC;
    def getMoods(self):
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute("select Mood.Name from Mood;")
        moods = []
        for mood in res.fetchall():
            moods.append(mood[0])
        connection.close()
        return moods
    
    def getMoodPerCodeInAYear(self,code,mood,years):
        dict = {}
        for x in years:
            dict[x]=0
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute(f"select Year.Date, Count(Mood.Name) As 'test'  from Code join(CodeText join(Mood join (Text join(Document join Year on Document.Year=Year.Id) on Text.Document=Document.Id) on Text.Mood=Mood.Id) on CodeText.TextId=Text.Id) on CodeText.CodeId=Code.Id where Code.Name='{code}' and Mood.Name='{mood}' group by Year.Date;")
        for year, amount in res.fetchall():
            dict[year]=amount
        connection.close()
        list = [x for x in dict.values()]
        return list
    
    def getMoodPercentageInYearForCode(self,code,moods,years):
        currentCodeMoodOccurences = self.getMoodPerCodeInAYear(code,moods,years)
        currentCodeOccurences = {}
        for x in years:
            currentCodeOccurences[x]=0
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute(f"select Year.Date, Count(Mood.Name) As 'test'  from Code join(CodeText join(Mood join (Text join(Document join Year on Document.Year=Year.Id) on Text.Document=Document.Id) on Text.Mood=Mood.Id) on CodeText.TextId=Text.Id) on CodeText.CodeId=Code.Id where Code.Name='{code}' group by Year.Date;")
        for year,amount in res.fetchall():
            currentCodeOccurences[year] = amount
        connection.close()
        final = []
        for i in range(len(currentCodeMoodOccurences)):
            if currentCodeOccurences[years[i]]!=0:
                # Maximale anzahl an den aktuellen Code/Aktuelle Anzahl des Moods
                final.append(currentCodeMoodOccurences[i]/currentCodeOccurences[years[i]])
            else:
                final.append(0)
        return final
    
    def getAllCodeOccurences(self,code,years):
        returner = []
        cur = {}
        for x in years:
            cur[x] = 0
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute(f"select Year.Date, count(Code.Id) from Year join(Document join(Text join(CodeText join Code on Code.Id=CodeText.CodeId) on Text.Id=CodeText.TextId) on Text.Document = Document.Id) on Year.Id=Document.Id where Code.Name ='{code}'group by Year.Date;")
        for year,amount in res.fetchall():
            cur[year] = amount
        connection.close()
        for x in cur:
            returner.append(cur[x])
        return returner
    
    def createTotalOccurencesData(self):
        name = []
        amount = []
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute(f"select Code.Name, Count(Code.Name) As 'Anzahl' from Code join CodeText on Code.Id=CodeText.CodeId group by Code.Name order by Anzahl DESC;")
        for code,occ in res.fetchall():
            name.append(occ)
            amount.append(code)
        connection.close()
        return (name,amount)
    
    def moodPerYear(self,year):
        connection = sqlite3.connect("Database.db")
        cursor = connection.cursor()
        res = cursor.execute(f"select Count(Mood.Name) As 'test'  from Code join(CodeText join(Mood join (Text join(Document join Year on Document.Year=Year.Id) on Text.Document=Document.Id) on Text.Mood=Mood.Id) on CodeText.TextId=Text.Id) on CodeText.CodeId=Code.Id where Year.Date={year} group by Year.Date;")
        try:
            amount = res.fetchone()[0]
        except:
            amount = 0
        res2 = cursor.execute(f"select Mood.Name, Count(Mood.Name) As 'test'  from Code join(CodeText join(Mood join (Text join(Document join Year on Document.Year=Year.Id) on Text.Document=Document.Id) on Text.Mood=Mood.Id) on CodeText.TextId=Text.Id) on CodeText.CodeId=Code.Id where Year.Date={year} group by Mood.Id;")
        pos = 0
        neut = 0
        neg = 0
        for mood,amou in res2.fetchall():
            if mood=="Positiv":
                pos = round(amou/amount,2)
            elif mood == "Neutral":
                neut = round(amou/amount,2)
            elif mood == "Negativ":
                neg = round(amou/amount,2)
        connection.close()
        return (pos,neut,neg)
        
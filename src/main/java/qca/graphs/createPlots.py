from matplotlib import pyplot as plt
from database import database
import numpy as np
import pandas as pd
import os

class plots(database):
    def __init__(self):
        os.chdir("projects")
        self.year = self.getYears()
        self.occurences = self.getOccurencesOfYears()
        

    def createPlots(self):
        # Create folders
        try:
            os.mkdir("output/Code Häufigkeit")
        except:
            pass
        try:
            os.mkdir("output/Mood Verteilung")
        except:
            pass
        try:
            os.mkdir("output/Code Häufigkeit Real")
        except:
            pass
        try:
            os.mkdir("output/Mood Pro Jahr")
        except:
            pass
        # Create the plots
        self.plotAmountOfDocuments()
        self.codeOccurencesForGivenCodeInTime()
        self.createTotalOccurences()
        self.createMoodPerYear()

    def plotAmountOfDocuments(self):
        plt.figure(figsize=(14,4))
        x = plt.bar(self.year,self.occurences)
        for bar in x:
            yval = bar.get_height()
            test = str(yval)
            plt.text(bar.get_x()+0.1,yval+5,(3-len(str(yval)))*"0"+test)

        plt.title("Verlauf der Anzahl der Texteinträge im Untersuchungszeitraum")
        plt.xlabel("Jahre")
        plt.ylabel("Anzahl der Texteinträge")
        plt.xticks(self.year)
        plt.savefig('output/DokumentenAnzahl.png', bbox_inches='tight')
        text_file = open(f"output/Dokumentenanzahl DATA.txt","a")
        text_file.write(f"YEAR: Amount of Documents\n")
        for x in range(len(self.year)):
            text_file.write(f"{self.year[x]}: {self.occurences[x]}\n")
        text_file.close()
        plt.close()


    def codeOccurencesForGivenCodeInTime(self):
        codes = self.getCodes()
        moods = self.getMoods()
        for currentCode in codes:
            self.__codeOccurencesForGivenCodeInTimeCreate(currentCode,codes[currentCode])
            self.moodPerCodeInAYear(currentCode,moods)
            self.codeOccurencesReal(currentCode,codes[currentCode])
    
    def __codeOccurencesForGivenCodeInTimeCreate(self,code,data):
        plt.figure(figsize=(14,4))
        ax = plt.gca()
        ax.set_ylim([0,100])
        test = {}
        for x in self.year:
            if x not in data:
                test[x]=0
            else:
                test[x]=data[x]
        finalOccurences = [test[x] for x in test]
        for x in range(len(finalOccurences)):
            if self.occurences[x]>0:
                finalOccurences[x] = (finalOccurences[x]/self.occurences[x])*100
        x = plt.bar(self.year,finalOccurences)
        
        for bar in x:
            yval = bar.get_height()
            test = str(yval.round(2))
            plt.text(bar.get_x()-0.05,yval+0.7,str(yval.round(2))+"%")
        # Writing to text file
        text_file = open(f"output/Code Häufigkeit/Verlauf der Kategorie Häufigkeit ALL DATA.txt","a")
        text_file.write(f"-------\n{code}\nYEAR: Occurence Percentage\n")
        for x in range(len(self.year)):
            text_file.write(f"{self.year[x]}: {round(finalOccurences[x],2)}\n")
        text_file.close()
        plt.title(f'Häufigkeit der Kategorie "{code}" im Betrachtungszeitraum')
        plt.xlabel("Jahre")
        plt.ylabel("Häufigkeit der Kategorie im Jahr")
        plt.xticks(self.year)
        plt.savefig(f'output/Code Häufigkeit/Verlauf Häufigkeit Kategorie {code}.png', bbox_inches='tight')
        plt.close()

    def codeOccurencesReal(self,code,data):
        plt.figure(figsize=(14,4))
        ax = plt.gca()
        ax.set_ylim([0,300])
        test = {}
        for x in self.year:
            if x not in data:
                test[x]=0
            else:
                test[x]=data[x]
        allCodes = [test[x] for x in test]
        x = plt.bar(self.year,allCodes)
        for bar in x:
            yval = bar.get_height()
            test = str(yval)
            plt.text(bar.get_x()+0.1,yval+1.5,str((3-len(test))*"0")+str(yval))
        
        # Writing to text file
        text_file = open(f"output/Code Häufigkeit Real/Verlauf der Code Häufigkeit ALL DATA.txt","a")
        text_file.write(f"-------\n{code}\nYEAR: Occurences\n")
        for x in range(len(self.year)):
            text_file.write(f"{self.year[x]}: {allCodes[x]}\n")
        text_file.close()
        plt.title(f'Häufigkeit des Codes "{code}" im Betrachtungszeitraum')
        plt.xlabel("Jahre")
        plt.ylabel("Anzahl der Codevorkommnise")
        plt.xticks(self.year)
        plt.savefig(f'output/Code Häufigkeit Real/Verlauf Häufigkeit Code {code}.png', bbox_inches='tight')
        plt.close()


    def moodPerCodeInAYear(self,code,moods):
        pos = self.getMoodPercentageInYearForCode(code,moods[0],self.year)
        neut = self.getMoodPercentageInYearForCode(code,moods[1],self.year)
        neg = self.getMoodPercentageInYearForCode(code,moods[2],self.year)
        df = pd.DataFrame({
            'Positiv': pos,
            'Neutral': neut,
            'Negativ': neg
        })
        
        # Plot stacked bar chart
        ax = df.plot(stacked=True, kind='bar',figsize=(12,4))
        # Set Tick labels
        ax.set_xticklabels(self.year,rotation='horizontal')
        handles, labels = ax.get_legend_handles_labels()
        ax.legend(handles[::-1], labels[::-1], loc='upper left')
        ax.set_title(f"Zeitlicher Verlauf der Bewertungsverteilung der Kategorie '{code}'")
        ax.set_ylabel("Teile des Gesamtenvokommens der Kategorie")
        ax.set_xlabel("Jahre")

        # Writing to text file
        text_file = open(f"output/Mood Verteilung/Verlauf der Bewertungsverteilung ALL DATA.txt","a")
        text_file.write(f"-------\n{code}\nYEAR POSITIV NEUTRAL NEGATIV\n")
        for x in range(len(self.year)):
            #print(code)
            text_file.write(f"{self.year[x]}: {round(pos[x]*100,2)}; {round(neut[x]*100,2)}; {round(neg[x]*100,2)}\n")
        text_file.close()
        # Display chart
        plt.savefig(f'output/Mood Verteilung/Verlauf der Bewertungsverteilung {code}.png', bbox_inches='tight')
        plt.close()
    
    def createTotalOccurences(self):
        data,labels = self.createTotalOccurencesData()
        # Writing to text file
        text_file = open(f"output/Total Occurences ALL DATA.txt","a")
        text_file.write(f"Code: Occurences\n")
        for x in range(len(data)):
            text_file.write(f"{labels[x]}: {data[x]}\n")
        text_file.close()
        fig, ax = plt.subplots()      
        ax.pie(data,labels=labels, autopct='%.0f%%',textprops={'size': 'smaller'}, radius=1)
    
        plt.savefig(f'output/Total Occurences.png', bbox_inches='tight')
        plt.close(fig)

    def createMoodPerYear(self):
        pos = []
        neut = []
        neg = []
        for x in self.year:
            p,n,ne = self.moodPerYear(x)
            pos.append(p)
            neut.append(n)
            neg.append(ne)
        df = pd.DataFrame({
            'Positiv': pos,
            'Neutral': neut,
            'Negativ': neg
        })
        ax = df.plot(stacked=True, kind='bar',figsize=(12,4))
        ax.set_xticklabels(self.year,rotation='horizontal')
        handles, labels = ax.get_legend_handles_labels()
        ax.legend(handles[::-1], labels[::-1], loc='upper left')
        ax.set_title("Verlauf der Bewertungsverteilungen")
        ax.set_xlabel("Jahre")
        ax.set_ylabel("Teile des Gesamtenvokommens der Kategorie")
        # Writing to text file
        text_file = open(f"output/Mood Pro Jahr/Verlauf der Bewertungsverteilung ALL DATA.txt","a")
        text_file.write(f"YEAR POSITIV NEUTRAL NEGATIV\n")
        for x in range(len(self.year)):
            text_file.write(f"{self.year[x]}: {pos[x]}; {neut[x]}; {neg[x]}\n")
        text_file.close()
        # Display chart
        plt.savefig(f'output/Mood Pro Jahr/Verlauf der Bewertungsverteilung.png', bbox_inches='tight')
        plt.close()
        

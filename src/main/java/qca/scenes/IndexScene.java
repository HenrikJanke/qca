package qca.scenes;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import qca.scenes.*;

public class IndexScene extends MasterScene{
    
    public IndexScene(JFrame window){
        this.window = window;
        this.main();       
    
    }
    private void main(){
        this.clearWindow();
        window.setTitle("Startpage");
        JPanel panel = new JPanel(new GridBagLayout());

        // Actionlistener to navigate the menu
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switch(e.getActionCommand()){
                    case "coding":
                        new AddCoding(window);
                        window.setVisible(true);
                        break;
                    case "document":
                        new AddDocument(window);
                        window.setVisible(true);
                        break;
                    case "text":
                        new AddText(window);
                        window.setVisible(true);
                        break;
                    case "newCodingToText":
                        new CodeText(window);
                        window.setVisible(true);
                        break;
                    case "statistics":
                        new CreateStatistics(window);
                        window.setVisible(true);
                }
            }
        };
        
        // Creating Header
        JLabel qca = new JLabel("QCA", JLabel.CENTER);
        qca.setFont(new Font("Serif",Font.PLAIN,150));
        
        // Creating Buttons and linkening them to the Actionlistener
        JPanel buttonPanel = new JPanel();

        // Adding Buttons
        JButton coding = new JButton("Add Coding");
        coding.setActionCommand("coding");
        coding.addActionListener(listener);
        coding.setLocale(null);
        buttonPanel.add(coding);

        JButton addingRessources = new JButton("Add Document");
        addingRessources.setActionCommand("document");
        addingRessources.addActionListener(listener);
        buttonPanel.add(addingRessources);

        JButton addingText = new JButton("Add Text");
        addingText.setActionCommand("text");
        addingText.addActionListener(listener);
        buttonPanel.add(addingText);

        JButton addCodingToText = new JButton("Add Coding to Text");
        addCodingToText.setActionCommand("newCodingToText");
        addCodingToText.addActionListener(listener);
        buttonPanel.add(addCodingToText);

        JButton createStatistics = new JButton("Create Statistics");
        createStatistics.setActionCommand("statistics");
        createStatistics.addActionListener(listener);
        buttonPanel.add(createStatistics);

        
        // Adding GridbagConstraints
        // Barrier
        addEmptySpace(panel, 0, 0, 1, 4, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH);
        addEmptySpace(panel, 2, 0, 1, 4, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.BOTH);
        addEmptySpace(panel, 1, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        // Adding Title to panel
        addToContainer(panel, qca, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        // Adding buttonPanel to panel
        addToContainer(panel, buttonPanel, 1,2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        // Adding panel to window
        this.window.add(panel);
    }
    
}

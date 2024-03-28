package qca.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import qca.functionality.CreateStatisticsFunctionality;

public class CreateStatistics extends MasterScene {
    public CreateStatistics(JFrame window){
        this.window = window;
        this.start();
    }
    private void start(){
        this.clearWindow();
        window.setTitle("Create the Statistics");
        
        // Creating the sibling from the functional package
        final CreateStatisticsFunctionality func = new CreateStatisticsFunctionality();
        
        // Create the actionslistener
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                switch(e.getActionCommand()){
                    case "returner":
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                    case "savecodedTexts":
                        func.createCodedTextsOutput();
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                    case "createStatistics":
                        func.createStatistics();
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                }
            }
        };

        // Create panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel boxPanel = new JPanel();
        BoxLayout layout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
        boxPanel.setLayout(layout);
        JPanel functionalButtonsPanel = new JPanel();
        
        // header
        JLabel header = new JLabel("Create the Statistics");
        header.setFont(new Font("Serif",Font.CENTER_BASELINE,50));
        header.setAlignmentX(Container.CENTER_ALIGNMENT);

        // Create buttons
        JButton returner = new JButton("Return to start");
        returner.setActionCommand("returner");
        returner.addActionListener(listener);
        returner.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton saveCodedTexts = new JButton("Save coded Texts");
        saveCodedTexts.setActionCommand("savecodedTexts");
        saveCodedTexts.addActionListener(listener);

        JButton createStatistics = new JButton("Create Statistics");
        createStatistics.setActionCommand("createStatistics");
        createStatistics.addActionListener(listener);

        // Adding the functional buttons to the functionalButtonsPanel
        functionalButtonsPanel.add(saveCodedTexts);
        functionalButtonsPanel.add(createStatistics);

        // Adding components to boxPanel
        boxPanel.add(header);
        boxPanel.add(Box.createRigidArea(new Dimension(5,20)));
        boxPanel.add(functionalButtonsPanel);
        boxPanel.add(Box.createRigidArea(new Dimension(5,10)));
        boxPanel.add(returner);
        
        // Adding the side barriers and the center boxPanel to the panel
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,boxPanel,1, 0, 1, 4, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        
        window.add(panel);
    }
}

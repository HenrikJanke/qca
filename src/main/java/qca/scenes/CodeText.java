package qca.scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import qca.functionality.CodeTextFunctionality;

public class CodeText extends MasterScene{
    String mood = "";
    String code = "";
    String currentText = "";

    public CodeText(JFrame window){
        this.window = window;
        this.start();
    }
    private void start(){
        this.clearWindow();
        window.setTitle("Add Coding");

        // Create the sibling from the functional package
        final CodeTextFunctionality func = new CodeTextFunctionality();
        
        // Creating the panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();
        JPanel boxPanel = new JPanel();
        JPanel comboBoxPanel = new JPanel();
        JPanel moodPanel = new JPanel();
        JPanel codePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
        BoxLayout comboBoxBoxLayout = new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS);
        BoxLayout codeBoxLayout = new BoxLayout(codePanel, BoxLayout.Y_AXIS);
        BoxLayout moodBoxLayout = new BoxLayout(moodPanel, BoxLayout.Y_AXIS);
        boxPanel.setLayout(boxLayout);
        comboBoxPanel.setLayout(comboBoxBoxLayout);    
        moodPanel.setLayout(moodBoxLayout);
        codePanel.setLayout(codeBoxLayout);


        // Header
        JLabel header = new JLabel("Add Coding");
        header.setFont(new Font("Serif",Font.CENTER_BASELINE,50));
        header.setAlignmentX(Container.CENTER_ALIGNMENT);
        boxPanel.add(header);
        boxPanel.add(Box.createRigidArea(new Dimension(5,20)));
        
        // Creating the textarea
        currentText = func.getText();
        final JTextArea text = new JTextArea(currentText,13,40);
        JScrollPane textPane = new JScrollPane(text);
        JLabel pleaseLabel = new JLabel("Please code the text:");
        pleaseLabel.setLabelFor(text);
        text.setLineWrap(true);
        text.setEditable(false);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        pleaseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        // ComboBox Mood
        final String[] chooseMood = func.getEntrys("Mood", "Name");
        final JList chooList = new JList(chooseMood);
        JLabel docChoserLabel = new JLabel("Choose the Mood:");
        docChoserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        docChoserLabel.setLabelFor(chooList);
        
        // ComboBox Code
        final String[] chooseCode = func.getEntrys("Code", "Name");
        final JLabel codeChoserLabel = new JLabel("Choose the Code:");
        final JList codeList = new JList(chooseCode);
        codeChoserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        codeChoserLabel.setLabelFor(codeList);
        
        // Buttons
        JButton returner = new JButton("Return to start");
        returner.setActionCommand("returner");
        JButton submit = new JButton("Submit");
        submit.setActionCommand("submit");
     

        // Creating the actionlistener
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                switch(e.getActionCommand()){
                    case "returner":
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                    case "submit":
                        // Adding the code and mood for the given text into the DB and reloading the scene
                        mood = chooseMood[chooList.getSelectedIndex()];
                        code = chooseCode[codeList.getSelectedIndex()];
                        if(code!="" && mood!=""){
                            func.doCode(code, mood, text.getText());
                            func.doMood(mood,text.getText());
                            new CodeText(window);
                            window.setVisible(true);
                        }
                        break;
                }
            }
        };

        // If there is at least 1 text that needs to be coded, the textarea and code+mood JLists are added
        if(func.needsCoding()>0){
            JLabel needsCoding;
            if(func.needsCoding()==1){
                needsCoding = new JLabel("1 Text needs cooding");
            }else{
                needsCoding = new JLabel(Integer.toString(func.needsCoding())+" Texts need cooding");
            }  
            needsCoding.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Adding the components to their logical Panel and the center boxPanel
            boxPanel.add(textPane);
            boxPanel.add(Box.createRigidArea(new Dimension(5,10)));
            moodPanel.add(docChoserLabel);
            moodPanel.add(Box.createRigidArea(new Dimension(5,3)));
            moodPanel.add(chooList);
            codePanel.add(codeChoserLabel);
            codePanel.add(Box.createRigidArea(new Dimension(5,3)));
            codePanel.add(codeList);
            comboBoxPanel.add(moodPanel);
            comboBoxPanel.add(Box.createRigidArea(new Dimension(30, 20)));
            comboBoxPanel.add(codePanel);
            boxPanel.add(comboBoxPanel);
            boxPanel.add(Box.createRigidArea(new Dimension(5,20)));
            boxPanel.add(needsCoding);
            submit.addActionListener(listener);
            buttonPanel.add(submit);
            
        }else{
            JLabel allCoded = new JLabel("You have coded everything!");
            allCoded.setAlignmentX(Container.CENTER_ALIGNMENT);
            boxPanel.add(allCoded);
            boxPanel.add(Box.createRigidArea(new Dimension(5,20)));
        }

        returner.addActionListener(listener);
        buttonPanel.add(returner);
        boxPanel.add(buttonPanel);
        
        // Adding the boxPanel to the main GridBagLayout panel
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,boxPanel,1, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        window.add(panel);
    }
}

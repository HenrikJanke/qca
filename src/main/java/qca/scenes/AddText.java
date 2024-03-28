package qca.scenes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.JTextField;
//import javax.swing.plaf.ActionMapUIResource;
import javax.swing.*;

import qca.functionality.AddTextFunctionality;

public class AddText extends MasterScene{
    String comboBoxChosed = "";
    // The latest chosen document
    int comboBoxStartInt = 0;

    /**
     * Constructor without prior choosing of the document
     * @param window
     */
    public AddText(JFrame window){
        this.window = window;
        //window.setVisible(true);
        this.start();
    }

    /**
     * Constructor with prior choosing of the document
     * @param window
     * @param current
     */
    public AddText(JFrame window, int current){
        this.window = window;
        this.comboBoxStartInt = current;
        //window.setVisible(true);
        this.start();
    }

    /**
     * Starts the addText subapplication
     */
    private void start(){
        this.clearWindow();
        window.setTitle("Choose the Document");
        
        // Create sibling
        final AddTextFunctionality func = new AddTextFunctionality();
        
        // Creating the panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel boxPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
        boxPanel.setLayout(boxLayout);

        
        // Creating the header
        JLabel header = new JLabel("Add a Text");
        header.setFont(new Font("Serif",Font.CENTER_BASELINE,50));
        

        // Adding the JComboBox where the document is beeing choosed 
        String[] choserArray = func.getDocuments();
        JLabel docChoserLabel = new JLabel("Choose the Document:");
        final JComboBox comboBoxChoser = new JComboBox(choserArray);
        comboBoxChoser.setPrototypeDisplayValue("das ist");
        docChoserLabel.setLabelFor(comboBoxChoser);
        ActionListener comboBoxListener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                comboBoxChosed = comboBoxChoser.getSelectedItem().toString();
            }
        };
        comboBoxChoser.addActionListener(comboBoxListener);
        // If no document was preselected, select the latest added document
        if (comboBoxStartInt==0){
            comboBoxStartInt=choserArray.length-1;
        }
        comboBoxChoser.setSelectedIndex(comboBoxStartInt);
                

        // Adding the textbox, where the Text is being inserted
        JLabel inputLabel = new JLabel("Please insert the text:");
        final JTextArea text = new JTextArea("",10,50);
        JScrollPane textPane = new JScrollPane(text);
        text.setLineWrap(true);
        inputLabel.setLabelFor(text);
        
        // Adding the Buttons
        JPanel buttonPanel = new JPanel();
        JButton submit = new JButton("Submit");
        submit.setActionCommand("submit");
        JButton reload = new JButton("Reload");
        reload.setActionCommand("Reload");
        JButton returnStart = new JButton("Return to start");
        returnStart.setActionCommand("start");
        
        
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                switch(e.getActionCommand()){
                    case "start":
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                    case "submit":
                        comboBoxChosed = comboBoxChoser.getSelectedItem().toString();
                        String text2 = text.getText().toString();
                        // removing linebreaks
                        String x = text2.replace("\n", " ");
                        // validating if both strings are not empty
                        if(validate(comboBoxChosed,text2)){
                            // Adding the textentry and choosen document to the DB
                            func.addText(comboBoxChosed, x);
                            new AddText(window,comboBoxChoser.getSelectedIndex());
                            window.setVisible(true);
                            break;
                        }
                        break;
                    case "Reload":
                        // Reloading the window, neccesary if in another instance a new document was added
                        clearWindow();
                        new AddText(window);
                        window.setVisible(true);
                        break;
                }
            }
        };
        // Alligning components to the center
        docChoserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setAlignmentX(Container.CENTER_ALIGNMENT);


        // Adding actionlistener to buttons and buttons to their buttonPanel
        returnStart.addActionListener(listener);
        submit.addActionListener(listener);
        buttonPanel.add(submit);
        buttonPanel.add(reload);
        buttonPanel.add(returnStart);

        // Adding the components to the boxPanel
        boxPanel.add(header);
        boxPanel.add(Box.createRigidArea(new Dimension(5,20)));
        boxPanel.add(docChoserLabel);
        boxPanel.add(Box.createRigidArea(new Dimension(5,3)));
        boxPanel.add(comboBoxChoser);
        boxPanel.add(Box.createRigidArea(new Dimension(5,10)));   
        boxPanel.add(inputLabel);
        boxPanel.add(Box.createRigidArea(new Dimension(5,3)));
        boxPanel.add(textPane);
        boxPanel.add(Box.createRigidArea(new Dimension(5,20)));
        boxPanel.add(buttonPanel);
        
        // Adding the boxPanel to the main panel with appropriate padding
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,boxPanel,1, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        window.add(panel);

    }

    /**
     * Validates if both Strings are not empty
     * @param comb
     * @param texti
     * @return
     */
    public Boolean validate(String comb, String texti){
        if(comb==""){
            return false;
        }
        if(texti==""){
            return false;
        }
        return true;
    }
}

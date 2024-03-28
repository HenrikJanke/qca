package qca.scenes;

import javax.swing.*;

import qca.functionality.AddCodingFunctionality;
import qca.functionality.returnThree;

import java.awt.*;
//import java.awt.Color;
import java.awt.event.*;
import java.util.Arrays;


public class AddCoding extends MasterScene{
    AddCodingFunctionality funct;
    
    public AddCoding(JFrame window){
        this.window = window;
        this.funct = new AddCodingFunctionality();
        this.start();
    }

    /**
     * Calls the different functions if there is code or not
     */
    private void start(){
        this.clearWindow();
        if(this.funct.noCode()){
            this.noCodeExists();
        }else{
            this.codeExists();
        }    
    }

    /**
     * Displaying buttons for the creation of the codes (inductive) 
     */
    private void noCodeExists(){
        this.clearWindow();
        window.setTitle("Create a Codebook");
        
        // Adding the panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel boxPanel = new JPanel();
        BoxLayout layout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
        boxPanel.setLayout(layout);

        // header
        JLabel header = new JLabel("Create a new Codebook");
        header.setFont(new Font("Serif",Font.CENTER_BASELINE,50));
        
        // Adding the buttons
        JButton returner = new JButton("Return to start");
        returner.setActionCommand("return");
        JButton createCoding = new JButton("Create a new Codebook");
        createCoding.setActionCommand("createCode");
        
        // Creating the actionlistener
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switch(e.getActionCommand()){
                    case "return":
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                    case "createCode":
                        funct.createCode();
                        new AddCoding(window);
                        window.setVisible(true);
                        break;
                }
            }
        };

        // Adding the actionlistener
        createCoding.addActionListener(listener);
        returner.addActionListener(listener);
        
        // Setting the allignment
        returner.setAlignmentX(Container.CENTER_ALIGNMENT);
        createCoding.setAlignmentX(Container.CENTER_ALIGNMENT);
        header.setAlignmentX(Container.CENTER_ALIGNMENT);
        
        // Adding everything to the boxPanel
        boxPanel.add(header);
        boxPanel.add(Box.createRigidArea(new Dimension(5,20)));
        boxPanel.add(createCoding);
        boxPanel.add(Box.createRigidArea(new Dimension(5,10)));
        boxPanel.add(returner);

        // Adding the boxPanel with appropriate padding to the panel
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,boxPanel,1, 0, 1, 4, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        window.add(panel);
    }

    /**
     * Displaying of the Codes and the possibilities to alter them
     */
    private void codeExists(){
        this.clearWindow();
        window.setTitle("Edit the Codebook");
        
        // Creating the panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel scrollPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel returnPanel = new JPanel();
        JPanel centerPane = new JPanel();
        BoxLayout box = new BoxLayout(centerPane, BoxLayout.Y_AXIS);
        centerPane.setLayout(box);
        

        // Adding header
        JLabel header = new JLabel("Codebook");
        header.setFont(new Font("Serif",Font.CENTER_BASELINE,50));
        
        // Scrollpane for displaying the existing codes
        final returnThree<String[],Integer[],String[]> val = funct.getCodes();
        String labels[] = val.first;
        final JList jlist = new JList(labels);
        jlist.setVisibleRowCount(25);
        jlist.setFixedCellWidth(470);
        JScrollPane codeScrollPane = new JScrollPane(jlist);
       
        // Buttons
        JButton combineCode = new JButton("Combine Codes");
        combineCode.setActionCommand("combine");
        JButton deleteCode = new JButton("Delete Code");
        deleteCode.setActionCommand("delete");
        JButton addCode = new JButton("Add Code");
        addCode.setActionCommand("add");
        JButton editCode = new JButton("Edit Code Name");
        editCode.setActionCommand("edit");
        JButton returner = new JButton("Return to start");
        returner.setActionCommand("return");


        // Actionlistener
        ActionListener codeAltering = new ActionListener() {
        public void actionPerformed(ActionEvent e){ 
                switch(e.getActionCommand()){
                    case "combine":
                        System.out.println(Arrays.toString(jlist.getSelectedIndices()));
                        if(jlist.getSelectedIndices().length>1){
                            funct.combine(val,jlist.getSelectedIndices());
                            new AddCoding(window);
                            window.setVisible(true);
                        }
                        break;
                    case "delete":
                        if(jlist.getSelectedIndices().length>0){
                            funct.deleteCode(val,jlist.getSelectedIndices());
                            new AddCoding(window);
                            window.setVisible(true);
                        }
                        break;
                    case "add":
                        addCode();
                        window.setVisible(true);
                        break;
                    case "edit":
                        //funct.editCode(val, jlist.getSelectedIndex(), window);
                        if(jlist.getSelectedIndex()!=-1){
                            editCode(val, jlist.getSelectedIndex());
                            window.setVisible(true);
                        }
                        break;
                    case "return":
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                }
            }
        };

        // Adding the actionlistener
        combineCode.addActionListener(codeAltering);
        deleteCode.addActionListener(codeAltering);
        addCode.addActionListener(codeAltering);
        editCode.addActionListener(codeAltering);
        returner.addActionListener(codeAltering);

        // Setting the allignment of certain components
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding the codeScrollPane
        scrollPanel.add(codeScrollPane);
 
        // Adding the buttons
        buttonPanel.add(combineCode);
        buttonPanel.add(deleteCode);
        buttonPanel.add(addCode);
        buttonPanel.add(editCode);
        returnPanel.add(returner);
        
        // Adding to Boxlayout
        centerPane.add(header);
        centerPane.add(scrollPanel);
        centerPane.add(buttonPanel);
        centerPane.add(returnPanel);
        
        // Adding the centerPane to the main panel
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,centerPane,1, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        
        this.window.add(panel);
    }

    /**
     * Popup for adding a new Code (deductiv)
     */
    private void addCode(){
        // Creating new JFrame for the popup
        final JFrame addCodeFrame = new JFrame("Insert a new Code");
        addCodeFrame.setSize(300, 125);
        addCodeFrame.setVisible(true);

        // Creating the panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel centerPane = new JPanel();
        BoxLayout layout = new BoxLayout(centerPane, BoxLayout.Y_AXIS);
        centerPane.setLayout(layout);

        // Creating a JTextfield
        JLabel textLabel = new JLabel("Insert a Code Name:");
        final JTextField textFile = new JTextField(20);
        textLabel.setLabelFor(textFile);

        // Adding button with ActionListener
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                    funct.addCode((String)textFile.getText());
                    addCodeFrame.setVisible(false);
                    new AddCoding(window);
                    window.setVisible(true);
                }catch (Exception k){
                    System.out.println(k.getLocalizedMessage());
                }
            }
        });

        // Set the allignment for certain components
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding the components to the centerPane
        centerPane.add(textLabel);
        centerPane.add(Box.createRigidArea(new Dimension(5,3)));
        centerPane.add(textFile);
        centerPane.add(Box.createRigidArea(new Dimension(5,5)));
        centerPane.add(submit);

        // Adding the centerPane to the panel
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,centerPane,1, 0, 1, 4, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        addCodeFrame.add(panel);

    }

    /**
     * Creation of a popup with the possibility to edit the selected code
     * @param values
     * @param selectedIndex
     */
    private void editCode(returnThree<String[],Integer[],String[]> values, final int selectedIndex){
        // Creating new JFrame for the popup
        final JFrame editCodeFrame = new JFrame("Edit Code Name");
        editCodeFrame.setSize(300, 125);
        editCodeFrame.setVisible(true);

        // Existing code, before the edit
        final String[] realCode = values.third;
        
        // Creating the panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel centerPane = new JPanel();
        BoxLayout layout = new BoxLayout(centerPane, BoxLayout.Y_AXIS);
        centerPane.setLayout(layout);

        // Create the neccesare JTextField
        JLabel textLabel = new JLabel("Edit Code Name:");
        final JTextField textFile = new JTextField(20);
        textFile.setText(realCode[selectedIndex]);
        textLabel.setLabelFor(textFile);

        // Creating the submit button with his respective ActionListener
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            // closes window and reloads main window
            public void actionPerformed(ActionEvent e){
                funct.editCode((String)textFile.getText(),(String)realCode[selectedIndex]);
                editCodeFrame.setVisible(false);
                new AddCoding(window);
                window.setVisible(true);
            }
        });
        
        // Set the allignment for certain components
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding the components to the boxLayout centerPane
        centerPane.add(textLabel);
        centerPane.add(Box.createRigidArea(new Dimension(5,3)));
        centerPane.add(textFile);
        centerPane.add(Box.createRigidArea(new Dimension(5,5)));
        centerPane.add(submit);

        // Adding the centerPane to the panel
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,centerPane,1, 0, 1, 4, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        editCodeFrame.add(panel);

    }
}

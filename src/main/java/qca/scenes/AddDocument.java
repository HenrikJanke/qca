package qca.scenes;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

import qca.functionality.AddDocumentFunctionality;


public class AddDocument extends MasterScene {
    String year = "";
    String institutionName = "";
    String documentName = "";
    String errorMessage = "";
    int curYear = -1;


    public AddDocument(JFrame window){
        window.revalidate();
        this.window = window;
        this.start();
        window.setVisible(true);
    }

    /**
     * This constructor is called if an semanticall error occured previously
     * @param window
     * @param year
     * @param institutionName
     * @param documentName
     * @param errrorMessage
     */
    public AddDocument(JFrame window,String year, String institutionName, String documentName, String errrorMessage){
        this.window = window;
        this.year = year;
        this.institutionName = institutionName;
        this.documentName = documentName;
        this.errorMessage = errrorMessage;
        this.start();
        window.setVisible(true);
    }

    public AddDocument(JFrame window, String successMessage){
        this.window = window;
        this.errorMessage = successMessage;
        this.start();
        window.setVisible(true);
    }

    public AddDocument(JFrame window,int curYear){
        this.curYear = curYear;
        window.revalidate();
        this.window = window;
        this.start();
        window.setVisible(true);
    }

    private void start(){
        this.clearWindow();
        window.setTitle("Add a Document");

        // creating functional sibling qca.functional
        final AddDocumentFunctionality func = new AddDocumentFunctionality();
        
        // Creating the panels
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel boxPanel = new JPanel();
        JPanel instituionYearPanel = new JPanel();
        JPanel institutionPanel = new JPanel();
        JPanel yearPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
        boxPanel.setLayout(boxLayout);


        // Header
        JLabel header = new JLabel("Add a Document");
        header.setFont(new Font("Serif",Font.CENTER_BASELINE,50));
        header.setAlignmentX(Container.CENTER_ALIGNMENT);
        
        // Document Name
        JLabel labelDocumentName = new JLabel("Input a Document Name:");
        final JTextField textDocumentName = new JTextField();
        labelDocumentName.setLabelFor(textDocumentName);
                
        // Institution name
        JLabel labelInstitutionName = new JLabel("Input Institution Name:");
        String[] allInstitutions = func.getAllInstitutions();
        final JComboBox comboBoxInstitutions = new JComboBox(allInstitutions);
        comboBoxInstitutions.setPreferredSize(new Dimension((int)(window.getWidth()/4.2),20));
        comboBoxInstitutions.setMaximumRowCount(10);
        ActionListener comboInstitutions = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            institutionName = comboBoxInstitutions.getSelectedItem().toString();
        }
        };
        comboBoxInstitutions.setSelectedIndex(0);
        comboBoxInstitutions.addActionListener(comboInstitutions);
        labelInstitutionName.setLabelFor(comboBoxInstitutions);
        
        // Year
        String[] possibleYears = func.getAllYears();
        JLabel labelYear = new JLabel("Input Year:");
        final JComboBox comboBoxYears = new JComboBox(possibleYears);
        labelYear.setLabelFor(comboBoxYears);
        ActionListener comboYearsListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            year = comboBoxYears.getSelectedItem().toString();
        }
        };
        comboBoxYears.setSelectedIndex(curYear);
        comboBoxYears.addActionListener(comboYearsListener);
          
        // Adding the buttons
        JPanel buttonPanel = new JPanel();
        JPanel programmButtons = new JPanel();
        JButton editYear = new JButton("Edit Year");
        JButton editInstitution = new JButton("Edit Institution");
        JButton startScreen = new JButton("Return to start");
        JButton send = new JButton("Submit");
        startScreen.setActionCommand("returner");
        send.setActionCommand("submit");


        //Actionlistener
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switch(e.getActionCommand()){
                    case "submit":
                        documentName = textDocumentName.getText();
                        institutionName = comboBoxInstitutions.getSelectedItem().toString();
                        year = comboBoxYears.getSelectedItem().toString();
                        System.out.println(textDocumentName.getText());
                        if(validate()){
                            func.addNewDocument(year, institutionName, documentName);
                            new AddDocument(window,comboBoxYears.getSelectedIndex());
                            window.setVisible(true);
                        }
                        break;
                    case "returner":
                        new IndexScene(window);
                        window.setVisible(true);
                        break;
                }
            }
        };

        // Adding the actionlisteners
        startScreen.addActionListener(listener);
        send.addActionListener(listener);

        // Setting up allignment
        textDocumentName.setAlignmentX(Container.CENTER_ALIGNMENT);
        labelDocumentName.setAlignmentX(Container.CENTER_ALIGNMENT);
        instituionYearPanel.setAlignmentX(Container.CENTER_ALIGNMENT);
        programmButtons.setAlignmentX(Container.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Container.CENTER_ALIGNMENT);

        
        // Institution and year to their respective subpanels
        institutionPanel.add(labelInstitutionName);
        institutionPanel.add(comboBoxInstitutions);
        yearPanel.add(labelYear);
        yearPanel.add(comboBoxYears);
        instituionYearPanel.add(institutionPanel);
        instituionYearPanel.add(yearPanel);
        
        // Adding the buttons to their subpanel (programmButtons for main program)
        buttonPanel.add(editInstitution);
        buttonPanel.add(editYear);
        buttonPanel.add(send);
        programmButtons.add(startScreen);

        // Adding Boxpanel
        boxPanel.add(header);
        boxPanel.add(Box.createRigidArea(new Dimension(5,20))); 
        boxPanel.add(labelDocumentName);
        boxPanel.add(Box.createRigidArea(new Dimension(5,3)));
        boxPanel.add(textDocumentName);  
        boxPanel.add(instituionYearPanel);
        // Adding an errormessage to the screen, if an input is missing 
        if(this.errorMessage!=""){
            JLabel error = new JLabel(this.errorMessage);
            boxPanel.add(error);
        }        
        boxPanel.add(buttonPanel);
        boxPanel.add(programmButtons);

        // Adding the boxPanel with the padding to the panel
        addEmptySpace(panel, 0, 0, 1, 3, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 2, 0, 1, 3, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addEmptySpace(panel, 1, 3, 1, 10, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addToContainer(panel,boxPanel,1, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        window.add(panel);
        window.setVisible(true);
    }

    /**
     * Validates if an error occured
     * @return true if everything is entered correctly, otherwise AddDocument with the errormessage is being called
     */
    private Boolean validate(){
        String Message = "";
        if(this.documentName==""){
            Message = Message+"Please enter a document name\n";
        }
        if(this.institutionName==""){
            Message = Message+"Please choose an insitution\n";
        }
        if(this.year==""){
            Message = Message+"Please choose a year\n";
        }
        if(Message!=""){
            new AddDocument(window, year, institutionName, documentName, Message);
            return false;
        }
        return true;
    }
}
package qca.scenes;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
//import java.awt.Color;

public abstract class MasterScene {
    JFrame window;

    /**
     * This method clears the JFrame window
     */
    public void clearWindow(){
        window.getContentPane().removeAll();
        window.repaint();
        window.setVisible(true);
    }

    /**
     * This method ads a given component to a given container with
     * the given GridBagConstraints 
     * @param container the GridBagLayout panel
     * @param component the component that we want to add to the container (JPanel, JButton, etc.)
     * @param gridx the x starting position on the Grid
     * @param gridy the y starting position on the Grid
     * @param gridwidth the width the component shall occupy
     * @param gridheight the height the component shall occupy
     * @param anchor where it should be anchored (GridBagConstraints.CENTER, GridBagConstraints.PAGE_START)
     * @param fill what should happen when there is more space available (GridBagConstraints.(VERTICAL,HORIZONTAL,NONE,BOTH))
     */
    static void addToContainer(Container container, Component component, 
                        int gridx, int gridy, int gridwidth, int gridheight, 
                        int anchor, int fill){
        container.add(component,new GridBagConstraints(gridx,gridy,gridwidth,gridheight,1.0,1.0,anchor,fill,new Insets(0,0,0,0),0,0));
    }
    
    /**
     * Adds an emptyspace to the grid of the GridBagLayout, for futher instructions see prior function addToContainer
     */
    void addEmptySpace(Container container, int gridx, int gridy, int gridwidth, 
                        int gridheight,int anchor, int fill){
        MasterScene.addToContainer(container, new JLabel(""), gridx, gridy, gridwidth, gridheight, anchor, fill);
    }
}

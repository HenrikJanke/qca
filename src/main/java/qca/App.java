package qca;
import java.awt.Dimension;

import javax.swing.JFrame;
import qca.scenes.*;
import com.formdev.flatlaf.*;

public class App{
    JFrame window;
    public static void main(String[] args) {
        // Starting the application
        new App();
        
    }
    /**
     * Initialising the window
     */
    private App(){
        // Flatlaf layout, better GUI
        FlatDarculaLaf.setup();
        
        // Window initialisation
        window = new JFrame("Window");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1500, 700);
        window.setMinimumSize(new Dimension(650, 500));
        window.setResizable(true);
        
        // Starting the App
        new IndexScene(window);
        window.setVisible(true);
    }
    
}

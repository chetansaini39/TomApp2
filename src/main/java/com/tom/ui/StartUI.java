package com.tom.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import sun.awt.resources.awt;

public class StartUI {
      
     	    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height=(int) (screenSize.getHeight()-100);
          /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MainFrame mainFrame=new MainFrame();
            mainFrame.setSize(screenSize);
            mainFrame.setVisible(true);
        });
    
           
           
    }
}
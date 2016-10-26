package com.tom.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import sun.awt.resources.awt;

public class StartUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            Dimension fixedScreenSize = new Dimension(1920, 1200);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (fixedScreenSize.getHeight() != screenSize.getHeight() && fixedScreenSize.getWidth() != screenSize.getWidth()) {
                screenSize = fixedScreenSize;
            }
            screenSize.height = (int) (screenSize.getHeight() - 100);
            screenSize.width = (int) (screenSize.getWidth() - 450);
            MainFrame mainFrame = new MainFrame();
            mainFrame.setSize(screenSize);
            mainFrame.setVisible(true);
            mainFrame.setResizable(false);
        });

    }
}

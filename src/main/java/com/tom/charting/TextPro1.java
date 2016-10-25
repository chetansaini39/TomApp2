/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tom.charting;

import com.tom.AppSharedData.AppSharedData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author csaini
 */
public class TextPro1 extends JPanel {

    int width = 414, height = 650;
    String numericTxtBig = "7.1M";
    private Color numberColor = new Color(143, 212, 0);
    Color backgroundColor = new Color(27, 35, 42);
    Color txtColorColor = new Color(0, 160, 223);
    Color lineColor = new Color(54, 66, 74);
//    JPanel this;

    public TextPro1( String numericTxtBig,   Color numberColor) {
        this.numericTxtBig=numericTxtBig;
        this.numberColor=numberColor;
    }

    
    public void getTextPanel(JPanel panel) {
        int numbersize=210;
        if(numericTxtBig.length()>2)
        {
            numbersize=150;
        }
        Font bigNumTxtFont = new Font("BebasNeue", Font.BOLD, numbersize);
        panel.setBackground(backgroundColor);    
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel label_NumericVal = new JLabel(numericTxtBig);
        label_NumericVal.setFont(bigNumTxtFont);
        label_NumericVal.setForeground(getNumberColor());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 0;
        c.insets = new Insets(0,60,0,0);  //top padding
        if(numbersize==150)
        {
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 60;
        c.insets = new Insets(0,40,0,0);  //top padding  
        }
        panel.add(label_NumericVal, c);

        Font txtFont = new Font("BebasNeue", Font.BOLD, 60);
        JLabel label_total = new JLabel("T O T A L");
//        label_total.setLocation(40, 150);
        label_total.setFont(txtFont);
        label_total.setForeground(txtColorColor);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.0;
        c.weightx = 0.0;        
        c.ipady = 0;
        c.insets = new Insets(0,60,0,0); 
//        c.ipadx=40;
if(numbersize==150)
        {
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0.0;
        c.weightx = 0.0;        
        c.ipady = 0;
        c.insets = new Insets(10,60,0,0);   //top padding  
        }
        panel.add(label_total,c);
        
        JLabel label_currWeel = new JLabel("C U R R E N T   W E E K");
        label_currWeel.setFont(txtFont);
        label_currWeel.setForeground(txtColorColor);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = -10;
        c.insets = new Insets(0,-10,0,0); 
        panel.add(label_currWeel,c);
        //add the components to this
        
        
        panel.revalidate();
        panel.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

        g.setColor(lineColor);
        g.fillRect(35, 420, AppSharedData.CHART_PANEL_Y, 7);//horizontal
        g.fillRect(10, 150, 7, AppSharedData.CHART_PANEL_Y+40);//vertical
//        g.drawLine(10, 100, 210, 100);
    }

    /**
     * @return the numberColor
     */
    public Color getNumberColor() {
        return numberColor;
    }

    /**
     * @param numberColor the numberColor to set
     */
    public void setNumberColor(Color numberColor) {
        this.numberColor = numberColor;
    }



}

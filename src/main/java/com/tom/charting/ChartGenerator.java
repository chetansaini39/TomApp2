package com.tom.charting;

import com.google.common.primitives.Doubles;
import com.tom.AppSharedData.AllConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.math.plot.Plot2DPanel;
import org.math.plot.plotObjects.BaseLabel;
import org.math.plot.plots.HistogramPlot2D;
import org.math.plot.plots.LinePlot;
import org.math.plot.utils.Array;

public class ChartGenerator {

    private Plot2DPanel plot;//plot on which chart will be drawn
    private Dimension dimension = null;
    private double red = 0.0;
    private double green = 0.0;

    /**
     * Method to create a histogram
     *
     * @param xData
     * @param yData
     * @param barColor
     * @param xAxisTitle
     * @param yAxisTitle
     * @param title
     * @param xSeriesTitle
     * @return
     */
    public void createHistogramChart(double[] xData, double[] yData, Color barColor, String xAxisTitle, String yAxisTitle, String title) {

        double[][] xyData = Array.buildXY(xData, yData);
        if (getDimension() != null) {
            Rectangle boundingRectangle = new Rectangle(getDimension());//rectangle or size of panel    
            this.getPlot().setBounds(boundingRectangle);
        }
        if (xAxisTitle != null || !xAxisTitle.equals("")) {
            this.getPlot().setAxisLabel(0, xAxisTitle);//set the asix label
            this.getPlot().getAxis(0).setLabelPosition(0.5, -0.15);
           
        }
        if (yAxisTitle != null || !yAxisTitle.equals("")) {
            this.getPlot().setAxisLabel(1, yAxisTitle);//set the axis label
            this.getPlot().getAxis(1).setLabelPosition(-0.15, 0.5);
        }
        if (red != 0.0 & green != 0.0)//if there are red and green values provided
        { //plot red bars
          this.getPlot().getAxis(0).setLabelFont(new Font("BebasNeue", Font.BOLD, 30));
          this.getPlot().getAxis(0).setColor(AllConstants.TOM_LETTERS);
          this.getPlot().getAxis(0).setLightLabelColor(AllConstants.TOM_LETTERS);
          this.getPlot().getAxis(0).setLightLabelFont(new Font("BebasNeue", Font.PLAIN, 25));
            
          this.getPlot().getAxis(1).setColor(AllConstants.TOM_LETTERS);
          this.getPlot().getAxis(1).setLightLabelColor(AllConstants.TOM_LETTERS);
          this.getPlot().getAxis(1).setLightLabelFont(new Font("BebasNeue", Font.PLAIN, 25));
          // add a title
                BaseLabel titleBL = new BaseLabel(title, AllConstants.TOM_LETTERS, 0.5, 1.2);
                titleBL.setFont(new Font("BebasNeue", Font.BOLD, 45));
                plot.addPlotable(titleBL);          
           
            double[] redBarValues = findRedBarValues(yData, red);
            double[] yellowBarValues = findYellowBarValues(yData, red, green);
            double[] greenBarValues = findGreenBarValues(yData, green);
            double width = 0.75;
            HistogramPlot2D redPlot = new HistogramPlot2D(title, AllConstants.TOM_RED, Array.buildXY(xData, redBarValues), width);
            redPlot.setAlpha(1); 
            
            HistogramPlot2D yellowPlot = new HistogramPlot2D(title, AllConstants.TOM_YELLOW, Array.buildXY(xData, yellowBarValues), width);
            yellowPlot.setAlpha(1); 
            
            HistogramPlot2D greenPlot = new HistogramPlot2D(title, AllConstants.TOM_GREEN, Array.buildXY(xData, greenBarValues), width);
            greenPlot.setAlpha(1);
            
            List<Double> tempWeekList = new ArrayList<>();
            Double week0 = xData[0] - 1;//to generate week one less than given
            tempWeekList.add(week0);
            tempWeekList.addAll(Doubles.asList(xData));
            tempWeekList.add(xData[xData.length - 1] + 1);//get the last element, add it to list, so that the line is visible from one end to another
            double[] xTempLineData = Doubles.toArray(tempWeekList);//convert it back to array
            double[] yTmpLineDataRed = new double[xData.length + 2];
            Arrays.fill(yTmpLineDataRed, red);
            LinePlot redLinePlot = new LinePlot("", Color.RED, Array.buildXY(xTempLineData, yTmpLineDataRed));
            double[] yTmpLineDataYellow = new double[xData.length + 2];
            Arrays.fill(yTmpLineDataYellow, green);
            LinePlot yellowLinePlot = new LinePlot("", Color.GREEN, Array.buildXY(xTempLineData, yTmpLineDataYellow));
            double[] yTmpLineDataBlack = new double[xData.length + 2];
            Arrays.fill(yTmpLineDataBlack, 0);
            LinePlot blackLinePlot = new LinePlot("", Color.BLACK, Array.buildXY(xTempLineData, yTmpLineDataBlack));
            plot.addPlot(redLinePlot);
            plot.addPlot(yellowLinePlot);
            plot.addPlot(redPlot);
            plot.addPlot(yellowPlot);
            plot.addPlot(greenPlot);            
            plot.addPlot(blackLinePlot);
            plot.getAxis(0).setLightLabelText(getNewXAxisLabels(xData).toArray(new String[0]));            
            plot.plotCanvas.setBackground(AllConstants.TOM_BACKGROUND);  
            plot.setSize(560, 450);

        } else {//if no red or green specified, create a simple bar chart
            HistogramPlot2D plot2D = new HistogramPlot2D(title, barColor, xyData, 0.5);
            this.getPlot().addPlot(plot2D);
        }
    }

    /**
     * Method to setup data on the side of chart
     *
     * @param title
     * @param yData
     * @throws NumberFormatException
     */
    private void dataOnChartSide(String title, double[] yData) throws NumberFormatException {
        BaseLabel titleChart = new BaseLabel(title, Color.BLUE, 0.5, 1.1);
        titleChart.setFont(new Font("Courier", Font.BOLD, 18));
        plot.addPlotable(titleChart);
        StringBuffer numberOnRight = new StringBuffer(String.valueOf((int) (yData[yData.length - 1])));//the big number on right
        double lastvalue = yData[yData.length - 1];
        Color bigTxtColor = Color.GREEN;
        if (lastvalue <= red) {
            bigTxtColor = Color.RED;
        } else if (lastvalue > red && lastvalue <= green) {
            bigTxtColor = Color.YELLOW;
        }
        if (numberOnRight.length() == 7)//modify the data presentation
        {
            double amt = Float.parseFloat(numberOnRight.toString()) / 1000000;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1);
            numberOnRight = new StringBuffer(String.valueOf(df.format(amt)) + "M");
        } else if (numberOnRight.length() >= 5)//modify the data presentation
        {
            double amt = Float.parseFloat(numberOnRight.toString()) / 1000;
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1);
            numberOnRight = new StringBuffer(String.valueOf(df.format(amt)) + "K");
        }
        BaseLabel titleChart2 = new BaseLabel(numberOnRight.toString(), bigTxtColor, 1.1, 0.7);
        titleChart2.setFont(new Font("Arial Narrow", Font.BOLD, 48));//lable at the end of panel
        plot.addPlotable(titleChart2);
        BaseLabel titleChart3 = new BaseLabel("Total", Color.BLUE, 1.1, 0.5);
        titleChart3.setFont(new Font("Courier", Font.BOLD, 18));//lable at the end of panel
        plot.addPlotable(titleChart3);
        BaseLabel titleChart4 = new BaseLabel("Current", Color.BLUE, 1.1, 0.4);
        titleChart4.setFont(new Font("Courier", Font.BOLD, 18));//lable at the end of panel
        plot.addPlotable(titleChart4);
        BaseLabel titleChart5 = new BaseLabel("Week", Color.BLUE, 1.1, 0.3);
        titleChart5.setFont(new Font("Courier", Font.BOLD, 18));//lable at the end of panel
        plot.addPlotable(titleChart5);
    }

    /**
     * Method to create barPlot
     *
     * @param xData
     * @param yData
     * @param color
     * @param title
     */
    public void createBarPlot(double[] xData, double[] yData, Color color, String title) {
        double[][] xyData = Array.buildXY(xData, yData);
        this.getPlot().addBarPlot(title, xyData);
    }

    /**
     * Method to draw line on plot
     *
     * @param xData
     * @param yData
     * @param color
     * @param title
     */
    public void createLinePlot(double[] xData, double[] yData, Color color, String title) {
        double[][] xyData = Array.buildXY(xData, yData);
        this.getPlot().addLinePlot(title, color, yData);

    }

    /**
     * Method to find the places where red bar needs to be placed
     *
     * @param xData
     * @param yData
     * @param red
     * @return
     */
    public double[] findRedBarValues(double[] yData, double red) {
        double[] yDataRedBar = new double[yData.length];
        for (int i = 0; i < yData.length; i++) {
            double d = yData[i];
            if (d <= red) {
                yDataRedBar[i] = d;
            } else {
                yDataRedBar[i] = 0;
            }
        }
        return yDataRedBar;
    }

    /**
     * Method to find Yellow Bar Values
     *
     * @param yData
     * @param red
     * @param green
     * @return
     */
    public double[] findYellowBarValues(double[] yData, double red, double green) {
        double[] yDataYellowBar = new double[yData.length];
        for (int i = 0; i < yData.length; i++) {
            double d = yData[i];
            if (d > red && d <= green) {
                yDataYellowBar[i] = d;
            } else {
                yDataYellowBar[i] = 0;
            }
        }
        return yDataYellowBar;
    }

    /**
     * Method to find the places where red bar needs to be placed
     *
     * @param green
     * @param yData
     * @return
     */
    public double[] findGreenBarValues(double[] yData, double green) {
        double[] yDataGreenBar = new double[yData.length];
        for (int i = 0; i < yData.length; i++) {
            double d = yData[i];
            if (d > green) {
                yDataGreenBar[i] = d;
            } else {
                yDataGreenBar[i] = 0;
            }
        }
        return yDataGreenBar;
    }

    /**
     * @return the plot
     */
    public Plot2DPanel getPlot() {
        if (plot == null) {
            plot = new Plot2DPanel();
        }
        return plot;
    }

    /**
     * @param plot the plot to set
     */
    public void setPlot(Plot2DPanel plot) {
        this.plot = plot;
    }

    /**
     * @return the dimension
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * @param dimension the dimension to set
     */
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * @return the red
     */
    public double getRed() {
        return red;
    }

    /**
     * @param red the red to set
     */
    public void setRed(double red) {
        this.red = red;
    }

    /**
     * @return the green
     */
    public double getGreen() {
        return green;
    }

    /**
     * @param green the green to set
     */
    public void setGreen(double green) {
        this.green = green;
    }
    
    public List<String>  getNewXAxisLabels(double[] xAxisData)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd ");
Calendar cal = Calendar.getInstance();
        
cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
System.out.println(sdf.format(cal.getTime())); 
       List<String> xAxisLabels= new ArrayList<>();
       xAxisLabels.add("");
        for (int i = 0; i < xAxisData.length; i++) {
            double d = xAxisData[i];
            cal.set(Calendar.WEEK_OF_YEAR, (int)d);
           xAxisLabels.add(sdf.format(cal.getTime()));
        }
       xAxisLabels.add(""); 
        return xAxisLabels;
    }

}

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
        {
            //plot red bars
//            this.getPlot().getAxis(0).setLabelFont(new Font("BebasNeue", Font.BOLD, 30));
            this.getPlot().getAxis(0).setColor(AllConstants.TOM_LETTERS);
            this.getPlot().getAxis(0).setLightLabelColor(AllConstants.TOM_LETTERS);
            this.getPlot().getAxis(0).setLightLabelFont(new Font("BebasNeue", Font.PLAIN, 55));

            this.getPlot().getAxis(1).setColor(AllConstants.TOM_LETTERS);
            this.getPlot().getAxis(1).setLightLabelColor(AllConstants.TOM_LETTERS);
            this.getPlot().getAxis(1).setLightLabelFont(new Font("BebasNeue", Font.PLAIN, 55));
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
            LinePlot blackLinePlot = new LinePlot("", Color.GRAY, Array.buildXY(xTempLineData, yTmpLineDataBlack));
            plot.addPlot(redLinePlot);
            plot.addPlot(yellowLinePlot);
            plot.addPlot(redPlot);
            plot.addPlot(yellowPlot);
            plot.addPlot(greenPlot);
            plot.addPlot(blackLinePlot);
            plot.getAxis(0).setLightLabelText(getNewXAxisLabels(xData).toArray(new String[0]));
            System.out.println(Arrays.toString(yData));
//            plot.getAxis(1).setLightLabelText(getNewYAxisLabel(yData).toArray(new String[0]));
            plot.plotCanvas.setBackground(AllConstants.TOM_BACKGROUND);

        } else {//if no red or green specified, create a simple bar chart
            HistogramPlot2D plot2D = new HistogramPlot2D(title, barColor, xyData, 0.5);
            this.getPlot().addPlot(plot2D);
        }
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

    public List<String> getNewXAxisLabels(double[] xAxisData) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd ");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        List<String> xAxisLabels = new ArrayList<>();
        xAxisLabels.add("");
        for (int i = 0; i < xAxisData.length; i++) {
            double d = xAxisData[i];
            cal.set(Calendar.WEEK_OF_YEAR, (int) d);
            xAxisLabels.add(sdf.format(cal.getTime()));
        }
        xAxisLabels.add("");
        return xAxisLabels;
    }
    
    /**
     * Method to replace the yAxis label values with custom values
     * @param yData
     * @return 
     */
    public List<String> getNewYAxisLabel(double[] yData)
    {      
        List<String> yAxisLabels = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            double d = yData[i];
            StringBuffer val= new StringBuffer(String.valueOf((int)d));
            int length=val.length();
            if(length>=4)
            {
                val.replace(length-3, length, "K");
                System.out.println(val.toString());
            }            
                yAxisLabels.add(val.toString());
           
        }
        System.out.println(Arrays.toString(yData));
        System.out.println(yAxisLabels);
        return yAxisLabels;
    }
    
    /**
     * Method to replace the yAxis label values with custom values
     * @param yData
     * @return 
     */
    public List<String> getNewYAxisLabel(String[] yData)
    {      
        List<String> yAxisLabels = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            StringBuffer val= new StringBuffer(yData[i]);
            int length=val.length();
            if(length>=4)
            {
                val.replace(length-3, length, "K");
                System.out.println(val.toString());
            }            
                yAxisLabels.add(val.toString());
           
        }
        System.out.println(Arrays.toString(yData));
        System.out.println(yAxisLabels);
        return yAxisLabels;
    }

}

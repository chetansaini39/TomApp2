package com.tom.charting;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.tom.AppSharedData.AllConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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
            this.getPlot().getAxis(0).setLightLabelFont(new Font("BebasNeue", Font.PLAIN, 45));

            this.getPlot().getAxis(1).setColor(AllConstants.TOM_LETTERS);
            this.getPlot().getAxis(1).setLightLabelColor(AllConstants.TOM_LETTERS);
            this.getPlot().getAxis(1).setLightLabelFont(new Font("BebasNeue", Font.PLAIN, 45));
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
            System.out.println("original->" + Arrays.toString(plot.getAxis(1).getLinesSlicing()));
            plot.getAxis(0).setLightLabelText(getNewXAxisLabels(xData).toArray(new String[0]));
            List<String> yAxisLabel = getNewYAxisLabel(plot.getAxis(1).getLinesSlicing());//new labels for yaxis           
            plot.getAxis(1).setLightLabelText(yAxisLabel.toArray(new String[0]));
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
            if (d < red) {
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
            if (d >= red && d < green) {
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
            if (d >= green) {
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
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
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
     *
     * @param yData
     * @return
     */
    public List<String> getNewYAxisLabel(double[] yData) {
        boolean decSignificant = false;
        BigDecimal bigDecimal;
        DecimalFormat df = new DecimalFormat("#.#");
        List<String> yDataList = new ArrayList<>();
//        if (yData[1] % 1 != 0) {
        System.out.println("Contains Decimal places");
        yDataList.add(0, "0");
        for (int i = 1; i <= 5; i++) {
            //String sb= String.valueOf((int)Math.round(yData[i]));//round the value and store it
            String sb = df.format(yData[i]);//round the value and store it
            //System.out.println("Sending for rounding: " + sb);
            bigDecimal = new BigDecimal(yData[i]);
            //to allow that . is placed for only 4 digit numbers whose float part is significant.
            if (bigDecimal.remainder(BigDecimal.ONE).floatValue() > 0.2f && bigDecimal.remainder(BigDecimal.ONE).floatValue() < 0.8 || sb.length()==4) {
                decSignificant = true;
            }
            yDataList.add(formatMyData(sb, decSignificant));
        }
//        } else {
//            System.out.println("Does Not Contains Decimal places");
//            yDataList.add(0,"0");
//            for (int i = 1; i <= 5; i++) {
//                int val= new BigDecimal(difference*i).intValue();
//                yDataList.add(formatMyData(String.valueOf(val)));
//            }
//        }
        System.out.println("Array to #.## conversion-> " + yDataList);
        return yDataList;
    }

    public String formatMyData(String d, boolean decSignificant) {
        int removeFromLeft = 3;
        StringBuilder sb = new StringBuilder(d);

        if (d.length() >= 4 && d.length() <= 6) {
            if (decSignificant) {
                String secondVal = sb.substring(1, 2);
                System.out.println("second Val: " + secondVal);
                int valInt = Integer.parseInt(secondVal);
                if (valInt > 0) {
                    sb.insert(1, ".");
                    removeFromLeft = 2;
                }
            }
            sb.replace(sb.length() - removeFromLeft, sb.length(), "K");
        } else if (d.length() > 6) {
            sb.replace(sb.length() - 6, sb.length(), "M");
        }
        return sb.toString();
    }

    /**
     * Method to setup data on the side of chart
     *
     * @param title
     * @param yData
     */
    private void dataOnChartSide(String title, double[] yData) throws NumberFormatException {
        // TODO - implement ChartGenerator.dataOnChartSide
        throw new UnsupportedOperationException();
    }

}

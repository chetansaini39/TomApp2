/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tom.ui;

import com.github.lwhite1.tablesaw.api.Table;
import com.github.lwhite1.tablesaw.columns.Column;
import com.tom.AppSharedData.AllConstants;
import com.tom.AppSharedData.AppSharedData;
import com.tom.AppSharedData.ExcelKeys;
import com.tom.charting.ChartGenerator;
import com.tom.charting.TextPro1;
import com.tom.excelfileIO.ExcelFileIO;
import com.tom.mongoDB.MongoOps;
import com.tom.mongoDB.MongoServerInstance;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.bson.Document;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author csaini
 */
public class ExecuteCommands {

    public Set<Integer> weekNumbersSet = new HashSet<>();
    public Set<String> weekNumbersStringSet = new HashSet<>();
    MyTable myTable = new MyTable("DataTable");

    /**
     * Method to process the selected excel file. The user enters the week no
     * for which data is selected from the File and stored
     */
    public void processExcelFile() {
        Runnable thread = () -> {
            if (AppSharedData.excelFile != null) {
                System.out.println("Processing file-> " + AppSharedData.excelFile.getName());
                String weekNo = JOptionPane.showInputDialog("Enter a Week#  for which data needs to be collected ");
                String weekNos[] = weekNo.split(",");
                System.out.println("WeekNos-> " + weekNos);
                for (String weekNo1 : weekNos) {
                    try {
                        Integer tmp_weekNo = Integer.parseInt(weekNo1.replaceAll("[^0-9]", ""));
                        weekNumbersSet.add(tmp_weekNo);//store the week no to set
                        String tmp_weekNoString = AllConstants.weekPrefix + String.valueOf(tmp_weekNo);
                        weekNumbersStringSet.add(tmp_weekNoString);//store sting to set
                        System.out.println("Processing Week# " + tmp_weekNo);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Please Enter Week# correctly, only digits allowed, seperated by comma");
                    }
                }
                ExcelFileIO excelFileOperations = new ExcelFileIO(AppSharedData.excelFile);
                ArrayList<ArrayList<String>> excelData = excelFileOperations.getExcelData(weekNumbersStringSet);//get the data from excel in form of arrayList
                MongoOps mongoOps = new MongoOps(MongoServerInstance.getInstance());
                ArrayList<Document> documentList = mongoOps.createDocument(excelData, weekNumbersSet, weekNumbersStringSet);
                mongoOps.InsertOrUpdate(AppSharedData.collectionName, documentList);
            } else {
                System.out.println("File not present");
                JOptionPane.showMessageDialog(null, "Please Upload The Dashboard Excel File");
            }
        };
        Thread t = new Thread(thread);
        t.start();
    }

    /**
     * Method to display charts. The method returns generated charts list.
     *
     * @param dimension
     * @return
     */
    public List<JPanel> createCharts(Dimension dimension) {
        List<JPanel> completeChartsList = new ArrayList<>();
        List<Plot2DPanel> plot2DPanelsList = new ArrayList<>();
        JPanel completeChartPanel;
        TextPro1 pro1;
        double green, red;
        String title = "";
        String xAxisTitle = "";
        String yAxisTitle = "";
        Plot2DPanel plot2DPanel;
        Table db_dataTable = myTable.getTable();
        ChartGenerator chartGenerator;
        List<Column> arrayList = db_dataTable.columns();
        double[] xData = db_dataTable.column(1).last(4).toDoubleArray();//get the col with all weeks
        System.out.println("Weeks " + Arrays.toString(xData));
        double[] yData;
        for (Column col : arrayList) {
            switch (col.name()) {
                case ExcelKeys.webDrivenRAQ:
                    chartGenerator = new ChartGenerator();
                    chartGenerator.setDimension(dimension);
                    green = AppSharedData.marketing_rules_green[0];
                    red = AppSharedData.marketing_rules_red[0];
                    plot2DPanel = new Plot2DPanel();
                    plot2DPanel.setSize(dimension);
                    plot2DPanel.setMinimumSize(dimension);
                    plot2DPanel.setMaximumSize(dimension);
                    chartGenerator.setPlot(plot2DPanel);
                    yData = col.last(4).toDoubleArray();
                    title = "Web Driven RAQ’s per Week";
                    xAxisTitle = "Week #";
                    chartGenerator.setGreen(green);//set the green val
                    chartGenerator.setRed(red);//set the red val
                    chartGenerator.createHistogramChart(xData, yData, Color.darkGray, xAxisTitle, yAxisTitle, title);
                    plot2DPanelsList.add(chartGenerator.getPlot());
                    createCompleteChart(yData, red, green, plot2DPanel, completeChartsList);
                    break;
                case ExcelKeys.quotations:

                    chartGenerator = new ChartGenerator();
                    chartGenerator.setDimension(dimension);
                    green = AppSharedData.sales_rules_green[3];
                    red = AppSharedData.sales_rules_red[3];
                    plot2DPanel = new Plot2DPanel();
                    plot2DPanel.setSize(dimension);
                    plot2DPanel.setMinimumSize(dimension);
                    plot2DPanel.setMaximumSize(dimension);
                    chartGenerator.setPlot(plot2DPanel);
                    yData = col.last(4).toDoubleArray();
                    title = "Quotations";
                    xAxisTitle = "Week No";
                    chartGenerator.setGreen(green);//set the green val
                    chartGenerator.setRed(red);//set the red val
                    chartGenerator.createHistogramChart(xData, yData, Color.darkGray, xAxisTitle, yAxisTitle, title);
                    plot2DPanelsList.add(chartGenerator.getPlot());
                    createCompleteChart(yData, red, green, plot2DPanel, completeChartsList);
                    break;
                case ExcelKeys.pipelineValue:

                    chartGenerator = new ChartGenerator();
                    chartGenerator.setDimension(dimension);
                    green = AppSharedData.sales_rules_green[4];
                    red = AppSharedData.sales_rules_red[4];
                    plot2DPanel = new Plot2DPanel();
                    plot2DPanel.setSize(dimension);
                    plot2DPanel.setMinimumSize(dimension);
                    plot2DPanel.setMaximumSize(dimension);
                    chartGenerator.setPlot(plot2DPanel);
                    yData = col.last(4).toDoubleArray();
                    title = "Pipeline Value";
                    xAxisTitle = "Week No";
                    chartGenerator.setGreen(green);//set the green val
                    chartGenerator.setRed(red);//set the red val
                    chartGenerator.createHistogramChart(xData, yData, Color.darkGray, xAxisTitle, yAxisTitle, title);
                    plot2DPanelsList.add(chartGenerator.getPlot());
                    createCompleteChart(yData, red, green, plot2DPanel, completeChartsList);
                    break;
                case ExcelKeys.f2fMeetings:
                    chartGenerator = new ChartGenerator();
                    chartGenerator.setDimension(dimension);
                    green = AppSharedData.sales_rules_green[1];
                    red = AppSharedData.sales_rules_red[1];
                    plot2DPanel = new Plot2DPanel();
                    plot2DPanel.setSize(dimension);
                    plot2DPanel.setMinimumSize(dimension);
                    plot2DPanel.setMaximumSize(dimension);
                    chartGenerator.setPlot(plot2DPanel);
                    yData = col.last(4).toDoubleArray();
                    title = "Face-to-Face Meetings";
                    xAxisTitle = "Week No";
                    chartGenerator.setGreen(green);//set the green val
                    chartGenerator.setRed(red);//set the red val
                    chartGenerator.createHistogramChart(xData, yData, Color.darkGray, xAxisTitle, yAxisTitle, title);
                    plot2DPanelsList.add(chartGenerator.getPlot());
                    createCompleteChart(yData, red, green, plot2DPanel, completeChartsList);
                    break;
                case ExcelKeys.bookings:
                    chartGenerator = new ChartGenerator();
                    chartGenerator.setDimension(dimension);
                    green = AppSharedData.sales_rules_green[5];
                    red = AppSharedData.sales_rules_red[5];
                    plot2DPanel = new Plot2DPanel();
                    plot2DPanel.setSize(dimension);
                    plot2DPanel.setMinimumSize(dimension);
                    plot2DPanel.setMaximumSize(dimension);
                    chartGenerator.setPlot(plot2DPanel);
                    yData = col.last(4).toDoubleArray();
                    title = "Bookings";
                    xAxisTitle = "Week #";
                    chartGenerator.setGreen(green);//set the green val
                    chartGenerator.setRed(red);//set the red val
                    chartGenerator.createHistogramChart(xData, yData, Color.darkGray, xAxisTitle, yAxisTitle, title);
                    plot2DPanelsList.add(chartGenerator.getPlot());
                    createCompleteChart(yData, red, green, plot2DPanel, completeChartsList);
                    break;
                case ExcelKeys.sales:
                    chartGenerator = new ChartGenerator();
                    chartGenerator.setDimension(dimension);
                    green = AppSharedData.sales_rules_green[6];
                    red = AppSharedData.sales_rules_red[6];
                    plot2DPanel = new Plot2DPanel();
                    plot2DPanel.setSize(dimension);
                    plot2DPanel.setMinimumSize(dimension);
                    plot2DPanel.setMaximumSize(dimension);
                    chartGenerator.setPlot(plot2DPanel);
                    yData = col.last(4).toDoubleArray();
                    title = "Sales";
                    xAxisTitle = "Week #";
                    chartGenerator.setGreen(green);//set the green val
                    chartGenerator.setRed(red);//set the red val
                    chartGenerator.createHistogramChart(xData, yData, Color.darkGray, xAxisTitle, yAxisTitle, title);
                    plot2DPanelsList.add(chartGenerator.getPlot());
                    createCompleteChart(yData, red, green, plot2DPanel, completeChartsList);
                    break;
                case ExcelKeys.partnershipProgress:
                    chartGenerator = new ChartGenerator();
                    chartGenerator.setDimension(dimension);
                    green = AppSharedData.sales_rules_green[7];
                    red = AppSharedData.sales_rules_red[7];
                    plot2DPanel = new Plot2DPanel();
                    plot2DPanel.setSize(dimension);
                    plot2DPanel.setMinimumSize(dimension);
                    plot2DPanel.setMaximumSize(dimension);
                    chartGenerator.setPlot(plot2DPanel);
                    yData = col.last(4).toDoubleArray();
                    title = "Partnership Progress (subjective)";
                    xAxisTitle = "Week #";
                    chartGenerator.setGreen(green);//set the green val
                    chartGenerator.setRed(red);//set the red val
                    chartGenerator.createHistogramChart(xData, yData, Color.darkGray, xAxisTitle, yAxisTitle, title);
                    chartGenerator.getPlot().setEditable(false);
                    plot2DPanelsList.add(chartGenerator.getPlot());
                    createCompleteChart(yData, red, green, plot2DPanel, completeChartsList);
                    break;
            }
        }
        return completeChartsList;
    }

    private void createCompleteChart(double[] yData, double red, double green, Plot2DPanel plot2DPanel, List<JPanel> completeChartsList) throws NumberFormatException {
        JPanel completeChartPanel;
        TextPro1 pro1;
        completeChartPanel = new JPanel();
        completeChartPanel.setLayout(new GridBagLayout());
        completeChartPanel.setSize(957, 460);
        pro1 = createSidePanel(yData, red, green);
        pro1.setMaximumSize(new Dimension(414, 460));
        pro1.setMinimumSize(new Dimension(414, 460));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = .90;
        c.weighty = .90;
        c.ipady = 40;
        completeChartPanel.add(plot2DPanel.plotCanvas, c);//item on left
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = .10;
        c.weighty = .10;
        completeChartPanel.add(pro1, c);//item on right
        completeChartsList.add(completeChartPanel);
    }

    /**
     * Method to create the side panel for the charts
     *
     * @param yData
     * @param red
     * @param green
     * @return
     * @throws NumberFormatException
     */
    private TextPro1 createSidePanel(double[] yData, double red, double green) throws NumberFormatException {
        //find the color of the bigNumberText
        Color bigNumberColor = AllConstants.TOM_GREEN;
        double lastElementOfData = yData[yData.length - 1];//last element of ydata
        if (lastElementOfData <= red) {
            bigNumberColor = AllConstants.TOM_RED;
        } else if (lastElementOfData > red && lastElementOfData <= green) {
            bigNumberColor = AllConstants.TOM_YELLOW;
        }
        //                    JPanel textPanel = new JPanel();//panel to hold right side chart
        StringBuffer numberOnRight = new StringBuffer(String.valueOf((int) (lastElementOfData)));//the big number on right                          
        if (numberOnRight.length() >= 6)//modify the data presentation
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
        TextPro1 pro1 = new TextPro1(numberOnRight.toString(), bigNumberColor);
        pro1.getTextPanel(pro1);//get the drawing
        return pro1;
    }

    /**
     * Method to check if Mongo DB server is running, if the server is not
     * running then run the server.
     */
    public void checkMongoDB() {
        if (!AppSharedData.MongoRunning) {
            MongoServerInstance.getInstance();
        } else {
            JOptionPane.showMessageDialog(null, "DB Server already running!!");
        }
    }

    /**
     * Method to return a filled JTable, it can be added to Panel
     *
     * @return
     */
    public JTable showDataTable() {
        Table db_dataTable = myTable.getTable();
        System.out.println(db_dataTable.print());
        JTable jTable = new JTable(new CustomTableModel(db_dataTable));
        return jTable;
    }

    /**
     * Method to upload the Font file
     */
    public void uploadFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, StartUI.class.getResourceAsStream("/BebasNeue.otf"))); //new File("/BebasNeue.otf")
            System.out.println("Font file loaded succesfully");
        } catch (FontFormatException | IOException ex) {
            System.out.println(ex);
        }
    }
}

class CustomTableModel extends AbstractTableModel {

    Table table;

    public CustomTableModel(Table table) {
        this.table = table;
    }

    @Override
    public int getRowCount() {
        return table.rowCount();
    }

    @Override
    public int getColumnCount() {
        return table.columnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return table.get(columnIndex, rowIndex);
    }

    @Override
    public String getColumnName(int column) {
        return table.columnNames().get(column);
    }

}

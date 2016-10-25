package com.tom.AppSharedData;

import com.github.lwhite1.tablesaw.api.Table;
import java.io.File;
import java.util.ArrayList;

/**
 * This class contains data that will be shared and used across the application.
 */
public class AppSharedData {

    public static File excelFile = null;
    public static Table table = null;
    public static String collectionName = "dashboard";//Mongo DB Table Name
    public static boolean MongoRunning=false;
    public static String serverHost="10.47.10.27";//IP address where the Mongo Server is running
    public static int port=27017;
     public static final double marketing_rules_green[]={10,550,.50,425,3.30,4000,950,4,20};
    public static final double marketing_rules_red[]={5,450,.35,350,2.45,3500,800,2,5};
    public static final double sales_rules_green[]={40,20,50,20,6000000,300000,300000,7};
    public static final double sales_rules_red[]={20,10,25,10,4000000,200000,200000,4};
    public static int CHART_PANEL_Y= 450;


}

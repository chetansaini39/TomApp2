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
    public static boolean MongoRunning = false;
    public static String serverHost = "localhost";//IP address where the Mongo Server is running
    public static int port = 27017;
    
    public static int CHART_PANEL_Y = 450;
    public static final double marketing_rules_green[] = {
                                                            10,//webRaq
                                                            550,//newVisitorsperweek
                                                            .50,//%newVisit
                                                            425,//returningVisitsperWeek
                                                            3.30,//Average Time per session
                                                            4000,//Webpages Viewed per Week
                                                            950,//Total Sessions per Week
                                                            4,//Ave Page Count per Sessions
                                                            20//Trade Show Leads
                                                        };
    public static final double marketing_rules_red[] = {5,//webRaq 
                                                        450,// newVisitorsperweek
                                                        .35, //%newVisit
                                                        350, //returningVisitsperWeek
                                                        2.45,// Average Time per session
                                                        3500,// Webpages Viewed per Week
                                                        800,// Total Sessions per Week
                                                        2,// Ave Page Count per Sessions
                                                        10//Trade Show Leads
                                                    };//newprospect,f2f,networkmining,quotation,pipeline,booking,sakes,pp
    
    public static final double sales_rules_green[] = {50, //New Prospects Contacted
                                                        25, //Face-to-Face Meetings
                                                        40, //Network Mining
                                                        25, //Quotations
                                                        20000000, //Pipeline Value
                                                        260000, //Bookings
                                                        260000,//Sales
                                                        7//Partnership Progress (subjective)
                                                    };
    public static final double sales_rules_red[] = {25,//New Prospects Contacted
                                                    15,//Face-to-Face Meetings
                                                    25,//Network Mining
                                                    15,//Quotations
                                                    16000000,//Pipeline Value
                                                    200000,//Bookings
                                                    200000,//Sales
                                                    4};//Partnership Progress (subjective)

}

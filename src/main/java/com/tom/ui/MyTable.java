package com.tom.ui;

import com.github.lwhite1.tablesaw.api.CategoryColumn;
import com.github.lwhite1.tablesaw.api.FloatColumn;
import com.github.lwhite1.tablesaw.api.Table;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.tom.AppSharedData.AppSharedData;
import com.tom.AppSharedData.ExcelKeys;
import com.tom.excelfileIO.ExcelData;
import com.tom.mongoDB.MongoServerInstance;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;

public class MyTable {

	private String tableName;
	private Table table;

	public String getTableName() {
		return this.tableName;
	}

	/**
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Table createEmptyTable() {
	Table table = Table.create(AppSharedData.collectionName);
        CategoryColumn column = CategoryColumn.create("ID");
        FloatColumn col_weekNo = FloatColumn.create(ExcelKeys.WEEK_NO);
        FloatColumn col_webDrivenRAQ = FloatColumn.create(ExcelKeys.webDrivenRAQ);
        FloatColumn col_newVisitorsPerWeek = FloatColumn.create(ExcelKeys.newVisitorsPerWeek);
        FloatColumn col_pcntNewVisitorsPerWeek = FloatColumn.create(ExcelKeys.pcntNewVisitorsPerWeek);
        FloatColumn col_returningVisitorsPerWeek = FloatColumn.create(ExcelKeys.returningVisitorsPerWeek);
        FloatColumn col_avgTimePerSession = FloatColumn.create(ExcelKeys.avgTimePerSession);
        FloatColumn col_webpagesViewedPerWeek = FloatColumn.create(ExcelKeys.webpagesViewedPerWeek);
        FloatColumn col_totalSessionPerWeek = FloatColumn.create(ExcelKeys.totalSessionPerWeek);
        FloatColumn col_avgPageCountPerSession = FloatColumn.create(ExcelKeys.avgPageCountPerSession);
        CategoryColumn col_tradeShowsPerWeek = CategoryColumn.create(ExcelKeys.tradeShowsPerWeek);
        FloatColumn col_tradeShowLeads = FloatColumn.create(ExcelKeys.tradeShowLeads);
        FloatColumn col_newProspectsContacted = FloatColumn.create(ExcelKeys.newProspectsContacted);
        FloatColumn col_f2fMeetings = FloatColumn.create(ExcelKeys.f2fMeetings);
        FloatColumn col_networkMining = FloatColumn.create(ExcelKeys.networkMining);
        FloatColumn col_quotations = FloatColumn.create(ExcelKeys.quotations);
        FloatColumn col_pipelineValue = FloatColumn.create(ExcelKeys.pipelineValue);
        FloatColumn col_bookings = FloatColumn.create(ExcelKeys.bookings);
        FloatColumn col_sales = FloatColumn.create(ExcelKeys.sales);
        FloatColumn col_partnershipProgress = FloatColumn.create(ExcelKeys.partnershipProgress);
        table.addColumn(column, col_weekNo, col_webDrivenRAQ, col_newVisitorsPerWeek, col_pcntNewVisitorsPerWeek, col_returningVisitorsPerWeek, col_avgTimePerSession,
               col_webpagesViewedPerWeek, col_totalSessionPerWeek, col_avgPageCountPerSession, col_tradeShowsPerWeek, col_tradeShowLeads, col_newProspectsContacted,
                col_f2fMeetings, col_networkMining, col_quotations, col_pipelineValue, col_bookings, col_sales, col_partnershipProgress);
        return table;
	}
/**
 * Method to get the table. If the table is not initialized then it is initialized and 
 * @return 
 */
	public Table getTable() {
           
            this.table=createTableFromMongoData(MongoServerInstance.getInstance());    
            return this.table.sortAscendingOn(ExcelKeys.WEEK_NO);
	}

	/**
	 * 
	 * @param table
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * 
	 * @param tableName
	 */
	public MyTable(String tableName) {
            this.tableName= tableName;
	}
        
        /**
     * Method to convert the data from DB into JSOn and then store it into POJO
     * into arrayList. The list will contain all the records
     * @param emptyTable
     */
    public  Table createTableFromMongoData(MongoDatabase mongoDatabase) {
        Table emptyTable= createEmptyTable();
        System.out.println("ColWidth-> "+Arrays.toString(emptyTable.colWidths()));
        System.out.println("createTableFromMongoData");
        if (AppSharedData.MongoRunning) {
            try {
                ArrayList<ExcelData> arrayListJson = new ArrayList<>();
                FindIterable<Document> iterable = mongoDatabase.getCollection(AppSharedData.collectionName).find();
                Gson gs = new Gson();
                iterable.forEach(new Block<Document>() {
                    @Override
                    public void apply(final Document document) {
                        ExcelData ed = gs.fromJson(document.toJson(), ExcelData.class);
                        arrayListJson.add(ed);
                    }
                });
                System.out.println("Elements in Array: " + arrayListJson.size());               
                for (ExcelData data : arrayListJson) {
                   emptyTable.column(0).addCell(data.getId());
                    emptyTable.column(1).addCell(String.valueOf(data.getWEEK_NO()));//int
                    emptyTable.column(2).addCell(data.getWebDrivenRAQ());
                    emptyTable.column(3).addCell(data.getNewVisitorsPerWeek());
                    emptyTable.column(4).addCell(data.getPcntNewVisitorsPerWeek());
                    emptyTable.column(5).addCell(data.getReturningVisitorsPerWeek());
                    emptyTable.column(6).addCell(data.getAvgTimePerSession());
                    emptyTable.column(7).addCell(data.getWebpagesViewedPerWeek());
                    emptyTable.column(8).addCell(data.getTotalSessionPerWeek());
                    emptyTable.column(9).addCell(data.getAvgPageCountPerSession());
                    emptyTable.column(10).addCell(data.getTradeShowsPerWeek()); //since this is string value
                    emptyTable.column(11).addCell(data.getTradeShowLeads());
                    emptyTable.column(12).addCell(data.getNewProspectsContacted());
                    emptyTable.column(13).addCell(data.getF2fMeetings());
                    emptyTable.column(14).addCell(data.getNetworkMining());
                    emptyTable.column(15).addCell(data.getQuotations());
                    emptyTable.column(16).addCell(data.getPipelineValue());//Int
                    emptyTable.column(17).addCell(data.getBookings());
                    emptyTable.column(18).addCell(data.getSales());
                    emptyTable.column(19).addCell(data.getPartnershipProgress());
                }
                System.out.println("Table Created \n" + emptyTable.print());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        this.table=emptyTable;
        return emptyTable;
    }

}
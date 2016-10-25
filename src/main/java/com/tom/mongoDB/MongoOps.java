package com.tom.mongoDB;

import com.github.lwhite1.tablesaw.api.Table;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.tom.AppSharedData.AppSharedData;
import com.tom.AppSharedData.ExcelKeys;
import com.tom.excelfileIO.ExcelData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import org.bson.Document;

public class MongoOps {

    private MongoDatabase mongoDB;

    public MongoOps(MongoDatabase mongoDB) {
        this.mongoDB = mongoDB;
    }

    /**
     *
     * @param mongoDB
     * @param collectionName
     * @param document
     */
    public void InsertOrUpdate(String collectionName, ArrayList<Document> document) {
        if (mongoDB != null && collectionName != null && document != null) {
            for (Document document1 : document) {
                if (findIfWeekInDB(document1.getInteger(ExcelKeys.WEEK_NO))) {
                    mongoDB.getCollection(collectionName).replaceOne(new Document(ExcelKeys.WEEK_NO, document1.getInteger(ExcelKeys.WEEK_NO)), document1);
                    System.out.println("Update Done");
                } else {
                    mongoDB.getCollection(collectionName).insertOne(document1);
                }
            }
            JOptionPane.showMessageDialog(null, "Record(s) " + document.size() + " updated/inserted into DB");
        } else if (mongoDB == null) {
            System.out.println("** Mongo DB Server Not Running \n, Starting the Server now....");
            MongoServerInstance.getInstance();
        } else if (collectionName == null) {
            JOptionPane.showMessageDialog(null, "Table not found or null");
        } else if (document == null) {
            JOptionPane.showMessageDialog(null, "Document has no data");
        }
    }

    /**
     * Method to find if the given week number is already present in DB
     *
     * @param week
     * @return
     */
    public boolean findIfWeekInDB(int week) {
        boolean result = false;
        if (AppSharedData.MongoRunning) {
            FindIterable<Document> iterable = mongoDB.getCollection(AppSharedData.collectionName).find(Filters.eq(ExcelKeys.WEEK_NO, week));
            if (iterable.iterator().hasNext()) {
                result = true;
                System.out.println("DB already contains the data related to Week: " + week + "\n old data will be replaced by new data");
            }
        }
        return result;
    }

    /**
     * Method that adds the data from excel array to document. this document can
     * be added to mongo db. This is the table structure
     */
    public ArrayList<Document> createDocument(ArrayList<ArrayList<String>> excelData, Set<Integer> weekNumbersSet, Set<String> weekNumStringSet) {
        Iterator<Integer> weekNumIterator = weekNumbersSet.iterator();
        Iterator<String> weekNumStringIterator = weekNumStringSet.iterator();
        ArrayList<Document> documentList = new ArrayList<>();

        for (ArrayList<String> excelDataList : excelData) {
            System.out.println("-> " + excelDataList);
            String weekNumString = weekNumStringIterator.next();
            Document document = new Document();
            document.append("_id", Calendar.getInstance().get(Calendar.YEAR) + weekNumString);//string
            document.append(ExcelKeys.WEEK_NO, weekNumIterator.next());//int value
            document.append(ExcelKeys.webDrivenRAQ, excelDataList.get(0));
            document.append(ExcelKeys.newVisitorsPerWeek, excelDataList.get(1));
            document.append(ExcelKeys.pcntNewVisitorsPerWeek, excelDataList.get(2));
            document.append(ExcelKeys.returningVisitorsPerWeek, excelDataList.get(3));
            document.append(ExcelKeys.avgTimePerSession, excelDataList.get(4));
            document.append(ExcelKeys.webpagesViewedPerWeek, excelDataList.get(5));
            document.append(ExcelKeys.totalSessionPerWeek, excelDataList.get(6));
            document.append(ExcelKeys.avgPageCountPerSession, excelDataList.get(7));
            document.append(ExcelKeys.tradeShowsPerWeek, excelDataList.get(8));
            document.append(ExcelKeys.tradeShowLeads, excelDataList.get(9));
            document.append(ExcelKeys.newProspectsContacted, excelDataList.get(11));
            document.append(ExcelKeys.f2fMeetings, excelDataList.get(12));
            document.append(ExcelKeys.networkMining, excelDataList.get(13));
            document.append(ExcelKeys.quotations, excelDataList.get(14));
            document.append(ExcelKeys.pipelineValue, excelDataList.get(15));
            document.append(ExcelKeys.bookings, excelDataList.get(16));
            document.append(ExcelKeys.sales, excelDataList.get(17));
            document.append(ExcelKeys.partnershipProgress, excelDataList.get(18));
            System.out.println("Creating Document with ID: " + Calendar.getInstance().get(Calendar.YEAR) + weekNumString);
            documentList.add(document);
        }
        return documentList;
    }

 

}

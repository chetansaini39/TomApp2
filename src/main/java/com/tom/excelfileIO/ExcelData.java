/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tom.excelfileIO;

/**
 *
 * @author csaini
 */
public class ExcelData {
    private String _id;
    private int WEEK_NO;
    private String webDrivenRAQ;
    private String newVisitorsPerWeek;
    private String pcntNewVisitorsPerWeek;
    private String returningVisitorsPerWeek;
    private String avgTimePerSession;
    private String webpagesViewedPerWeek;
    private String totalSessionPerWeek;
    private String avgPageCountPerSession;
    private String tradeShowsPerWeek;
    private String tradeShowLeads;
    private String newProspectsContacted;
    private String f2fMeetings;
    private String networkMining;
    private String quotations;
    private String pipelineValue;
    private String bookings;
    private String sales;
    private String partnershipProgress;

   

    @Override
    public String toString() {
        return getWEEK_NO()+";"
                +getWebDrivenRAQ()+";"
                +getNewVisitorsPerWeek()+";"
                +getPcntNewVisitorsPerWeek()+";"
                +getReturningVisitorsPerWeek()+";"
                +getAvgTimePerSession()+";"
                +getWebpagesViewedPerWeek()+";"
                +getTotalSessionPerWeek()+";"
                +getAvgPageCountPerSession()+";"
                +getTradeShowsPerWeek()+";"
                +getTradeShowLeads()+";"
                +getNewProspectsContacted()+";"
                +getF2fMeetings()+";"
                +getNetworkMining()+";"
                +getQuotations()+";"
                +getPipelineValue()+";"
                +getBookings()+";"
                +getSales()+";"
                +getPartnershipProgress(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the _id
     */
    public String getId() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void setId(String _id) {
        this._id = _id;
    }

    /**
     * @return the WEEK_NO
     */
    public int getWEEK_NO() {
        return WEEK_NO;
    }

    /**
     * @param WEEK_NO the WEEK_NO to set
     */
    public void setWEEK_NO(int WEEK_NO) {
        this.WEEK_NO = WEEK_NO;
    }

    /**
     * @return the webDrivenRAQ
     */
    public String getWebDrivenRAQ() {
        return webDrivenRAQ;
    }

    /**
     * @param webDrivenRAQ the webDrivenRAQ to set
     */
    public void setWebDrivenRAQ(String webDrivenRAQ) {
        this.webDrivenRAQ = webDrivenRAQ;
    }

    /**
     * @return the newVisitorsPerWeek
     */
    public String getNewVisitorsPerWeek() {
        return newVisitorsPerWeek;
    }

    /**
     * @param newVisitorsPerWeek the newVisitorsPerWeek to set
     */
    public void setNewVisitorsPerWeek(String newVisitorsPerWeek) {
        this.newVisitorsPerWeek = newVisitorsPerWeek;
    }

    /**
     * @return the pcntNewVisitorsPerWeek
     */
    public String getPcntNewVisitorsPerWeek() {
        return pcntNewVisitorsPerWeek;
    }

    /**
     * @param pcntNewVisitorsPerWeek the pcntNewVisitorsPerWeek to set
     */
    public void setPcntNewVisitorsPerWeek(String pcntNewVisitorsPerWeek) {
        this.pcntNewVisitorsPerWeek = pcntNewVisitorsPerWeek;
    }

    /**
     * @return the returningVisitorsPerWeek
     */
    public String getReturningVisitorsPerWeek() {
        return returningVisitorsPerWeek;
    }

    /**
     * @param returningVisitorsPerWeek the returningVisitorsPerWeek to set
     */
    public void setReturningVisitorsPerWeek(String returningVisitorsPerWeek) {
        this.returningVisitorsPerWeek = returningVisitorsPerWeek;
    }

    /**
     * @return the avgTimePerSession
     */
    public String getAvgTimePerSession() {
        return avgTimePerSession;
    }

    /**
     * @param avgTimePerSession the avgTimePerSession to set
     */
    public void setAvgTimePerSession(String avgTimePerSession) {
        this.avgTimePerSession = avgTimePerSession;
    }

    /**
     * @return the webpagesViewedPerWeek
     */
    public String getWebpagesViewedPerWeek() {
        return webpagesViewedPerWeek;
    }

    /**
     * @param webpagesViewedPerWeek the webpagesViewedPerWeek to set
     */
    public void setWebpagesViewedPerWeek(String webpagesViewedPerWeek) {
        this.webpagesViewedPerWeek = webpagesViewedPerWeek;
    }

    /**
     * @return the totalSessionPerWeek
     */
    public String getTotalSessionPerWeek() {
        return totalSessionPerWeek;
    }

    /**
     * @param totalSessionPerWeek the totalSessionPerWeek to set
     */
    public void setTotalSessionPerWeek(String totalSessionPerWeek) {
        this.totalSessionPerWeek = totalSessionPerWeek;
    }

    /**
     * @return the avgPageCountPerSession
     */
    public String getAvgPageCountPerSession() {
        return avgPageCountPerSession;
    }

    /**
     * @param avgPageCountPerSession the avgPageCountPerSession to set
     */
    public void setAvgPageCountPerSession(String avgPageCountPerSession) {
        this.avgPageCountPerSession = avgPageCountPerSession;
    }

    /**
     * @return the tradeShowsPerWeek
     */
    public String getTradeShowsPerWeek() {
        return tradeShowsPerWeek;
    }

    /**
     * @param tradeShowsPerWeek the tradeShowsPerWeek to set
     */
    public void setTradeShowsPerWeek(String tradeShowsPerWeek) {
        this.tradeShowsPerWeek = tradeShowsPerWeek;
    }

    /**
     * @return the tradeShowLeads
     */
    public String getTradeShowLeads() {
        return tradeShowLeads;
    }

    /**
     * @param tradeShowLeads the tradeShowLeads to set
     */
    public void setTradeShowLeads(String tradeShowLeads) {
        this.tradeShowLeads = tradeShowLeads;
    }

    /**
     * @return the newProspectsContacted
     */
    public String getNewProspectsContacted() {
        return newProspectsContacted;
    }

    /**
     * @param newProspectsContacted the newProspectsContacted to set
     */
    public void setNewProspectsContacted(String newProspectsContacted) {
        this.newProspectsContacted = newProspectsContacted;
    }

    /**
     * @return the f2fMeetings
     */
    public String getF2fMeetings() {
        return f2fMeetings;
    }

    /**
     * @param f2fMeetings the f2fMeetings to set
     */
    public void setF2fMeetings(String f2fMeetings) {
        this.f2fMeetings = f2fMeetings;
    }

    /**
     * @return the networkMining
     */
    public String getNetworkMining() {
        return networkMining;
    }

    /**
     * @param networkMining the networkMining to set
     */
    public void setNetworkMining(String networkMining) {
        this.networkMining = networkMining;
    }

    /**
     * @return the quotations
     */
    public String getQuotations() {
        return quotations;
    }

    /**
     * @param quotations the quotations to set
     */
    public void setQuotations(String quotations) {
        this.quotations = quotations;
    }

    /**
     * @return the pipelineValue
     */
    public String getPipelineValue() {
        return pipelineValue;
    }

    /**
     * @param pipelineValue the pipelineValue to set
     */
    public void setPipelineValue(String pipelineValue) {
        this.pipelineValue = pipelineValue;
    }

    /**
     * @return the bookings
     */
    public String getBookings() {
        return bookings;
    }

    /**
     * @param bookings the bookings to set
     */
    public void setBookings(String bookings) {
        this.bookings = bookings;
    }

    /**
     * @return the sales
     */
    public String getSales() {
        return sales;
    }

    /**
     * @param sales the sales to set
     */
    public void setSales(String sales) {
        this.sales = sales;
    }

    /**
     * @return the partnershipProgress
     */
    public String getPartnershipProgress() {
        return partnershipProgress;
    }

    /**
     * @param partnershipProgress the partnershipProgress to set
     */
    public void setPartnershipProgress(String partnershipProgress) {
        this.partnershipProgress = partnershipProgress;
    }
    
    
}

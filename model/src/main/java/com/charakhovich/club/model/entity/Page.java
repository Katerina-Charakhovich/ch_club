package com.charakhovich.club.model.entity;

public class Page {

    private int pageNumber;
    private int recordNumber;
    public final static int RECORD_NUMBER = 5;

    public Page(int pageNumber,int recordNumber) {
        this.pageNumber = pageNumber;
        this.recordNumber=recordNumber;
    }

    public int getFirst() {

        return (pageNumber - 1) * getMax();
    }

    public int getMax() {
        return recordNumber;
    }
}

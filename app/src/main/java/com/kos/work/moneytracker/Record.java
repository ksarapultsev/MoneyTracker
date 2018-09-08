package com.kos.work.moneytracker;

public class Record {
    private String title;
    private final int price;
    private String comment;


    public Record(String title, int price) {
        this.title=title;
        this.price=price;

    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getComment() {
        return comment;
    }
}

package com.kos.work.moneytracker;

public class Item {
    private String title;
    private  int price;
    private String comment;




    public Item(String title, int price) {
        this.title=title;
        this.price=price;

    }

    public String getPriceTitle() {
        String str = Integer.toString(price);
        str +=" "+"\u20BD";
        return str;
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

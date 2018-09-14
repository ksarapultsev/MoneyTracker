package com.kos.work.moneytracker;

public class Item {
    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";
    // public static final String TYPE_BALANCE = "balance";


    public String name;
    public int price;
    public int id;
    public String type;
    //  private String comment;


    public Item(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;

    }

    public String getPriceTitle() {
        String str = Integer.toString(price);
        str += " " + "\u20BD";
        return str;
    }

    public String getTitle() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    //  public String getComment() {
//        return comment;
//    }
}

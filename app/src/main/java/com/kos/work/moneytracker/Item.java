package com.kos.work.moneytracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_INCOMES = "incomes";
    public static final String TYPE_EXPENSES = "expenses";
    // public static final String TYPE_BALANCE = "balance";


    public String name;
    public String price;
    public int id;
    public String type;
    //  private String comment;


    public Item(String name, String price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;

    }

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getPriceTitle() {
        String str = price;
        str += " " + "\u20BD";
        return str;
    }

    public String getTitle() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    //  public String getComment() {
//        return comment;
//    }
    public int describContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(type);

    }

}

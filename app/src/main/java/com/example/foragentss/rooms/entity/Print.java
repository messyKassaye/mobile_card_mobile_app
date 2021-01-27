package com.example.foragentss.rooms.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "prints")
public class Print {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "card_type")
    private String card_type;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "printing_date")
    private String printingDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPrintingDate() {
        return printingDate;
    }

    public void setPrintingDate(String printingDate) {
        this.printingDate = printingDate;
    }
}

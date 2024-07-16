package com.example.ingredientsmeal.models;

public class SendMessageModel {

    public String consignor, contents;

    public SendMessageModel() {
    }

    public SendMessageModel(String consignor, String contents) {
        this.consignor = consignor;
        this.contents = contents;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }


    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}

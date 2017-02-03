package com.easycore.digestio.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 03.02.17.
 */
public class GetItemsResponse {

    @Expose
    private String status;

    @Expose
    @SerializedName("files")
    private ArrayList<AudioItem> items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<AudioItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<AudioItem> items) {
        this.items = items;
    }
}

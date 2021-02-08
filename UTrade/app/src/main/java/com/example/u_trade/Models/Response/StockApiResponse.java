package com.example.u_trade.Models.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class StockApiResponse {
    public ArrayList<StockApiItem> data;
    public Pagination pagination;

    public StockApiResponse() {
    }

    public StockApiResponse(ArrayList<StockApiItem> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public ArrayList<StockApiItem> getData() {
        return data;
    }

    public void setData(ArrayList<StockApiItem> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public HashMap<Date, Double> getDict() {
        HashMap<Date, Double> result = new HashMap<>();
        for (StockApiItem item : data) {
            result.put(item.date, item.close);
        }
        return result;
    }
}

class Pagination{
    public int limit;
    public int total;
    public int offset;
    public int count;
}



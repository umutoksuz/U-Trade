package com.example.u_trade.Models.Response;

import java.util.Date;

public class StockApiItem {
    public double open;
    public double high;
    public double low;
    public double close;
    public double volume;
    public double adj_high;
    public double adj_low;
    public double adj_close;
    public double adj_open;
    public double adj_volume;
    public String symbol;
    public String exchange;
    public Date date;

    public StockApiItem(double open, double high, double low, double close, double volume, double adj_high, double adj_low, double adj_close, double adj_open, double adj_volume, String symbol, String exchange, Date date) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adj_high = adj_high;
        this.adj_low = adj_low;
        this.adj_close = adj_close;
        this.adj_open = adj_open;
        this.adj_volume = adj_volume;
        this.symbol = symbol;
        this.exchange = exchange;
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getAdj_high() {
        return adj_high;
    }

    public void setAdj_high(double adj_high) {
        this.adj_high = adj_high;
    }

    public double getAdj_low() {
        return adj_low;
    }

    public void setAdj_low(double adj_low) {
        this.adj_low = adj_low;
    }

    public double getAdj_close() {
        return adj_close;
    }

    public void setAdj_close(double adj_close) {
        this.adj_close = adj_close;
    }

    public double getAdj_open() {
        return adj_open;
    }

    public void setAdj_open(double adj_open) {
        this.adj_open = adj_open;
    }

    public double getAdj_volume() {
        return adj_volume;
    }

    public void setAdj_volume(double adj_volume) {
        this.adj_volume = adj_volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

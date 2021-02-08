package com.example.u_trade.Models.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class YahooApiResponse {
    @SerializedName("")
    @Expose
    private Map<String, StockData> result;

    /*public StockData MSFT;
    public StockData NVDA;
    public StockData AAPL;
    public StockData TSLA;
    public StockData CSCO;
    public StockData EBAY;
    public StockData AMD;
    public StockData ADBE;
    public StockData FB;
    public StockData AMZN;*/

    public YahooApiResponse() {
    }

    public Map<String, StockData> getResult() {
        return result;
    }

    public void setResult(Map<String, StockData> result) {
        this.result = result;
    }

    /*public StockData getMSFT() {
        return MSFT;
    }

    public void setMSFT(StockData MSFT) {
        this.MSFT = MSFT;
    }

    public StockData getNVDA() {
        return NVDA;
    }

    public void setNVDA(StockData NVDA) {
        this.NVDA = NVDA;
    }

    public StockData getAAPL() {
        return AAPL;
    }

    public void setAAPL(StockData AAPL) {
        this.AAPL = AAPL;
    }

    public StockData getTSLA() {
        return TSLA;
    }

    public void setTSLA(StockData TSLA) {
        this.TSLA = TSLA;
    }

    public StockData getCSCO() {
        return CSCO;
    }

    public void setCSCO(StockData CSCO) {
        this.CSCO = CSCO;
    }

    public StockData getEBAY() {
        return EBAY;
    }

    public void setEBAY(StockData EBAY) {
        this.EBAY = EBAY;
    }

    public StockData getAMD() {
        return AMD;
    }

    public void setAMD(StockData AMD) {
        this.AMD = AMD;
    }

    public StockData getADBE() {
        return ADBE;
    }

    public void setADBE(StockData ADBE) {
        this.ADBE = ADBE;
    }

    public StockData getFB() {
        return FB;
    }

    public void setFB(StockData FB) {
        this.FB = FB;
    }

    public StockData getAMZN() {
        return AMZN;
    }

    public void setAMZN(StockData AMZN) {
        this.AMZN = AMZN;
    }*/
}


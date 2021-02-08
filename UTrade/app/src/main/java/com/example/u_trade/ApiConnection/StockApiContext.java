package com.example.u_trade.ApiConnection;

import com.example.u_trade.Models.Response.StockApiResponse;
import com.example.u_trade.Models.Response.StockData;
import com.example.u_trade.Models.Response.YahooApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface StockApiContext {
    @Headers({
            "x-rapidapi-key: 72fe404ba9msh79f91acf39f03b2p11e826jsnabc635abe0d8",
            "x-rapidapi-host: yahoo-finance-low-latency.p.rapidapi.com"
    })
    @GET("/v8/finance/spark?symbols=AAPL%2CAMZN%2CMSFT%2CFB%2CTSLA%2CEBAY%2CADBE%2CCSCO%2CNVDA%2CAMD&range=5d&interval=1d")
    Call<Map<String, StockData>> getStockData();
}

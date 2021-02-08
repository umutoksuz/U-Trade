package com.example.u_trade.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.u_trade.ApiConnection.StockApiContext;
import com.example.u_trade.Models.Response.StockApiResponse;
import com.example.u_trade.Models.Response.StockData;
import com.example.u_trade.Models.Response.YahooApiResponse;
import com.example.u_trade.Models.Stock;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    StockApiContext apiContext;
    ListView list;
    StocksArrayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticData.stockApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiContext = retrofit.create(StockApiContext.class);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        list = root.findViewById(R.id.home_stocks_listview);

        adapter = new StocksArrayAdapter(this.getContext(), StaticData.availableStocks);
        list.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        if(StaticData.availableStocks.size() == 0){
            getStockData();
        }

    }

    public void getStockData() {
        Call<Map<String, StockData>> call = apiContext.getStockData();
        call.enqueue(new Callback<Map<String, StockData>>() {
            @Override
            public void onResponse(Call<Map<String, StockData>> call, Response<Map<String, StockData>> response) {
                Log.i("Status", response.code()+" : ");

                if(response.code() == 200) {

                    for(Map.Entry<String, StockData> entry: response.body().entrySet()) {
                        Stock stock = new Stock(entry.getKey(), "");
                        ArrayList<Double> prices = new ArrayList<>();
                        ArrayList<Date> dates = new ArrayList<>();
                        for (int i = 0; i < entry.getValue().close.size(); i++) {
                            dates.add(new Date(new Timestamp(entry.getValue().timestamp.get(i)).getTime()));
                            prices.add(entry.getValue().close.get(i));
                        }
                        stock.setDates(dates);
                        stock.setPrices(prices);
                        StaticData.availableStocks.add(stock);
                        adapter.notifyDataSetChanged();
                        Log.i("Stock: ", stock.getCode());
                    }

                } else {
                    Log.i("Status", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Map<String, StockData>> call, Throwable t) {

            }
        });
    }
}
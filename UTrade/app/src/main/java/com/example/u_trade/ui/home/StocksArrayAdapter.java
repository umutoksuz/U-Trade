package com.example.u_trade.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.u_trade.Models.Stock;
import com.example.u_trade.R;
import com.example.u_trade.ui.stock.StockFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StocksArrayAdapter extends ArrayAdapter<Stock> {
    private final LayoutInflater inflater;
    private final Context context;
    private RecyclerView.ViewHolder holder;
    private final ArrayList<Stock> stocks;

    private static DecimalFormat df = new DecimalFormat("#.##");

    public StocksArrayAdapter(Context context, ArrayList<Stock> stocks) {
        super(context,0, stocks);
        this.context = context;
        this.stocks = stocks;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return stocks.size();
    }

    @Override
    public Stock getItem(int position) {
        return stocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return stocks.get(position).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View stockView = inflater.inflate(R.layout.stock_listview_element, null);
        final TextView codeText = (TextView) stockView.findViewById(R.id.stock_code);
        TextView valueText = (TextView) stockView.findViewById(R.id.stock_value);
        TextView nameText = (TextView) stockView.findViewById(R.id.stock_name);
        TextView priceText = (TextView) stockView.findViewById(R.id.stock_price);

        final Stock stock = stocks.get(i);
        codeText.setText(stocks.get(i).getCode());
        nameText.setText(stocks.get(i).getName());

        final double percentageChange = getPercentageChange(i);

        valueText.setText(df.format(percentageChange)+"%");
        if(percentageChange < 0){
            valueText.setTextColor(Color.parseColor("#ff0000"));
        } else if (percentageChange == 0) {
            valueText.setTextColor(Color.parseColor("#ffffff"));
        }

        priceText.setText(getCurrentPrice(i)+"");

        stockView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new StockFragment(stock, percentageChange);
                ((AppCompatActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, "Stock")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return stockView;
    }

    public double getPercentageChange(int index) {
        Stock stock = stocks.get(index);
        double previousValue = stock.getPrices().get(stock.getPrices().size()-2);
        double currentValue = stock.getPrices().get(stock.getPrices().size()-1);

        return (currentValue-previousValue)/previousValue*100;
    }

    public double getCurrentPrice(int index) {
        Stock stock = stocks.get(index);
        return stock.getPrices().get(stock.getPrices().size()-1);
    }
}

package com.example.u_trade.ui.stock;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.u_trade.Models.Stock;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.example.u_trade.ui.buy.BuyFragment;

import java.text.DecimalFormat;

public class StockFragment extends Fragment {

    private final Stock stock;
    private final double percentgeChange;

    TextView stockCodeText, stockValueText, stockPriceText;
    Button buyButton;

    private static DecimalFormat df = new DecimalFormat("#.##");

    public StockFragment(Stock stock, double percentageChange)  {
        this.stock = stock;
        this.percentgeChange = percentageChange;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_stock, container, false);

        stockCodeText = (TextView) root.findViewById(R.id.stock_detail_code);
        stockValueText = (TextView) root.findViewById(R.id.stock_detail_value);
        stockPriceText = (TextView) root.findViewById(R.id.stock_detail_price);
        buyButton = (Button) root.findViewById(R.id.stock_detail_buy);


        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BuyFragment(stock);
                ((AppCompatActivity)getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, "Order")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        stockCodeText.setText(stock.getCode());
        stockValueText.setText(df.format(percentgeChange));
        stockPriceText.setText(stock.getPrices().get(stock.getPrices().size()-1)+"");
        if(percentgeChange < 0) {
            stockValueText.setTextColor(Color.parseColor("#ff0000"));
        } else if (percentgeChange == 0) {
            stockValueText.setTextColor(Color.parseColor("#ffffff"));
        }
        //TODO: bring it back!!
        if (!StaticData.loggedIn){
            buyButton.setVisibility(View.INVISIBLE);
        }
    }
}

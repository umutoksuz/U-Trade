package com.example.u_trade.ui.slideshow;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.u_trade.ApiConnection.BJPClient;
import com.example.u_trade.Models.Offer;
import com.example.u_trade.Models.Request.SingleIdRequest;
import com.example.u_trade.Models.Stock;
import com.example.u_trade.Protocol.BjpResponse;
import com.example.u_trade.Protocol.StatusCode;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AvailableOfferListAdapter extends ArrayAdapter<Offer> {

    private final LayoutInflater inflater;
    private final Context context;
    private final ArrayList<Offer> offers;

    public AvailableOfferListAdapter(Context context, ArrayList<Offer> offers) {
        super(context,0, offers);
        this.context = context;
        this.offers = offers;
        inflater = LayoutInflater.from(context);
    }

    private static DecimalFormat df = new DecimalFormat("#.##");
    private static DecimalFormat df3 = new DecimalFormat("#.###");

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Offer getItem(int position) {
        return offers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return offers.get(position).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View offerView = inflater.inflate(R.layout.available_offer_list_element, null);
        TextView codeText = (TextView) offerView.findViewById(R.id.available_offer_code);
        TextView currentPriceText = (TextView) offerView.findViewById(R.id.available_offer_current_price);

        TextView dateText = (TextView) offerView.findViewById(R.id.available_offer_date_to);
        TextView amountText  = (TextView) offerView.findViewById(R.id.available_offer_amount);
        TextView priceText  = (TextView) offerView.findViewById(R.id.available_offer_price);
        TextView leverageText  = (TextView) offerView.findViewById(R.id.available_offer_leverage);
        TextView positionText  = (TextView) offerView.findViewById(R.id.available_offer_type);
        Button button = (Button) offerView.findViewById(R.id.available_offer_button);

        final Offer offer = offers.get(i);

        double currentPrice=0;
        for(Stock stock : StaticData.availableStocks) {
            if(stock.getCode().equals(offer.getStockCode())) {
                currentPrice = stock.getPrices().get(stock.getPrices().size()-1);
            }
        }
        codeText.setText(offer.getStockCode());
        currentPriceText.setText(currentPrice+"");
        dateText.setText(offer.endDate.toString());
        amountText.setText(offer.getAmount()+"");
        priceText.setText(offer.getPrice()+"");
        leverageText.setText(offer.getLeverage()+"");

        String position = "LONG";
        if(!offer.isShortPosition()){
            position = "SHORT";
        }
        positionText.setText(position);

        LocalDate now = LocalDate.now();

        double requiredMoney = offer.getPrice() * offer.getAmount();
        if(StaticData.accountBalance < requiredMoney)
            button.setEnabled(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BJPClient client = new BJPClient();
                            final Gson gsonBuilder = new GsonBuilder().create();
                            SingleIdRequest requestBody = new SingleIdRequest(offer.id);
                            final BjpResponse response = client.sendRequest("acceptoffer", StaticData.token, gsonBuilder.toJson(requestBody));
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.getStatusCode() == StatusCode.OK && response.isSuccessful()) {
                                        Toast.makeText(context, "Success!!", Toast.LENGTH_LONG).show();
                                        StaticData.accountBalance -= offer.price * offer.amount;
                                    }else {
                                        Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } catch (InterruptedException | ExecutionException exc) { }
                    }
                });
                thread.start();
            }
        });

        return offerView;
    }

}

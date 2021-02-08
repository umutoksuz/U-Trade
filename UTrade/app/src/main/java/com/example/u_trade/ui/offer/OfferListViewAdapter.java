package com.example.u_trade.ui.offer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.u_trade.ApiConnection.BJPClient;
import com.example.u_trade.Models.Offer;
import com.example.u_trade.Models.Request.SingleIdRequest;
import com.example.u_trade.Models.Request.TransactionRequest;
import com.example.u_trade.Models.Response.TransactionResponse;
import com.example.u_trade.Models.Stock;
import com.example.u_trade.Protocol.BjpResponse;
import com.example.u_trade.Protocol.StatusCode;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.example.u_trade.ui.stock.StockFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class OfferListViewAdapter extends ArrayAdapter<Offer> {
    private final LayoutInflater inflater;
    private final Context context;
    private final ArrayList<Offer> offers;

    public OfferListViewAdapter(Context context, ArrayList<Offer> offers) {
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
        View offerView = inflater.inflate(R.layout.offer_listview_element, null);
        TextView codeText = (TextView) offerView.findViewById(R.id.offer_code);
        TextView currentPriceText = (TextView) offerView.findViewById(R.id.offer_current_price);
        TextView percentageChange = (TextView) offerView.findViewById(R.id.offer_percent_change);
        TextView dateText = (TextView) offerView.findViewById(R.id.offer_date_to);
        TextView amountText  = (TextView) offerView.findViewById(R.id.offer_amount);
        TextView priceText  = (TextView) offerView.findViewById(R.id.offer_price);
        TextView leverageText  = (TextView) offerView.findViewById(R.id.offer_leverage);
        TextView positionText  = (TextView) offerView.findViewById(R.id.offer_type);
        TextView statusText = (TextView) offerView.findViewById(R.id.offer_status);
        Button button = (Button) offerView.findViewById(R.id.offer_button);

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
        statusText.setText(getStatus(offer));



        if(offer.isShortPosition()){
            position = "SHORT";
        } if (offer.creatorId != StaticData.userId) {
            if(position.equals("LONG")) {
                position = "SHORT";
            } else {
                position = "LONG";
            }
        }

        positionText.setText(position);

        LocalDate now = LocalDate.now();

        double diff = 0;

        String state = "CANCEL";

        if(now.isAfter(offer.endDate) && offer.isAccepted &&
                ((StaticData.userId == offer.acceptorId && !offer.isAcceptorCheckout()) ||
                        (StaticData.userId == offer.creatorId && !offer.isCreatorCheckout()))){
            state = "CLAIM";
            button.setText("GET MONEY");
            button.setBackgroundColor(Color.parseColor("#00ff00"));
        } else if (!offer.isAccepted()) {

        }  else {
            button.setVisibility(View.INVISIBLE);
            state = "PENDING";
        }

        if (!state.equals("CANCEL")){
            diff = calculateDiff(offer, position.equals("SHORT"));
        }
        if (diff > 0) {
            percentageChange.setText("+"+df.format(diff));
        } else if (diff == 0) {
            percentageChange.setText("0");
        } else {
            percentageChange.setText("-"+df.format(diff));
            percentageChange.setTextColor(Color.parseColor("#ff0000"));
        }



        final String fState = state;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BJPClient client = new BJPClient();
                            final Gson gsonBuilder = new GsonBuilder().create();
                            String path = "";
                            String json = "";
                            if(fState.equals("CANCEL")){
                                path = "canceloffer";
                                SingleIdRequest requestBody = new SingleIdRequest(offer.id);
                                json = gsonBuilder.toJson(requestBody);
                            } else {
                                path = "transaction";
                                TransactionRequest requestBody = new TransactionRequest(offer.id, getEndPrice(offer));
                                json = gsonBuilder.toJson(requestBody);
                            }

                            final BjpResponse response = client.sendRequest(path, StaticData.token, json);

                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.getStatusCode() == StatusCode.OK && response.isSuccessful()) {
                                        Toast.makeText(context, "Success!!", Toast.LENGTH_LONG).show();
                                        if (fState.equals("CANCEL"))
                                            StaticData.accountBalance += offer.price * offer.amount;
                                        else {
                                            TransactionResponse responseBody = gsonBuilder.fromJson(response.getData(), TransactionResponse.class);
                                            Log.i("MONEY_BACK", responseBody.getReturnMoney()+"");
                                            StaticData.accountBalance += responseBody.getReturnMoney();
                                        }
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

    public double getEndPrice (Offer offer) {
        Stock stock = null;
        for (Stock stc : StaticData.availableStocks) {
            if(stc.getCode().equals(offer.getStockCode())) {
                stock = stc;
            }
        }
        if (stock == null)
            return 0;
        double price = 0;
        for (int i = 0; i < stock.getDates().size(); i++) {
            Date date = stock.getDates().get(i);
            LocalDate dt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(dt.equals(offer.getEndDate())){
                price = stock.getPrices().get(i);
            }
        }
        if (price == 0)
            price = stock.getPrices().get(stock.getPrices().size()-1);
        return price;
    }

    public String getStatus(Offer offer){
        String status = "";

        if (offer.endDate.isAfter(LocalDate.now()) || offer.endDate.equals(LocalDate.now())) {
            if (offer.isAccepted()) {
                status = "In progress";
            }else {
                status = "Pending";
            }
        } else {
            if (offer.isAccepted()) {
                status = "Done";
            }else {
                status = "Expired";
            }
        }
        return  status;
    }

    public double calculateDiff (Offer offer, boolean isShort) {
        Stock stock = null;
        for (Stock stc : StaticData.availableStocks) {
            if(stc.getCode().equals(offer.getStockCode())) {
                stock = stc;
            }
        }
        if (stock == null)
            return 0;
        double lastPrice = stock.getPrices().get(stock.getPrices().size()-1);
        double capital = offer.amount * offer.price;
        double diffRatio = (lastPrice-offer.price)/offer.price * offer.leverage;
        if(isShort)
            diffRatio = -diffRatio;
        return capital*diffRatio;
    }

}

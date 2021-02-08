package com.example.u_trade.ui.buy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.u_trade.ApiConnection.BJPClient;
import com.example.u_trade.Models.Request.PostOfferRequest;
import com.example.u_trade.Models.Response.SingleIdResponse;
import com.example.u_trade.Models.Stock;
import com.example.u_trade.Protocol.BjpResponse;
import com.example.u_trade.Protocol.StatusCode;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BuyFragment extends Fragment {

    private final Stock stock;

    TextView codeText, statusText;
    EditText amountField, dateText, priceText;
    Spinner leverageSpinner, typeSpinner;
    Button createOrderButton;
    DatePickerDialog datePickerDialog;

    public BuyFragment(Stock stock) {
        this.stock = stock;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_buy, container, false);

        codeText = (TextView) root.findViewById(R.id.stock_buy_name);
        priceText = (EditText) root.findViewById(R.id.stock_buy_price);
        amountField = (EditText) root.findViewById(R.id.stock_buy_amount);
        leverageSpinner = (Spinner) root.findViewById(R.id.stock_buy_leverage);
        typeSpinner = (Spinner) root.findViewById(R.id.stock_buy_type);
        createOrderButton = (Button) root.findViewById(R.id.stock_buy_submit);
        dateText = (EditText) root.findViewById(R.id.stock_buy_date);
        statusText = (TextView) root.findViewById(R.id.stock_buy_status);

        final ArrayAdapter<String> leverageAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.custom_spinner,
                getResources().getStringArray(R.array.leverage_options));
        leverageAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        leverageSpinner.setAdapter(leverageAdapter);

        ArrayAdapter<String> positionAdapter = new ArrayAdapter<>(this.getContext(), R.layout.custom_spinner,
                getResources().getStringArray(R.array.position_options));
        positionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeSpinner.setAdapter(positionAdapter);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String monthStr = ""+month;
                        String dayStr = ""+day;
                        int monthCorrect = month+1;
                        if(monthCorrect < 10) {
                            monthStr = "0" + monthCorrect;
                        }
                        if(day < 10) {
                            dayStr = "0"+day;
                        }
                        dateText.setText(year+"-"+monthStr+"-"+dayStr);
                    }
                }, 2021, 1, 8);
                datePickerDialog.show();
            }
        });

        createOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread thread = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    boolean isShort = false;
                                    if(typeSpinner.getSelectedItemPosition() == 1)
                                        isShort = true;

                                    BJPClient client = new BJPClient();
                                    final Gson gsonBuilder = new GsonBuilder().create();
                                    PostOfferRequest requestBody = new PostOfferRequest(codeText.getText().toString(),
                                            Integer.parseInt(amountField.getText().toString()),
                                            Double.parseDouble(priceText.getText().toString()),
                                            isShort, leverageSpinner.getSelectedItemPosition()+1, dateText.getText().toString());
                                    final BjpResponse response = client.sendRequest("createoffer",
                                            StaticData.token, gsonBuilder.toJson(requestBody));

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(response.getStatusCode() == StatusCode.OK && response.isSuccessful()) {
                                                SingleIdResponse responseBody = gsonBuilder.fromJson(response.getData(), SingleIdResponse.class);
                                                StaticData.accountBalance -= Double.parseDouble(priceText.getText().toString()) * Integer.parseInt(amountField.getText().toString());
                                            } else {

                                            }
                                            statusText.setText(response.getMessage());
                                        }
                                    });

                                } catch (Exception e) {
                                    Log.e("EXCEPTION",e.getMessage());
                                }
                            }
                        }
                );
                thread.start();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        codeText.setText(stock.getCode());

    }
}

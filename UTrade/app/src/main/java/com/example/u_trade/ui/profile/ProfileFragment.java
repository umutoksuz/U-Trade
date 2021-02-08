package com.example.u_trade.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.u_trade.ApiConnection.BJPClient;
import com.example.u_trade.Models.Request.AddBalanceRequest;
import com.example.u_trade.Protocol.BjpResponse;
import com.example.u_trade.Protocol.StatusCode;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class ProfileFragment extends Fragment {


    TextView nameText, surnameText, accountBalanceText, statusText;
    EditText addBalanceText;
    Button submitNewBalance;

    private static DecimalFormat df = new DecimalFormat("#.##");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        nameText = (TextView) root.findViewById(R.id.profile_name);
        surnameText = (TextView) root.findViewById(R.id.profile_surname);
        accountBalanceText = (TextView) root.findViewById(R.id.profile_balance);
        statusText = (TextView) root.findViewById(R.id.profile_status);
        addBalanceText = (EditText) root.findViewById(R.id.profile_add_balance);
        submitNewBalance = (Button) root.findViewById(R.id.profile_button);

        submitNewBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BJPClient client = new BJPClient();
                            final Gson gsonBuilder = new GsonBuilder().create();

                            final AddBalanceRequest requestBody = new AddBalanceRequest(Double.parseDouble(addBalanceText.getText().toString()));
                            final BjpResponse response = client.sendRequest("addbalance", StaticData.token, gsonBuilder.toJson(requestBody));

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.getStatusCode() == StatusCode.OK && response.isSuccessful()) {
                                        StaticData.accountBalance += Double.parseDouble(addBalanceText.getText().toString());
                                        accountBalanceText.setText(df.format(StaticData.accountBalance));
                                        addBalanceText.setText("");
                                    }
                                    statusText.setText(response.getMessage());
                                }
                            });

                        } catch (InterruptedException | ExecutionException exc) {
                            Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                thread.start();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        if (StaticData.loggedIn) {
            nameText.setText(StaticData.firstName);
            surnameText.setText(StaticData.lastName);
            accountBalanceText.setText(df.format(StaticData.accountBalance));
        } else {
            nameText.setText("----");
            surnameText.setText("----");
            accountBalanceText.setText("----");
            submitNewBalance.setEnabled(false);
        }
    }
}
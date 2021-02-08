package com.example.u_trade.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.u_trade.ApiConnection.BJPClient;
import com.example.u_trade.Models.Offer;
import com.example.u_trade.Protocol.BjpResponse;
import com.example.u_trade.Protocol.StatusCode;
import com.example.u_trade.R;
import com.example.u_trade.StaticData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AvailableOffersFragment extends Fragment {

    AvailableOfferListAdapter adapter;
    ArrayList<Offer> offers;
    ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_available_offer, container, false);
        list = (ListView) root.findViewById(R.id.available_offers_listview_);
        offers = new ArrayList<>();
        adapter = new AvailableOfferListAdapter(this.getContext(), offers);
        list.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        getData();

    }

    public void getData() {
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BJPClient client = new BJPClient();
                            final Gson gsonBuilder = new GsonBuilder().create();

                            final BjpResponse response = client.sendRequest("alloffers",
                                    StaticData.token, "");

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.getStatusCode() == StatusCode.OK && response.isSuccessful()) {
                                        Type listType = new TypeToken<ArrayList<Offer>>(){}.getType();
                                        ArrayList<Offer> result = gsonBuilder.fromJson(response.getData(), listType);
                                        for(Offer offer : result){
                                            Log.i("ADD", offer.getStockCode());
                                            offers.add(offer);
                                            adapter.notifyDataSetChanged();
                                        }

                                    } else {
                                        Toast.makeText(getContext(), response.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
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
}
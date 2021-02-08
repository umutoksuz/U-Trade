package com.example.u_trade.ApiConnection;

import android.os.AsyncTask;
import android.util.Log;

import com.example.u_trade.Protocol.BjpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerThread extends AsyncTask<String, Void, BjpResponse> {
    Socket socket;

    @Override
    protected BjpResponse doInBackground(String... params){
        String ip = params[0];
        int port = Integer.parseInt(params[1]);

        try{
            socket = new Socket(ip, port);
            Log.e("Connection", "Connected");
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(params[2]);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BjpResponse response = new BjpResponse();
            response.readInput(reader);
            return response;

        } catch (IOException e){
            Log.e("Connection Failed", e.getMessage());
            return null;
        }
    }
}

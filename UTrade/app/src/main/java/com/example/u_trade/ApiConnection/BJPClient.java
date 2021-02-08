package com.example.u_trade.ApiConnection;

import com.example.u_trade.Protocol.BjpRequest;
import com.example.u_trade.Protocol.BjpResponse;

import java.util.concurrent.ExecutionException;

public class BJPClient {
    //private final String IP_ADDRESS = "10.0.2.2";
    private final String IP_ADDRESS = "18.189.22.86";
    private final int PORT_NUMBER = 3999;

    public BJPClient() {

    }

    public BjpResponse sendRequest(String route, String token, String data) throws InterruptedException, ExecutionException {
        BjpRequest request = new BjpRequest(route, token, data);
        WorkerThread workerThread = new WorkerThread();
        BjpResponse response = workerThread.execute(IP_ADDRESS, String.valueOf(PORT_NUMBER), request.getContent()).get();
        return response;
    }
}

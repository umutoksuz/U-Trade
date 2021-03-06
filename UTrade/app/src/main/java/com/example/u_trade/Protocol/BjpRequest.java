package com.example.u_trade.Protocol;

import java.io.BufferedReader;
import java.io.IOException;

public class BjpRequest {
    private static final String NL = "\n";

    private String route;
    private String token;
    private int dataSize;
    private String data;
    private String content;

    public BjpRequest(String route, String token, String data) {
        this.route = route;
        this.token = token;
        this.dataSize = data.length();
        this.data = data;
        this.content = route + NL + token + NL + dataSize + NL + data;
    }

    public BjpRequest(String route, String token, int dataSize) {
        this.route = route;
        this.token = token;
        this.dataSize = dataSize;
    }

    public void readInput(BufferedReader reader) throws IOException, NumberFormatException {
        route = reader.readLine();
        token = reader.readLine();
        dataSize = Integer.parseInt(reader.readLine());
        String dataString = "";
        boolean completed = false;
        while (!completed && reader.ready()) {
            int readByte = reader.read();
            dataString += (char) readByte;
            if (dataString.length() == this.dataSize) {
                completed = true;
            }
        }
        this.data = dataString;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        String result =
                "Route: " + getRoute() + "\n" +
                        "Token: " + getToken() + "\n" +
                        "Data Size: " + getDataSize() + "\n" +
                        "Data: " + getData() + "\n";
        return result;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.example.u_trade.Protocol;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

public class BjpResponse {
    private StatusCode statusCode;
    private boolean isSuccessful;
    private String message;
    private int dataLength;
    private String data;
    private String content;

    public BjpResponse() {}

    public BjpResponse(StatusCode statusCode, boolean isSuccessful, String message, String data) {
        this.statusCode = statusCode;
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.data = data;
        this.content = "";
        processResponse();
    }

    public void readInput(BufferedReader reader) throws IOException, NumberFormatException {
        statusCode = StatusCodeMapper.mapToEnum(reader.readLine());
        isSuccessful = reader.readLine().equals("1");
        message = reader.readLine();
        dataLength = Integer.parseInt(reader.readLine());

        String dataString = "";
        boolean completed = false;
        while (!completed && reader.ready()) {
            int readByte = reader.read();
            dataString+=(char)readByte;
            if (dataString.length() == this.dataLength){
                completed = true;
            }
        }
        this.data = dataString;
        Log.e("DATA", data);
    }

    public String toString(){
        String result =
                "Status: " + statusCode + "\n" +
                        "Is successful: " + isSuccessful + "\n" +
                        "Message: " +message + "\n" +
                        "Size: " + dataLength + "\n" +
                        "Data: " + data;
        return  result;
    }

    private void processResponse(){
        this.dataLength = this.data.length();
        this.content += StatusCodeMapper.mapToStatusCode(this.statusCode) + "\n";
        this.content += (this.isSuccessful ? "1" : "0") + "\n";
        this.content += this.message + "\n";
        this.content += this.dataLength + "\n";
        this.content += this.data;
    }

    public String getContent(){
        return this.content;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

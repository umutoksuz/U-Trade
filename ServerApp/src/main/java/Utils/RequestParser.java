package Utils;

import Contract.Request.LoginRequest;
import Protocol.BjpRequest;
import Protocol.BjpResponse;
import Protocol.StatusCode;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class RequestParser {
    public static <T> T parse(BjpRequest request, Class<T> type){
        Gson gson = new Gson();

        T requestData = null;
        System.out.println(request.getData());
        try{
            requestData = gson.fromJson(request.getData(), type);
        }catch (JsonSyntaxException e){
            System.out.println(e.getStackTrace());
        }

        return requestData;
    }
}

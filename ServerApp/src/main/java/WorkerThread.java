
import Protocol.BjpRequest;
import Protocol.BjpResponse;
import Protocol.StatusCode;

import java.io.*;
import java.net.Socket;

public class WorkerThread implements Runnable{
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public WorkerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
    }

    @Override
    public void run(){
        try {
            //Read the request and parse it
            BjpRequest request = new BjpRequest();
            request.readInput(new BufferedReader(new InputStreamReader(in)));

            System.out.println(request.toString());

            BjpResponse response = Router.routeRequest(request);

            Thread.sleep(100);

            PrintWriter writer = new PrintWriter(out, true);
            writer.println(response.getContent());

            in.close();
            out.close();
            socket.close();

        }  catch (Exception e) {
            System.out.println(e.getMessage());
            BjpResponse response = new BjpResponse(StatusCode.SERVER_ERROR, false, "Server error", "");
            PrintWriter writer = new PrintWriter(out, true);
            writer.println(response.getContent());
            try {
                in.close();
                out.close();
                socket.close();
            } catch (Exception ex) {}
        }
    }
}

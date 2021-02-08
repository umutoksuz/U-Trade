
import Protocol.BjpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class TestClient {
    public static void main(String[] args) throws IOException {
        /*Socket socket = new Socket("localhost", 3999);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.write("acceptoffer\n");
        writer.write("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEsImF1ZCI6InN0cmFkZV91c2VycyJ9.un7Hm9yS4ZMxstqhFBhjaRIJONqOSvvQ8krPPqALTAU\n");
        writer.write("250\n");
        writer.write("{\"id\":1}");*/
        /*writer.write("{\n" +
                "    \"stockCode\": \"ALCAR\",\n" +
                "    \"amount\": 100,\n" +
                "    \"price\": 455,\n" +
                "    \"shortPosition\": false,\n" +
                "    \"leverage\": 2,\n" +
                "    \"endDate\": \"2021-02-10\"\n" +
                "}");*/
        /*writer.flush();

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(in);

        //while (!reader.ready());
        /*System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());
        System.out.println(reader.readLine());*/
        /*BjpResponse response = new BjpResponse();
        response.readInput(reader);
        System.out.println(response.toString());*/

        LocalDate date = LocalDate.now();
        Date dt = Calendar.getInstance().getTime();
        System.out.println(dt.getMonth());

    }

    public static boolean dateEquals (Date date, LocalDate localDate){
        if (localDate.getMonth().getValue() == date.getMonth() && localDate.getDayOfMonth() == date.getDate() && localDate.getYear() == date.getYear()){
            return true;
        }
        return false;
    }
}

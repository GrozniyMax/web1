package org.example;


import com.fastcgi.FCGIInterface;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final String RESULT_JSON = """
            {
                "hit": %b,
                "startTime": "%s", 
                "executionTime": "%s"
            }
            """;
    private static final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: %d
                        
            %s
            """;
    private static final String HTTP_ERROR = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            Content-Length: %d
                        
            %s
            """;
    private static final String ERROR_JSON = """
            {
                "reason": "%s"
            }
            """;


    public static void main(String[] args) {
        var fcgi = new FCGIInterface();
        while (fcgi.FCGIaccept() >= 0) {
            try {
                LocalTime startTime = LocalTime.now();

                var fcgiRequest = fcgi.request;
                String requestMethod = fcgiRequest.params.getProperty("REQUEST_METHOD");

                if (requestMethod.equals("POST")) {


                    fcgiRequest.inStream.fill();


                    int contentLength = 0;

                    contentLength = fcgiRequest.inStream.available();


                    var buffer = ByteBuffer.allocate(contentLength);
                    int readBytes = 0;

                    readBytes = fcgiRequest.inStream.read(buffer.array(), 0, contentLength);


                    var requestBodyRaw = new byte[readBytes];
                    buffer.get(requestBodyRaw);
                    buffer.clear();


                    String request = new String(requestBodyRaw, StandardCharsets.UTF_8);

                    Gson gson = new Gson();
                    Params params = gson.fromJson(request, Params.class);
                    var result = calculate(params.getX(), params.getY(), params.getR());

                    LocalTime endTime = LocalTime.now();
                    Duration duration = Duration.between(startTime, endTime);
                    long millis = duration.toMillis();
                    long seconds = millis / 1000;
                    long remainingMillis = millis % 1000;
                    String formattedDuration = String.format("%d.%03d", seconds, remainingMillis);

                    // Используем форматтер для времени
                    String startTimeString = startTime.format(DateTimeFormatter.ISO_LOCAL_TIME);

                    var json = String.format(RESULT_JSON, result, startTimeString, formattedDuration);
                    var response = String.format(HTTP_RESPONSE, json.getBytes(StandardCharsets.UTF_8).length + 2, json);

                    System.out.println(response);

                }
            } catch (Exception ex) {
                var json = String.format(ERROR_JSON, ex.getMessage());
                var response = String.format(HTTP_ERROR, json.getBytes(StandardCharsets.UTF_8).length + 2, json);
                System.out.println(response);
            }
        }
    }


        private static boolean calculate ( float x, float y, float r){
            if (x < 0 && y < 0) {
                return false;
            }
            if (x < 0 && y > 0) {
                if ((x * x + y * y) > (r / 2) * (r / 2)) {
                    return false;
                }
            }
            if (x > 0 && y < 0) {
                if ((x / 2 + y) < -r / 2) {
                    return false;
                }
            }
            if (x > 0 && y > 0) {
                if (x < -r / 2 || y > r) {
                    return false;
                }
            }
            return true;
        }
    }

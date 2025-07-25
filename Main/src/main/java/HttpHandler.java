import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class HttpHandler {
//    public JsonHandler jsonHandler;



    public static void main(String[] args){
        String api_type = "send_group_msg";
        JsonHandler jsonHandler;

        jsonHandler = new JsonHandler(api_type, "text", "this is encapsulation message");

        HttpClient mainClient = HttpClient.newHttpClient();
        HttpRequest mainRequest = HttpRequest.newBuilder()
                .uri(URI.create(jsonHandler.url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonHandler.getJson()))
                .build();
//        System.out.println(mainRequest);
        try {
            HttpResponse<String> response =
                    mainClient.send(mainRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

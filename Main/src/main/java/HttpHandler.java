import javax.xml.parsers.ParserConfigurationException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class HttpHandler {
//    public JsonHandler jsonHandler;



    public static void main(String[] args) throws ParserConfigurationException {
        String api_type = "send_group_msg";
        JsonHandler jsonHandler;
        RssHandler rssHandler = new RssHandler();
        for (int i=0;i<rssHandler.getContent().size();i++){
            jsonHandler = new JsonHandler(api_type, "text", rssHandler.getContent().get(i));
//            System.out.println(rssHandler.getContent().get(i));
            HttpClient mainClient = HttpClient.newHttpClient();
            HttpRequest mainRequest = HttpRequest.newBuilder()
                    .uri(URI.create(jsonHandler.url))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonHandler.getJson()))
                    .build();

            try {
                HttpResponse<String> response =
                        mainClient.send(mainRequest, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.statusCode());
                System.out.println(response.body());
                Thread.sleep(5000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



    }
}

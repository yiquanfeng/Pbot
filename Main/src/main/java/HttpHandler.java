import javax.xml.parsers.ParserConfigurationException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;


public class HttpHandler {
    private static final HttpClient mainClient = HttpClient.newHttpClient();

    static void http_handler(String url, String json){

        HttpRequest mainRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
//        System.out.println(json);
        try {
            HttpResponse<String> response =
                    mainClient.send(mainRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
//            Thread.sleep(1000); // 20mins
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void send_private_msg(JsonHandler jsonHandler){
//        String api_type = "send_private_msg";
//        http_handler(jsonHandler.url, jsonHandler.getJson());

//        RssHandler rssHandler = new RssHandler();
//        ArrayList<String> fullContent = rssHandler.getContent();
//        int limit = fullContent.size();
//        for (int i=0;i< limit;i++){
//            JsonHandler = new JsonHandler(api_type, "text", fullContent.get(i));
//            System.out.println(rssHandler.getContent().size());

    }

    public static void main(String[] args) throws ParserConfigurationException {

    }
}

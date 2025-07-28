import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main (String[] aegs){
        // init api type,messge json
        String api = "send_group_msg";
        String sender_id = "3775141348";
        String sneder_name = "bot";
        String group_id = "1055065019";

        JsonHandler jsonHandler = new JsonHandler(group_id, "nodes", api);
        RssHandler rssHandler = new RssHandler();
        ArrayList<String> fulls = rssHandler.getContent();
        for (int i=0; i<fulls.size(); i++) {
            jsonHandler.addNode(sender_id, sneder_name, "text", fulls.get(i));
        }
        System.out.println(jsonHandler.getNodeJson());
        HttpHandler.http_handler(jsonHandler.url, jsonHandler.getNodeJson());
    }
}

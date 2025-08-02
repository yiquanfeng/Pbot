import com.sun.java.accessibility.util.Translator;

import java.util.ArrayList;


public class Main {
    public static void main (String[] aegs){
        // init api type,messge json
        String api = "send_group_msg";
        String sender_id = "3775141348";
        String sneder_name = "bot";
        String group_id = "1055065019";

        TranslatorMy translator = new TranslatorMy();
//        String content =  translator.trans("hello linux, this is a test for xdd");
//
//        JsonHandler greeting = new JsonHandler(group_id, "text", content, api);
//        System.out.println(greeting.json);
//        HttpHandler.http_handler(greeting.url, greeting.json);

        JsonHandler jsonHandler = new JsonHandler(group_id, "nodes", api);
        RssHandler rssHandler = new RssHandler();
        ArrayList<String> fulls = rssHandler.getContent();

        for (int i=0; i<fulls.size(); i++) {
            jsonHandler.addNode(sender_id, sneder_name, "text", translator.trans(fulls.get(i))+"\n\n\n"+rssHandler.extraContent.get(i));
        }
        System.out.println(jsonHandler.getNodeJson());
        HttpHandler.http_handler(jsonHandler.url, jsonHandler.getNodeJson());
    }
}

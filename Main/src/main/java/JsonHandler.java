import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonHandler {
    Gson gson = new Gson();
    GroupMessage gmsg = new GroupMessage();
    String json = gson.toJson(gmsg);
    String url = "http://127.0.0.1:3001/{api_type}";
    JsonHandler(){

    }

    JsonHandler(String apiType, String type, String text){
        this.url = this.url.replace("{api_type}", apiType);
        this.gmsg = new GroupMessage(type, text);
        json = gson.toJson(gmsg);
    }

    public String getJson(){
        return json;
    }
    public static void main(String[] args){
        JsonHandler hsn = new JsonHandler("send_group_msg", "text", "this is encapsulation message");
        System.out.println(hsn.getJson());
    }
}

class GroupMessage {
    String group_id = "1055065019";
    ArrayList<Message> message = new ArrayList<>();

    GroupMessage(){
        message.add(new Message("text", "default text"));
    }
    // default is the linux group
    GroupMessage(String type, String text){
        message.add(new Message(type, text));
    }
    // choose the pacific group
    GroupMessage(String group_id, String type, String text){
        this.group_id = group_id;
        message.add(new Message(type, text));
    }
}

class Message {
    String type = "default text";
    Data data = new Data("default text");
    Message(String type, String text) {
        this.type = type;
        this.data.text = text;
    }
}

class Data {
    public String text;
    Data(String text){
        this.text = text;
    }
}
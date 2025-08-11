import com.google.gson.Gson;

import java.util.*;


public class JsonHandler {
    static Gson gson = new Gson();
    String json = "";
    String url = "http://xxx/{api}";
    NodesMessage nodesMessage = new NodesMessage();
    JsonHandler(){

    }

    JsonHandler(String user_id, String type, String data, String api){
        url = url.replace("{api}", api);
        json = gson.toJson(new SingalMessage(user_id, type, data));
    }

    JsonHandler(String user_id, String type, String api){
        url = url.replace("{api}", api);
        nodesMessage.group_id = user_id;
    }

    void addNode(String user_id, String nickname, String type, String data){
        nodesMessage.add(user_id, nickname, type, data);
    }

    public String getNodeJson(){
        json = gson.toJson(nodesMessage);
        return json;
    }
    public static void main(String[] args){
//        NodesMessage nodes = new NodesMessage("1572087810");
//        nodes.add("1572087810", "yqf", "text", "xdd is handsome");
//        System.out.println(JsonHandler.gson.toJson(nodes));
    }
}


class NodesMessage {
    String group_id = "";
    List<NodeContent> message = new ArrayList<>();

    NodesMessage(){

    }
    NodesMessage(String group_id){
        this.group_id = group_id;
    }

    void add(String user_id, String nickname, String type, String data){
        NodeContent tmp1 = new NodeContent(user_id, nickname);
        SingalContent tmp2 = new SingalContent(type, data);
        tmp1.add(tmp2);
        message.add(tmp1);
    }
}

//class groupNodesMessage {
//    String group_id = "";
//    List<NodeContent> messages = new ArrayList<>();
//
//    groupNodesMessage(){
//
//    }
//    groupNodesMessage(String group_id){
//        this.group_id = group_id;
//    }
//
//    void add(String group_id, String nickname, String type, String data){
//        NodeContent tmp1 = new NodeContent(group_id, nickname);
//        SingalContent tmp2 = new SingalContent(type, data);
//        tmp1.add(tmp2);
//        messages.add(tmp1);
//    }
//}

class SingalMessage {
    String group_id = "";
    List<SingalContent> message = new ArrayList<>();
//    NodeContent nodeMsg = new NodeContent();
//    String jsonMsg = JsonHandler.gson.toJson(Msg);

    SingalMessage(String group_id, String type, String data) {
        this.group_id = group_id;
        this.message.add(new SingalContent(type, data));
    }

}

//class groupSingalMessage {
//    String group_id = "";
//    List<SingalContent> messages = new ArrayList<>();
////    NodeContent nodeMsg = new NodeContent();
////    String jsonMsg = JsonHandler.gson.toJson(Msg);
//
//    groupSingalMessage(String group_id, String type, String data) {
//        this.group_id = group_id;
//        this.messages.add(new SingalContent(type, data));
//    }
//
//}

class NodeContent {
    String type = "node";
    Node data = new Node();

    NodeContent(){

    }
    NodeContent(String user_id, String nickname){
        this.data.set(user_id, nickname);
    }

    void add(SingalContent content){
        this.data.add(content);
    }
}

class Node {
    String user_id = "";
    String nickname = "";
    List<SingalContent> content = new ArrayList<>();
    Node() {

    }
    Node(String user_id, String nickname){
        this.user_id = user_id;
        this.nickname = nickname;
    }

    void set(String user_id, String nickname){
        this.user_id = user_id;
        this.nickname = nickname;
    }

    void add(SingalContent toAdd){
        content.add(toAdd);
    }
}

class SingalContent {
    String type = "";
    Map<String, String> data = new LinkedHashMap<>();

    SingalContent(){

    }

    SingalContent(String type, String content){
        if (type.equals("text"))
        {
            this.data.put("text", content);
        }
        else {
            System.out.println("not text");
        }
        this.type = type;
    }

    void set(String type, String content){
        if (type.equals("text"))
        {
            this.data.put("text", content);
        }
        else {
            System.out.println("not text");
        }
        this.type = type;
    }
}

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

public class TranslatorMy {
    static String BASE_URL = "http://xx.xx.253.170:2277/v1";
    static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10)) // 10秒连接超时
            .build();

    static String system = "你是一位熟悉中文、英文和科技，尤其是操作系统、硬件和 Linux 相关知识的科技翻译人员。" +
            "用户会给出一则英文科技新闻摘要，你的任务是将其翻译为中文。你的翻译应当准确表达原文内容；" +
            "在此前提下你可以适当调整翻译内容，保证翻译结果符合中文语言习惯。输出的翻译中不应包含其他内容。\n" +
            "输出应当以Markdown格式给出，其中标题以#开头即可，不应添加粗体标记。\n" +
            "摘要中会给出发布时间，请在输出的翻译结果中将其转换为北京时间（+0800）。";



    String trans(String toTrans) {
        String ans = "";
        Message message = new Message("qwen/qwen3-14b", toTrans);
        String json = JsonHandler.gson.toJson(message);
        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/chat/completions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            Content content = JsonHandler.gson.fromJson(response.body(), Content.class);
            System.out.println(content.getContent());
            ans = content.getContent();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";

    }
    public static void main(String[] args){

    }

}

class Content {
    String id;
    String object;
    String created;
    String model;
    List<Object> choices;
    Map<String, String> usage;
    Map<String, String> stats;
    String system_fingerprint;
    Content(){

    }

    String getContent(){
        Map<String, Object> tmp1 = (Map<String, Object>) this.choices.get(0);
        Map<String, Object> tmp2 = (Map<String, Object>) tmp1.get("message");
        String tmp3 =  tmp2.get("content").toString();

        return tmp3;//.split("</think>")[1];
    }
}

class Message {
    String model = "";
    List<Role> messages = new ArrayList<>();

    Message(String model, String content){
        Role system = new Role("system", TranslatorMy.system);
        this.model = model;
        Role user = new Role("user", content);
        messages.add(system);
        messages.add(user);
    }
}

class Role {
    String role = "";
    String content = "";

    Role(String id, String content){
        this.role = id;
        this.content = content;
    }
}
import com.google.common.io.Files;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class RssHandler {
    static File inputxml = new File("./phoronix_rss.xml");
    static DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
    String title = "";
    String link = "";
    String description = "";
    String pubDate = "";
    String creator = "";
    String Passage = "";
    String info = "";
    ArrayList<String> mainContent = new ArrayList<String>();
    ArrayList<String> extraContent = new ArrayList<>();
    timeMachine timeMachine = new timeMachine();

    public RssHandler() {
    }

    ArrayList<String> getContent(){
        try {
            DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputxml);
            doc.getDocumentElement().normalize();
            NodeList Passages = doc.getElementsByTagName("item");
            int limit = Passages.getLength()-1;
            for (int index = limit; index >= 0; index--)
            {
                Node nNode = Passages.item(index);
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    title = eElement.getElementsByTagName("title").item(0).getTextContent();
                    link = eElement.getElementsByTagName("link").item(0).getTextContent();
                    description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    pubDate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                    creator = eElement.getElementsByTagName("dc:creator").item(0).getTextContent();
                }
                if(timeMachine.getTime().before(timeMachine.String2Date(pubDate)))
                {
                    Passage = "#  " + title + "\n\n" +
                            description;

                    info =  timeMachine.pubdate(pubDate) + "\n\n" +
                            "作者： " + creator + "\n\n" +
                            link;
                    timeMachine.writeTime(timeMachine.String2Date(pubDate));
                    extraContent.add(info);
                    mainContent.add(Passage);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return mainContent;
    }
    public static void  main(String[] args){
        timeMachine test = new timeMachine();
//        System.out.println(test.getTime());
//        Date tmp = test.String2Date("Fri, 25 Jul 2025 12:53:11 -0400")
//        var sdf = new SimpleDateFormat("yyyy M ")
//        System.out.println(test.pubdate("Sat, 02 Aug 2025 11:50:00 -0400"));
    }
}

class timeMachine {
    File lastTime = new File("./lastTime.cfg");

    timeMachine(){

    }

    boolean touch() {
        try {
            Files.touch(lastTime);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    String pubdate(String pubdate){
        String[] times = pubdate.split(" ");
        String day = times[1];
        String month = times[2];
        String year = times[3];
        String clock = times[4];
        String tmp = year + month + day + clock;
        DateTimeFormatter str2date = DateTimeFormatter.ofPattern("yyyyMMMddHH:mm:ss").withLocale(Locale.ENGLISH).withZone(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-4)));
        ZonedDateTime tmp1 = ZonedDateTime.parse(tmp, str2date);
        ZonedDateTime tmp2 = tmp1.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter date2str = DateTimeFormatter.ofPattern("yyyy MMM dd EE HH:mm:ss").withLocale(Locale.CHINA);
        return date2str.format(tmp2);
    }

    Date String2Date(String pubDate) {
        String[] times = pubDate.split(" ");
        String day = times[1];
        String month = times[2];
        String year = times[3];
        String clock = times[4];
        String tmp = year + month + day + clock;
        DateFormat fmt = new SimpleDateFormat("yyyyMMMddHH:mm:ss", Locale.ENGLISH);
        try {
            return fmt.parse(tmp);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    void writeTime (Date time) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMMddHH:mm:ss", Locale.ENGLISH);
        String wtime = fmt.format(time);
        try {
            Files.write(wtime.getBytes(), lastTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    Date getTime() {
        try {
            Scanner in = new Scanner(lastTime);
            String time = in.nextLine();
            DateFormat fmt = new SimpleDateFormat("yyyyMMMddHH:mm:ss", Locale.ENGLISH);
            return fmt.parse(time);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}

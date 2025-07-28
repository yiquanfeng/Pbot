import com.google.common.io.Files;
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
    ArrayList<String> fullContent = new ArrayList<String>();
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
                            description + "\n\n\n"+
                            pubDate + "\n\n" +
                            "Written by " + creator + "\n\n" +
                            link;
                    timeMachine.writeTime(timeMachine.String2Date(pubDate));
                    fullContent.add(Passage);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return fullContent;
    }
    public static void  main(String[] args){
        timeMachine test = new timeMachine();
        System.out.println(test.getTime());
//        test.writeTime(test.String2Date("Fri, 25 Jul 2025 12:53:11 -0400"));
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

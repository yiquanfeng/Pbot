import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class RssHandler {
    static File inputxml = new File("/Users/spriple/Codes/Pbot/PyScraper/phoronix_rss.xml");
    static DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
    String title = "";
    String link = "";
    String description = "";
    String pubDate = "";
    String creator = "";
    String fullContent = "";
    int temp = 0;

    public RssHandler() {
    }

    String getContent(){
        try {
            DocumentBuilder dBuilder = dbfactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputxml);
            doc.getDocumentElement().normalize();
            NodeList Passages = doc.getElementsByTagName("item");
            for (int tmp=0;tmp< Passages.getLength();tmp++)
            {
                Node nNode = Passages.item(tmp);
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    title = eElement.getElementsByTagName("title").item(temp).getTextContent();
                    link = eElement.getElementsByTagName("link").item(temp).getTextContent();
                    description = eElement.getElementsByTagName("description").item(temp).getTextContent();
                    pubDate = eElement.getElementsByTagName("pubDate").item(temp).getTextContent();
                    creator = eElement.getElementsByTagName("dc:creator").item(temp).getTextContent();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        fullContent = "#  " + title + "\n\n" +
                    description + "\n\n"+
                    pubDate + "\n\n" +
                    "Written by " + creator + "\n\n" +
                    link;
        return fullContent;
    }
    public static void  main(String[] args){



    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Scraper {
    public static void  main(String[] args){
        WebDriver chrome = new ChromeDriver();
        chrome.get("https://www.baidu.com");

        WebElement box = chrome.findElement(By.id("kw"));
        box.sendKeys("Selenium key press", Keys.ENTER);
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        chrome.quit();
    }
}
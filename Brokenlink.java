package firstPacakage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Brokenlink {
	
    private static WebDriver driver = null;
    static String driverPath = ("E:\\Selenium\\chromedriver_win32\\chromedriver.exe");
    
    public static void main(String[] args)  {
    	
        String homePage = "http://kidshoot.com/orange/";
        String url = "";
        HttpURLConnection huc = null;
        int respCode = 200;
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        
     /* Dimension d = new Dimension(300,108);
        driver.manage().window().setSize(d);  */
        driver.manage().window().maximize();
        
        driver.get(homePage);
        
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        System.out.println("Total links are "+links.size());
        
        Iterator<WebElement> it = links.iterator();
        
        while(it.hasNext()){
            
            url = it.next().getAttribute("href");
            
            System.out.println(url);
        
            if(url == null || url.isEmpty()){
            System.out.println("URL is either not configured for anchor tag or it is empty"); 
              continue;
            }
            
           if(!url.startsWith(homePage)){
            	
            System.out.println("URL belongs to another domain, skipping it.");
                continue;
            } 
            
            try {
                huc = (HttpURLConnection)(new URL(url).openConnection());
                
                huc.setRequestMethod("HEAD");
                
                huc.connect();
                
                respCode = huc.getResponseCode();
                
                if(respCode >= 400){
                    System.out.println(url+" is a broken link");
                   
                }
                else{
                    System.out.println(url+" is a valid link");
                }
                    
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        driver.close();

    }
}


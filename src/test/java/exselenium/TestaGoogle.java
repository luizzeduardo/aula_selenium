package exselenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestaGoogle {

    protected WebDriver driver;
    
    @BeforeEach
    public void createDriver() {  
        driver = new ChromeDriver();
        driver.get("https://www.google.com.br");
    }   

    @Test
    public void test() {
        String keysToSend = "Auto test";
        
        WebElement element = driver.findElement(By.name("q"));
        
        element.sendKeys(keysToSend);
        element.submit();
        
        assertEquals(keysToSend + " - Pesquisa Google", driver.getTitle());
    }
    
    @AfterEach
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DiscountSystemTest {
    WebDriver driver;
    String login;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
    }

    public void login() {
        driver.get("https://sharelane.com/cgi-bin/create_account.py");
        driver.findElement(By.cssSelector("[type=submit]")).click();
        login = driver.findElement(By.xpath("//span/b")).getText();
    }

    @Test
    public void quantitytiLessThen10() {
        login();
        driver.get("https://sharelane.com/cgi-bin/main.py");
        driver.findElement(By.name("email")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys("1111");
        driver.findElement(By.cssSelector("[value=Login]")).click();
        driver.get("https://sharelane.com/cgi-bin/show_book.py?book_id=2");
        driver.findElement(By.cssSelector("[href='./add_to_cart.py?book_id=2']")).click();
        driver.get("https://sharelane.com/cgi-bin/shopping_cart.py");
        driver.findElement(By.name("q")).sendKeys("6");
        driver.findElement(By.cssSelector("[value=Update]")).click();
        assertEquals(driver.findElement(By.xpath("//tr[2]//td[7]")).getText(), "60");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }
}

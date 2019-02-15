import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class JavaTestingClass {
    public static void main(String args[]) {
        System.setProperty("webdriver.chrome.driver", "D:\\Иван Вадимович\\СТАЖИРОВКА\\seleniumweb\\driversSel\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://passport.yandex.ru");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        SignIn(driver, "Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
       // WriteMessage(driver);
       // System.out.println(driver.findElement(By.xpath("//div[@class = 'mail-Done-Title js-title-info']")).getText());
        //DeleteMessages(driver,"иван бурков");
    }

    public static String SignIn(WebDriver driver, String userEmail, String userPassword) {
        driver.findElement(By.id("passp-field-login")).sendKeys(userEmail);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.id("passp-field-passwd")).sendKeys(userPassword);
        driver.findElement(By.xpath("//button[@type='submit']")).click();


        String result = driver.findElement(By.xpath("//div[@class='personal__security-level']")).getText();
        driver.findElement(By.xpath("//span[text()='van.Burkov2043']")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/ul/ul/li[2]/a")).click();

        return result;
    }


    public static int LettersCount(WebDriver driver)
    {
        WebElement lastLetter = driver.findElement(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div[last()]"));
        for (int i=1;; i++)
        {
            String xpathLetter = "//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div["
                    + i + ']';
           WebElement letter  = driver.findElement(By.xpath(xpathLetter));
           if (lastLetter.getText().equals(letter.getText())) {
               return i;
           }
        }
    }
    public static void RemeberLetters(WebDriver driver) //ArrayList<String>
    {
        ArrayList<String> letters = new ArrayList<String>();
        driver.findElement(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']")).getSize();
        for (int i=1; i<6;i++)
        {
            String xpath = "//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div["
                    + i + ']';
            By letter  = By.xpath(xpath);
            String letterText = driver.findElement(letter).getText();
            letters.add(letterText);
        }
        for (String name : letters) {
            System.out.println(name);
        }

    }
    public static void DeleteMessages(WebDriver driver, String name)
    {
        int amountOfMessages = LettersCount(driver);
        for (int i = 1; i <= amountOfMessages; i++)
        {
            String xpath = "//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div["+ i +"]//span[@class='mail-MessageSnippet-FromText']";
            WebElement message = driver.findElement(By.xpath(xpath));
            if (message.getText().equals(name))
                driver.findElement(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div[" + i +"]//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']")).click();
        }
        driver.findElement(By.xpath("//span[contains(@class,'js-toolbar-item-title-delete')]")).click();
    }
    public static void WriteMessage(WebDriver driver)
    {
        By buttonWriteMessage = By.xpath("//span[@class='mail-ComposeButton-Text']");
        driver.findElement(buttonWriteMessage).click();
        By receiver = By.xpath("//div[@class='js-compose-field mail-Bubbles']");
        driver.findElement(receiver).sendKeys("utka.burkov@yandex.ru");
        By title = By.xpath("//input[contains(@class,'js-compose-field js-editor-tabfocus')]");
        driver.findElement(title).sendKeys("Тестирование");
        By textOfMessage = By.xpath("//div[contains(@class,'cke_show_borders')]");
        driver.findElement(textOfMessage).sendKeys("testing test");
        By buttonSend = By.xpath("//div[contains(@class,'Footer-Main')]//button[contains(@title, 'Отправить письмо')]");
        driver.findElement(buttonSend).click();
    }

    @Test
    private static void SigInTest()
    {
        WebDriver driver = new ChromeDriver();
        String trueResult = "Аккаунт надежно защищен";
        String methodResult = SignIn(driver,"Ivan.Burkov@yandex.ru", "mynalegkesnova333");
        Assert.assertEquals(trueResult,methodResult);
    }
}
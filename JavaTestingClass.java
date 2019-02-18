import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class JavaTestingClass {

    private static WebDriver driver;
    private static WebDriverWait wait;

    public static  void settingDriver()
    {
        System.setProperty("webdriver.chrome.driver", "D:\\Иван Вадимович\\СТАЖИРОВКА\\seleniumweb\\driversSel\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,10);
    }
    public static void main(String args[]) {
        settingDriver();
        signIn( "Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        //settingLanguage("English");
        //settingLanguage("Русский");
       // writeMessage();
        deletingMessages("utka.burkov@yandex.ru");

       // driver.close();
    }
    public static void settingLanguage(String language)
    {
        By setting = By.xpath("//div[contains(@class, 'mail-Settings-Controls')]/a");
        driver.findElement(setting).click();
        driver.findElement(By.xpath("//span[@class='settings-popup-title-content']")).click();
        driver.findElement(By.xpath("//span[contains(@class, 'Settings-Lang_arrow')]")).click();
        By languageXpath = By.xpath("//div[@class = 'b-mail-dropdown__box__content']//*[contains(text(),'"+language+"')]");
        driver.findElement(languageXpath).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(setting));
        driver.navigate().to("https://mail.yandex.ru");
    }

    public static String signIn(String userEmail, String userPassword) {
        driver.get("https://passport.yandex.ru");
        driver.findElement(By.id("passp-field-login")).sendKeys(userEmail);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.id("passp-field-passwd")).sendKeys(userPassword);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        String result = driver.findElement(By.xpath("//div[@class='personal__security-level']")).getText();
        System.out.println(result);
        driver.findElement(By.xpath("//span[text()='van.Burkov2043']")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/ul/ul/li[2]/a")).click();


        return result;
    }


    public static int messagesCount() {
        WebElement lastLetter = driver.findElement(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div[last()]"));
        for (int i = 1; ; i++) {
            String xpathLetter = "//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div["
                    + i + ']';
            WebElement letter = driver.findElement(By.xpath(xpathLetter));
            if (lastLetter.getText().equals(letter.getText())) {
                return i;
            }
        }
    }

    public static ArrayList <String> deletingMessages(String email) {
        ArrayList <String> deleteedMessages = new ArrayList<String>();
        int amountOfMessages = messagesCount();
        for (int i = 1; i <= amountOfMessages; i++) {
            String xpath = "//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div[" + i + "]//span[@class='mail-MessageSnippet-FromText']";
            WebElement message = driver.findElement(By.xpath(xpath));
            if (message.getAttribute("title").equals(email))
                driver.findElement(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div[" + i + "]//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']")).click();
                deleteedMessages.add(message.getText());
        }
        driver.findElement(By.xpath("//span[contains(@class,'js-toolbar-item-title-delete')]")).click();
        return deleteedMessages;
    }

    public static void writeMessage() {
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
        driver.get("https://mail.yandex.ru");
    }


    @Test
    public void sigInTest() {
        settingDriver();
        String res = signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        String trueResult = "Аккаунт надёжно защищён";
        Assert.assertEquals(res, trueResult);
    }
    @Test
    public void deletingMessagesTest() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        ArrayList<String> deletedMessage = new ArrayList<String>();
        deletedMessage = deletingMessages("utka.burkov@yandex.ru");
        int amountOfMessages = messagesCount();
        for (int i = 1; i < amountOfMessages; i++) {
            String xpath = "//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div[" + i + "]//span[@class='mail-MessageSnippet-FromText']";
            WebElement message = driver.findElement(By.xpath(xpath));
            if (message.getText().equals(deletedMessage.get(0)))
                Assert.assertTrue(false);
        }
        Assert.assertTrue(true);
    }


}
    //привязываемся к тайтлу
    //wait.until
    //методы с маленькой буквы
    //глобальный вебдрайвер
    //настройки и вход на сайт сделать в отдельный метод
    //сложные действия на яндекс почте, лиюо аккуратно привязались

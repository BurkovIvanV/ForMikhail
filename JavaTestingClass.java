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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaTestingClass {

    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void settingDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\Иван Вадимович\\СТАЖИРОВКА\\seleniumweb\\driversSel\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    public static void main(String args[]) {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");



        //swithchOverLanguage();
        // deletingMessages("utka.burkov@yandex.ru");
        // sendMessage();
        // deletingMessages("utka.burkov@yandex.ru");

        // driver.close();
       // System.out.println(isEmailCorrect("utka.burkov@yandex.ru"));
    }


    public static String signIn(String userEmail, String userPassword) {
        driver.get("https://passport.yandex.ru");
        driver.findElement(By.id("passp-field-login")).sendKeys(userEmail);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.id("passp-field-passwd")).sendKeys(userPassword);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        String result = driver.findElement(By.xpath("//div[@class='personal__security-level']")).getText();

        driver.findElement(By.xpath("//span[text()='van.Burkov2043']")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/ul/ul/li[2]/a")).click();


        return result;
    }

    public static String swithchOverLanguage() {

        clickSettingButton();
        openLanguageList();
        By languageXpath = By.xpath("//div[@class='b-mail-dropdown__box__content']/div[1]");
        String newLanguage = driver.findElement(languageXpath).getText().trim();
        driver.findElement(languageXpath).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(setting));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//a[@class='b-setup-title__link']")).click();
        return newLanguage;
    }

    public static void clickSettingButton() {
        By setting = By.xpath("//div[contains(@class, 'mail-Settings-Controls')]/a");
        driver.findElement(setting).click();
        driver.findElement(By.xpath("//span[@class='settings-popup-title-content']")).click();
    }

    public static void openLanguageList() {
        driver.findElement(By.xpath("//span[contains(@class, 'Settings-Lang_arrow')]")).click();
    }

    public static ArrayList<WebElement> getMessagesOnPage() {
        ArrayList<WebElement> messagesOnPage = (ArrayList<WebElement>) driver.findElements(By.xpath(
                "//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div//span[@class='mail-MessageSnippet-FromText']"));
        return messagesOnPage;
    }


    public static ArrayList<WebElement> deletingMessages(String email) {
        ArrayList<WebElement> messagesOnPage = getMessagesOnPage();
        ArrayList<WebElement> deletedMessages;
        deletedMessages = (ArrayList<WebElement>) driver.findElements(By.xpath("//span[@title='" + email + "']"));
        for (WebElement message : messagesOnPage) {
            if (message.getAttribute("title").equals(email)) {
                int numberOfMessage = messagesOnPage.indexOf(message) + 1;
                driver.findElement(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']" +
                        "/div[" + numberOfMessage + "]//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']")).click();
                //break;
            }
        }
        return deletedMessages;
    }

    public static void clickDeleteMessageButton() {
        driver.findElement(By.xpath("//span[contains(@class,'js-toolbar-item-title-delete')]")).click();
    }


    public static void sendingMessageTrueEmail() {
        clickComposeButton();
        String email = "utka.burkov@yandex.ru";
        By buttonWriteMessage = By.xpath("//span[@class='mail-ComposeButton-Text']");
        driver.findElement(buttonWriteMessage).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='mail-ui-Link mail-ui-ComplexLink']")));
        By receiver = By.xpath("//div[@class = 'js-compose-field mail-Bubbles']");
        driver.findElement(receiver).sendKeys(email);
        By title = By.xpath("//input[contains(@class,'js-compose-field js-editor-tabfocus')]");
        driver.findElement(title).sendKeys("Тестирование");
        By textOfMessage = By.xpath("//div[contains(@class,'cke_show_borders')]");
        driver.findElement(textOfMessage).sendKeys("testing test");
        //driver.switchTo().alert().accept();
       // clickSendMessageButton();
    }

    public static void sendingMessageFalseEmail() {
        clickComposeButton();
        String email = "utka.burkovyandex.ru";
        By buttonWriteMessage = By.xpath("//span[@class='mail-ComposeButton-Text']");
        driver.findElement(buttonWriteMessage).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='mail-ui-Link mail-ui-ComplexLink']")));
        By receiver = By.xpath("//div[@class = 'js-compose-field mail-Bubbles']");

        driver.findElement(receiver).sendKeys(email);
        By title = By.xpath("//input[contains(@class,'js-compose-field js-editor-tabfocus')]");
        driver.findElement(title).sendKeys("Тестирование");
        By textOfMessage = By.xpath("//div[contains(@class,'cke_show_borders')]");
        driver.findElement(textOfMessage).sendKeys("testing test");
        //clickSendMessageButton();
        //driver.switchTo().alert().accept();
    }

    public static void clickSendMessageButton() {
        By buttonSend = By.xpath("//div[contains(@class,'Footer-Main')]//button[contains(@class, 'ui-button-text-only')]");
        driver.findElement(buttonSend).click();
    }
    public static void clickComposeButton(){
        By buttonSend = By.xpath("//span[@class='mail-ComposeButton-Text']");
        driver.findElement(buttonSend).click();
    }

    public static boolean isElementExists(By xpathElement) {
        return driver.findElements(xpathElement).size() > 0;
    }

    public static boolean isEmailCorrect (String email) {
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Test
    public void sigInTest() {
        settingDriver();
        String res = signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        if (res.equals("Account is well protected") || res.equals("Аккаунт надёжно защищён"))
            Assert.assertTrue(true);
        else Assert.assertTrue(false);
    }


    @Test
    public static void switchOverLanguageTest() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");

        String trueLanguage = swithchOverLanguage();

        String currentLanguage =
                driver.findElement(By.xpath("//a[@class='mail-ui-Link mail-ui-ComplexLink']")).getAttribute("title").trim();
        Assert.assertEquals(trueLanguage, currentLanguage);
    }

    @Test
    public void deletingMessagesTestWithoutClick() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");

        ArrayList<WebElement> deletedMessages;
        ArrayList<WebElement> messagesOnPage;
        deletedMessages = deletingMessages("utka.burkov@yandex.ru");
        messagesOnPage = getMessagesOnPage();
        for (WebElement deletedMessage : deletedMessages) {
            if (messagesOnPage.contains(deletedMessage))
                Assert.assertTrue(false);
        }
        Assert.assertTrue(true);
    }
    @Test
    public static void deletingMessagesTestWithClick() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");

        ArrayList<WebElement> deletedMessages;
        ArrayList<WebElement> messagesOnPage;
        deletedMessages = deletingMessages("utka.burkov@yandex.ru");
        clickDeleteMessageButton();
        messagesOnPage = getMessagesOnPage();
        for (WebElement deletedMessage : deletedMessages) {
            if (messagesOnPage.contains(deletedMessage))
                Assert.assertTrue(false);
        }
        Assert.assertTrue(true);
    }

    @Test
    public static void sendingMessageTestWithTrueEmail() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");

        sendingMessageTrueEmail();
        String currentEmail = driver.findElement
                (By.xpath("//div[@class='js-compose-field mail-Bubbles mail-Bubbles-Sms']/span")).getAttribute("data-yabble-email");
        clickSendMessageButton();
        By titleInfoXpath = By.xpath("//div[@class='mail-Done-Title js-title-info']");
        boolean isEmailCorrect = isEmailCorrect(currentEmail);
        if (isElementExists(titleInfoXpath) && isEmailCorrect) {
            Assert.assertTrue(true);
        } else if (isEmailCorrect == false && isElementExists(By.xpath("//div[contains(@class,'mail-Compose-Field-Footnote_error')]"))) {
            Assert.assertTrue(true);
        } else Assert.assertTrue(false);
    }

    @Test
    public static void sendingMessageTestWithWrongEmail() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");

        sendingMessageFalseEmail();

        String currentEmail = driver.findElement
                (By.xpath("//div[@class='js-compose-field mail-Bubbles mail-Bubbles-Sms']/span")).getAttribute("data-yabble-email");
        clickSendMessageButton();
        By titleInfoXpath = By.xpath("//div[@class='mail-Done-Title js-title-info']");
        boolean isEmailCorrect = isEmailCorrect(currentEmail);

        if (isElementExists(titleInfoXpath)==true && isEmailCorrect==true) {
            Assert.assertTrue(true);
        } else if (isEmailCorrect == false && isElementExists(By.xpath("//div[contains(@class,'mail-Compose-Field-Footnote_error')]"))) {
            Assert.assertTrue(true);
        } else Assert.assertTrue(false);
    }


}
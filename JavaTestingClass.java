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

    public static String LANGUAGE_BEFORE;
    public static ArrayList<WebElement> DELETED_MESSAGES;
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void main(String args[]) {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
    }

    public static void settingDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    public static void signIn(String userEmail, String userPassword) {
        driver.get("https://passport.yandex.ru");
        driver.findElement(By.id("passp-field-login")).sendKeys(userEmail);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.id("passp-field-passwd")).sendKeys(userPassword);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//span[text()='van.Burkov2043']")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/ul/ul/li[2]/a")).click();
    }


    public static void switchOverLanguage() {
        LANGUAGE_BEFORE = getCurrentLanguage();
        driver.findElement(By.xpath("//div[@class='b-mail-dropdown__box__content']/div[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='b-setup-title__link']")));
    }

    public static void clickSettingButton() {
        By setting = By.xpath("//div[contains(@class, 'mail-Settings-Controls')]/a");
        driver.findElement(setting).click();
        driver.findElement(By.xpath("//span[@class='settings-popup-title-content']")).click();
    }

    public static void openLanguageList() {
        driver.findElement(By.xpath("//span[contains(@class, 'Settings-Lang_arrow')]")).click();
    }

    public static void tickMessages(String email) {
        int n = driver.findElements(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div")).size();
        for (int i = 1; i <= n; i++) {
            WebElement message = driver.findElement(By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div["
                    + i + "]//span[@class=\"mail-MessageSnippet-FromText\"]"));
            if (message.getAttribute("title").equals(email)) {
                driver.findElement(By.xpath("//div[contains(@class,'js-messages-list')]" +
                        "/div[" + i + "]//span[@class='_nb-checkbox-flag _nb-checkbox-normal-flag']")).click();
            }
        }
    }

    public static void clickDeleteMessageButton() {
        DELETED_MESSAGES = getTickedMessage();
        driver.findElement(By.xpath("//span[contains(@class,'js-toolbar-item-title-delete')]")).click();
    }


    public static void clickComposeButton() {
        By buttonSend = By.xpath("//span[@class='mail-ComposeButton-Text']");
        driver.findElement(buttonSend).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='mail-ui-Link mail-ui-ComplexLink']")));
    }

    public static void writingEmail(String email) {
        //By receiver = By.xpath("//div[@class = 'js-compose-field mail-Bubbles']");
        driver.findElement(By.xpath("//div[@class = 'js-compose-field mail-Bubbles']")).sendKeys(email);
    }

    public static void writingTitle(String title) {
        driver.findElement(By.xpath("//input[contains(@class,'js-compose-field js-editor-tabfocus')]"))
                .sendKeys(title);
    }

    public static void writingTextMessage(String text) {
        By textOfMessage = By.xpath("//div[contains(@class,'cke_show_borders')]");
        driver.findElement(textOfMessage).sendKeys("testing test");
    }


    public static void clickSendMessageButton() {
        By buttonSend = By.xpath("//div[contains(@class,'Footer-Main')]//button[contains(@class, 'ui-button-text-only')]");
        driver.findElement(buttonSend).click();
    }

    public static boolean isElementExists(By xpathElement) {
        return driver.findElements(xpathElement).size() > 0;
    }

    public static boolean isEmailCorrect(String email) {
        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getCurrentLanguage() {
        return driver.findElement(By.xpath
                ("//span[@class='b-selink__inner']/span[@class='b-selink__link mail-Settings-Lang']")).getText();
    }

    public static ArrayList<WebElement> getTickedMessage() {
        return (ArrayList<WebElement>) driver.findElements(
                By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div[contains(@class, 'is-checked')]"));
    }

    public static ArrayList<WebElement> getAllMessagesOnPage() {
        return (ArrayList<WebElement>) driver.findElements(
                By.xpath("//div[@class='ns-view-container-desc mail-MessagesList js-messages-list']/div"));
    }

    //@Step
    public static void checkSendingMessage(String email) {
        if (isElementExists(By.xpath("//div[contains(@class,'mail-Compose-Field-Footnote_error')]")) != isEmailCorrect(email))
            Assert.assertTrue(true);
        else if (isEmailCorrect(email) == false)
            Assert.fail("Email is invalid");
        else Assert.assertTrue(false);
    }

    public static void waitUntil(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public static void isEmailDeleted() {
        waitUntil("//a[@class='mail-ui-Link mail-ui-ComplexLink']");
        ArrayList<WebElement> allMesages = getAllMessagesOnPage();
        for (WebElement deletedMessage : DELETED_MESSAGES) {
            if (allMesages.contains(deletedMessage))
                Assert.assertTrue(false);
        }
        //Assert.assertTrue(true);
    }

    public static void isMessagesWereTicked() {
        ArrayList<WebElement> tickedMessage = getTickedMessage();
        waitUntil("//a[@class='mail-ui-Link mail-ui-ComplexLink']");
        ArrayList<WebElement> messageWhichMustTicked =
                (ArrayList<WebElement>) driver.findElements
                        (By.xpath("//span[@title='utka.burkov@yandex.ru']/../../../../../../../.."));

        if (messageWhichMustTicked.equals(tickedMessage))
            Assert.assertTrue(true);
        else
            Assert.fail("Delete message button was not pressed");
    }

    @Test
    public static void switchOverLanguageTest() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        clickSettingButton();
        openLanguageList();
        switchOverLanguage();
        Assert.assertNotEquals(getCurrentLanguage(), LANGUAGE_BEFORE);
    }

    @Test
    public static void sendingMessageTestWithTrueEmail() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        clickComposeButton();
        writingEmail("ivan.burkov2043@yandex.ru");
        writingTitle("Testing");
        clickSendMessageButton();
        checkSendingMessage("ivan.burkov2043@yandex.ru");
    }

    @Test
    public static void sendingMessageTestWithWrongEmail() {

        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        clickComposeButton();
        writingEmail("ivan.burkov2043@yandexru");
        writingTitle("Testing");
        clickSendMessageButton();
        checkSendingMessage("ivan.burkov2043@yandexru");
    }

    @Test
    public static void deletingMessagesTestWithClick() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        tickMessages("utka.burkov@yandex.ru");
        clickDeleteMessageButton();
        isEmailDeleted();
    }


    @Test
    public void sigInTest() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        Assert.assertTrue(isElementExists(By.xpath("//a[@class='mail-ui-Link mail-ui-ComplexLink']")));

    }

    @Test
    public void tickMessagesTest() {
        settingDriver();
        signIn("Ivan.Burkov2043@yandex.ru", "mynalegkesnova333");
        tickMessages("utka.burkov@yandex.ru");
        isMessagesWereTicked();
    }
}




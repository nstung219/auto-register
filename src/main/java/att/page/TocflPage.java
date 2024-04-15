package att.page;

import att.model.Input;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;
import att.utils.Util;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TocflPage {

    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected Input input;

    private final String URL = "https://tocfl.sc-top.org.tw/zoom/index.php?country=007";
    private final String HOME_PAGE = "https://tocfl.sc-top.org.tw/zoom/index.php";
    private final String USERNAME = "//*[@id=\"textfield\" and @name=\"uid\"]";
    private final String PASSWORD = "//*[@id=\"textfield2\" and @name=\"pwd\"]";
    private final String LOGIN = "//*[@id=\"button\" and @class=\"Login2\"]";
    // Replace test day and test location
    private final String TEST_LOCATION = "//tr[td/big/text()='%s']//option[text()[contains(.,'%s')]]";
    // Replace time slot
    private final String TEST_TIME = "//tr[td/text()[contains(., '%s')]]";
    // Replace band
    private final String TEST_BAND = TEST_TIME + "//input[@name=\"cat\" and @value = '%s']";
    // Replace language type
    private final String LANG_TYPE = TEST_TIME + "//input[@name=\"clang\" and @value = '%s']";
    private final String REGISTER_BUTTON = TEST_TIME + "//input[@type=\"button\"]";

    public void setup() {
        HashMap<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.password_manager_enabled", false);

        ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless=new");
        options.addArguments("--no-default-browser-check");
        options.setExperimentalOption("prefs", chromePreferences);
        driver.set(ThreadGuard.protect(new ChromeDriver(options)));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public void login() {
        getDriver().get(URL);
        sleep(2);
        findXpath(USERNAME).sendKeys(input.getUsername());
        findXpath(PASSWORD).sendKeys(input.getPassword());
        findXpath(LOGIN).click();
        sleep(2);
        getDriver().switchTo().alert().accept();
    }

    public void selectTestLocation() {
        toHomePage();
        findXpath(TEST_LOCATION).click();
        sleep(2);
        findXpath(TEST_BAND).click();
        findXpath(LANG_TYPE).click();
        findXpath(REGISTER_BUTTON).click();
    }

    public void toHomePage() {
        getDriver().get(HOME_PAGE);
    }

    public void tearDown() {
        getDriver().close();
        driver.remove();
    }

    private WebElement findXpath(String xpath) {
        return getDriver().findElement(By.xpath(xpath));
    }

    private void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

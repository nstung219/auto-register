package att.page;

import att.model.Input;
import att.model.TestModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;
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
    private final String NEXT_PAGE_NUMBER = "//a[contains(text(), \"下一步\")]";
    // Replace time slot
    private final String TEST_TIME = "//tr[td/text()[contains(., '%s')]]";
    // Replace band
    private final String TEST_BAND = TEST_TIME + "//input[@name=\"cat\" and @value = '%s']";
    // Replace language type
    private final String LANG_TYPE = TEST_TIME + "//input[@name=\"clang\" and @value = '%s']";
    private final String REGISTER_BUTTON = TEST_TIME + "//input[@type=\"button\" and contains(@value, \"報名\") and not(@disabled)]";
    private final String CONFIRM_IDENTITY_CHECKBOX = "//input[@type = \"checkbox\" and @name = \"cok\"]";
    private final String CONFIRM_IDENTITY_BTN = "//input[@type=\"button\" and contains(@value, \"確認報名資料正確\")]";
    private final String REGISTERED_TEST = "//a[text()[contains(., \"考生專區\")]]";

    public void setup(Input input) {
        this.input = input;
        HashMap<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.password_manager_enabled", false);

        ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless=new");
        options.addArguments("--no-default-browser-check");
        options.setExperimentalOption("prefs", chromePreferences);
        driver.set(ThreadGuard.protect(new ChromeDriver(options)));
        System.out.println(this.input);
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
        boolean registered = false;
        for (TestModel testModel : input.getTestModels()) {
            if (registered) {
                break;
            }
            toHomePage();
            String testLocation = String.format(TEST_LOCATION, testModel.getDay(), testModel.getLocation());
            String testTime = String.format(TEST_TIME, testModel.getSlot());
            String testBand = String.format(TEST_BAND, testModel.getSlot(), testModel.getBand());
            String languageType = String.format(LANG_TYPE, testModel.getSlot(), testModel.getLanguageType());
            String registerButton = String.format(REGISTER_BUTTON, testModel.getSlot());
            if (findXpath(testLocation) == null) {
                log("failed to find test location");
                log("test location: " + testLocation + "on day: " + testModel.getDay());
            }
            findXpath(testLocation).click();
            sleep(2);

            WebElement registerButtonElm = findXpath(registerButton);
            if (registerButtonElm == null) {
                continue;
            }
            findXpath(testTime).click();
            findXpath(testBand).click();
            findXpath(languageType).click();
            findXpath(registerButton).click();
            acceptAlert();
            registered = true;
        }
    }

    public void confirmIdentity() {
        findXpath(CONFIRM_IDENTITY_CHECKBOX).click();
        findXpath(CONFIRM_IDENTITY_BTN).click();
        acceptAlert();
        acceptAlert();
        log("registered sucessfully");
    }

    public void acceptAlert() {
        getDriver().switchTo().alert().accept();
    }
    public void toHomePage() {
        getDriver().get(HOME_PAGE);
    }

    public void tearDown() {
        getDriver().close();
        driver.remove();
    }

    private WebElement findXpath(String xpath) {
        try {
            return getDriver().findElement(By.xpath(xpath));
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void log(String message) {
        System.out.println(input.getUsername() + ": " + message);
    }
}

package att.page;

import att.exception.NoSuchElementException;
import att.model.Input;
import att.model.TestModel;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
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
    private final String FAILED_TO_LOGIN_MESSAGE = "Login Error!";
    private final String LOGOUT_BTN = "//a[@href = \"?logout=1\"]";

    public void setup(Input input) {
        this.input = input;
        HashMap<String, Object> chromePreferences = new HashMap<>();
        chromePreferences.put("profile.password_manager_enabled", false);

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless=new");
        options.addArguments("--incognito");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--ignore-certificate-errors");
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.50 Safari/537.36";
        options.addArguments("user-agent=" + userAgent);
//        options.addArguments("--start-maximized");
        options.setExperimentalOption("prefs", chromePreferences);
        driver.set(ThreadGuard.protect(new ChromeDriver(options)));
        System.out.println(this.input);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public void login() {
        getDriver().get(URL);
        sendKeys(USERNAME, input.getUsername());
        sendKeys(PASSWORD, input.getPassword());
        click(LOGIN, 5);
        acceptAlert();
    }

    public void logout() {
        click(LOGOUT_BTN);
        acceptAlert();
    }

    public void selectTestLocation() throws IOException {
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
            click(testLocation);
            try {
                findXpath(registerButton);
            } catch (NoSuchElementException e) {
                log(e.getMessage());
                continue;
            }
            click(testTime);
            click(testBand);
            click(languageType);
            click(registerButton);
            acceptAlert();
            registered = true;
        }
    }

    public void confirmRegister() {
        click(CONFIRM_IDENTITY_BTN, 5);
    }

    public void confirmIdentity() {
        findXpath(CONFIRM_IDENTITY_CHECKBOX).click();
        findXpath(CONFIRM_IDENTITY_BTN).click();
        acceptAlert();
        acceptAlert();
        log("registered sucessfully");
    }

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5) /*timeout in seconds*/);
        if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
            getDriver().switchTo().alert().accept();
        }
    }
    public void toHomePage() {
        getDriver().get(HOME_PAGE);
    }

    public void tearDown() {
        getDriver().quit();
        driver.remove();
    }

    private void click(String xpath) {
        findXpath(xpath).click();
    }

    private void click(String xpath, int time) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(time));
        if (wait.until(ExpectedConditions.visibilityOf(findXpath(xpath))) != null) {
            findXpath(xpath).click();
        }
    }

    private void sendKeys(String xpath, String keys) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        if (wait.until(ExpectedConditions.visibilityOf(findXpath(xpath))) != null) {
            findXpath(xpath).sendKeys(keys);
        }
    }

    private WebElement findXpath(String xpath) {
        try {
            return getDriver().findElement(By.xpath(xpath));
        } catch (Exception e) {
            throw new NoSuchElementException(xpath);
        }
    }

    public void screenShot(boolean passed) throws IOException {
        // Take screenshot
        String path = "succeeded";
        if (!passed) {
            path = "failed";
        }
        File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/" + path + "/" + input.getUsername() + ".png"));
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

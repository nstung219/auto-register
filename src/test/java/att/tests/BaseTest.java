package att.tests;

import org.testng.annotations.*;
import att.page.TocflPage;
import att.utils.DataLoader;

public class BaseTest extends TocflPage{
    private DataLoader dataLoader = new DataLoader();

    @BeforeMethod(alwaysRun = true)
    public void beforeClass() {
        System.out.println("Before Method Thread ID: " + Thread.currentThread().getId());
    }

    @Test(threadPoolSize = 1, invocationCount = 1)
    public void test() {
        input = dataLoader.getInput();
        setup();
        login();
        selectTestLocation();
        tearDown();
    }

    /**
     * This method runs after every test (including during parallel execution).
     */
    @AfterMethod(alwaysRun = true)
    public void afterClass() {
        System.out.println("After Method Thread ID: " + Thread.currentThread().getId());
    }
}

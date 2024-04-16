package att.tests;

import att.model.Input;
import att.model.TestModel;
import org.testng.annotations.*;
import att.page.TocflPage;
import att.utils.DataLoader;

import java.util.ArrayList;

public class BaseTest {
    private DataLoader dataLoader = new DataLoader();

//    @BeforeMethod(alwaysRun = true)
//    public void beforeClass() {
//        System.out.println("Before Method Thread ID: " + Thread.currentThread().getId());
//    }

    @DataProvider(name = "data", parallel = true)
    private Object[] generateData() {
        return dataLoader.generateData();
    }

    @Test(dataProvider = "data")
    public void test(Input input) {
        try {
            TocflPage tocflPage = new TocflPage();
            if (input == null) {
                return;
            }
            tocflPage.setup(input);
            tocflPage.login();
            try {
//                TestModel testModel = input.getRequiredTest();
//                selectTestLocation(testModel);
            } catch (Exception e) {
//                TestModel testModel = input.getOptionalTest();
//                selectTestLocation(testModel);
            }
        } finally {
//            tearDown();
        }
    }

    /**
     * This method runs after every test (including during parallel execution).
     */
//    @AfterMethod(alwaysRun = true)
//    public void afterClass() {
//        System.out.println("After Method Thread ID: " + Thread.currentThread().getId());
//    }
}

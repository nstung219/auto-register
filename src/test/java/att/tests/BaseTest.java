package att.tests;

import att.model.Input;
import att.model.TestModel;
import org.testng.annotations.*;
import att.page.TocflPage;
import att.utils.DataLoader;

import java.util.ArrayList;

public class BaseTest {
    private DataLoader dataLoader = new DataLoader();

    @DataProvider(name = "data", parallel = true)
    private Object[] generateData() {
        return dataLoader.generateData();
    }

    @Test(dataProvider = "data")
    public void test(Input input) {
        TocflPage tocflPage = new TocflPage();
        try {
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
            tocflPage.tearDown();
        }
    }
}

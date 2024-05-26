package att.tests;

import att.model.Input;
import att.model.TestModel;
import org.testng.annotations.*;
import att.page.TocflPage;
import att.utils.DataLoader;

import java.io.IOException;
import java.util.ArrayList;

public class BaseTest {
    private DataLoader dataLoader = new DataLoader();

    @DataProvider(name = "data", parallel = true)
    private Object[] generateData() {
        return dataLoader.generateData();
    }

    @Test(dataProvider = "data")
    public void test(Input input) throws Exception {
        TocflPage tocflPage = new TocflPage();
        try {
            if (input == null) {
                return;
            }
            tocflPage.setup(input);
            tocflPage.login();
            tocflPage.selectTestLocation();
            tocflPage.confirmRegister();
            tocflPage.confirmIdentity();
            tocflPage.screenShot(true);
            tocflPage.logout();
        } catch (Exception e) {
            tocflPage.screenShot(false);
            throw new Exception(e);
        } finally {
            tocflPage.tearDown();
        }
    }
}

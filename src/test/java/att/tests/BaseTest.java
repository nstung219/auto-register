package att.tests;

import att.model.Input;
import org.testng.annotations.*;
import att.page.TocflPage;
import att.utils.DataLoader;

public class BaseTest {
    private DataLoader dataLoader = new DataLoader();

    @DataProvider(name = "data", parallel = true)
    private Object[] generateData() {
        return dataLoader.generateData();
    }

    @Test(dataProvider = "data")
    public void test(Input input) throws Exception {
        TocflPage tocflPage = new TocflPage();
        tocflPage.setup(input);
        try {
            if (input.isInValid()) {
                return;
            }
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

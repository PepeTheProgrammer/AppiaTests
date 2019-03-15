package tests.loginTest;

import dataProviderClasses.ReadExcelFile;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.SetUpClass;
import tests.importEditSearchTest.UserActions;

import java.io.IOException;

public class LoginTest {

    private WebDriver driver;

    @BeforeClass
    public void setDriver(){
        driver = SetUpClass.webDriver();
    }

    @Test(dataProvider = "credentials", enabled = true)
    public void testLogin(String login, String password) throws InterruptedException {
        UserActions actions = new UserActions(driver);
        actions.appiaLogin(login, password);
        Thread.sleep(2000);
        actions.appiaLogout();
        Thread.sleep(3000);
    }


    @DataProvider(name = "credentials")
    public Object[][] credentialsData() throws IOException {
        ReadExcelFile config = new ReadExcelFile("/home/applitopia/Desktop/credentials.xls");

        int rows = config.getRowCount(0);

        Object[][] credentials = new Object[rows][2];

        for (int i = 0; i < rows; i++) {
            credentials[i][0] = config.getData(0, i, 0);
            credentials[i][1] = config.getData(0, i, 1);
        }
        return credentials;
    }
}

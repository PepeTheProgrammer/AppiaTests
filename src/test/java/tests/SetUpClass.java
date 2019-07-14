package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

public class SetUpClass {

    private static WebDriver driver;

    @BeforeSuite
    public static void setUp() throws InterruptedException {
        //	DesiredCapabilities caps = DesiredCapabilities.firefox();
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", false);
        driver = new FirefoxDriver(options);
        driver.get("http://127.0.0.1");
        driver.manage().window().maximize();
        Thread.sleep(6000);
        String title = driver.getTitle();
        Assert.assertEquals("Applitopia", title);
    }

    public static WebDriver webDriver(){
        return driver;
    }
}

package importEditSearchTest;



import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class tests
{
	private WebDriver driver;
	private UserActions actions;
	private String filePath;

	@BeforeClass
	public void setUp() throws InterruptedException
	{
		filePath = "/home/applitopia/workspace/AppiaTests/src/main/resources/demoFiles/finance/COMPANIES.csv";
	//	DesiredCapabilities caps = DesiredCapabilities.firefox();
		System.setProperty("webdriver.gecko.driver", "/home/applitopia/workspace/AppiaTests/src/main/resources/geckodriver");
		FirefoxOptions options = new FirefoxOptions();
		options.setCapability("marionette", false);
		driver = new FirefoxDriver(options);
		driver.get("http://127.0.0.1");
		driver.manage().window().maximize();
		actions = new UserActions();
		Thread.sleep(6000);
		actions.appiaLogin(driver, "Applitopia", "ma5t3rk3y");
	}


	@Test
	public void shouldReturnApplitopiaTitle()
	{
		String title = driver.getTitle();
		Assert.assertEquals("Applitopia", title);

	}

	@Test(priority = 1)
	public void shouldClickFiles() throws InterruptedException
	{
		actions.clickFiles(driver);
	}

	@Test(priority = 2, enabled = true)
	public void shouldRightClickOnTabSetContainer() throws InterruptedException
	{
		actions.rightClickAdd(driver);
	}

	@Test(priority = 3, dependsOnMethods = { "shouldRightClickOnTabSetContainer" }, enabled = true)
	public void shouldCreateDirectory() throws InterruptedException
	{
		actions.chooseDir(driver);
	}

	@Test(priority = 4, dependsOnMethods = { "shouldCreateDirectory" }, enabled = true)
	public void shouldEnterDirName() throws InterruptedException
	{
		actions.nameDir(driver);
	}

	@Test(priority = 5, enabled = true, dependsOnMethods = { "shouldEnterDirName" })
	public void shouldClickTestDir() throws InterruptedException
	{
		actions.openDir(driver);
	}

	@Test(priority = 6, dependsOnMethods = { "shouldClickTestDir" }, enabled = true)
	public void shouldRightClickUpload() throws InterruptedException
	{
		actions.rightClickUpload(driver);
	}

	@Test(priority = 7, dependsOnMethods = { "shouldRightClickUpload" }, enabled = true)
	public void shouldUploadFile() throws InterruptedException, AWTException
	{
		actions.uploadFiles(driver, filePath);
	}

	@Test(priority = 8, dependsOnMethods = { "shouldUploadFile" }, enabled = true)
	public void shouldClickOnTestDirTab() throws InterruptedException
	{
		actions.clickOnTestDirTab(driver);
	}

	@Test(priority = 9, dependsOnMethods = { "shouldClickOnTestDirTab" }, enabled = true)
	public void shouldOpenCompaniesFile() throws InterruptedException
	{
		actions.clickOnCompaniesFile(driver);
	}

	@Test(priority = 15, dependsOnMethods = { "shouldEnterDirName" }, enabled = true)
	public void deleteTestDir() throws InterruptedException
	{
		actions.deleteTestDir(driver);
	}

	@AfterClass
	public void closeDriver() throws InterruptedException
	{
		driver.close();
	}

}
package importEditSearchTest;



import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import reusableElements.tableFilesHandlers.DataSearchButtons;
import reusableElements.WindowButtons;
import reusableElements.tableFilesHandlers.TableCell;
import reusableElements.tableFilesHandlers.tableExceptions.NoSuchColumnException;

import java.awt.*;
import java.util.List;


public class tests {

	private WebDriver driver;
	private UserActions actions;
	private String filePath;

	@BeforeClass
	public void setUp() throws InterruptedException {
		filePath = "/home/applitopia/workspace/AppiaTests/src/test/resources/finance/COMPANIES.csv";
	//	DesiredCapabilities caps = DesiredCapabilities.firefox();
		System.setProperty("webdriver.gecko.driver", "geckodriver");
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
	public void shouldReturnApplitopiaTitle() {
		String title = driver.getTitle();
		Assert.assertEquals("Applitopia", title);

	}

	@Test(priority = 1)
	public void shouldClickFiles() throws InterruptedException {
		actions.clickFiles(driver);
		Thread.sleep(3000);
	/*	try {
			actions.deleteTestDir(driver);
			Thread.sleep(2000);
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			Thread.sleep(2000);
			actions.clickFiles(driver);
		}catch(Exception e){
		}
		*/
	}


/*	@Test(priority = 2, enabled = true)
	public void shouldRightClickOnTabSetContainer() throws InterruptedException {
		actions.rightClickAdd(driver);
	}

	@Test(priority = 3, dependsOnMethods = { "shouldRightClickOnTabSetContainer" }, enabled = true)
	public void shouldCreateDirectory() throws InterruptedException {
		actions.chooseDir(driver);
	}

	@Test(priority = 4, dependsOnMethods = { "shouldCreateDirectory" }, enabled = true)
	public void shouldEnterDirName() throws InterruptedException {
		actions.nameDir(driver);
	}
*/
	@Test(priority = 5, /*dependsOnMethods = { "shouldEnterDirName" }, */ enabled = true)
	public void shouldClickTestDir() throws InterruptedException
	{
		actions.openDir(driver);
	}
/*
	@Test(priority = 6, dependsOnMethods = { "shouldClickTestDir" }, enabled = true)
	public void shouldRightClickUpload() throws InterruptedException {
		actions.rightClickUpload(driver);
	}

	@Test(priority = 7, dependsOnMethods = { "shouldRightClickUpload" }, enabled = true)
	public void shouldUploadFile() throws InterruptedException, AWTException {
		actions.uploadFiles(driver, filePath);
	}
*/
	@Test(priority = 8/*, dependsOnMethods = { "shouldUploadFile" }*/, enabled = true)
	public void shouldClickOnTestDirTab() throws InterruptedException {
		actions.clickOnTestDirTab(driver);
		//Thread.sleep(30000);
	}

	@Test(priority = 9,/* dependsOnMethods = { "shouldClickOnTestDirTab" },*/ enabled = true)
	public void shouldOpenCompaniesFile() throws InterruptedException {
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
		Thread.sleep(2000);
		actions.clickFiles(driver);
		Thread.sleep(2000);
		actions.openDir(driver);
		Thread.sleep(2000);
		actions.clickOnCompaniesFile(driver);
		Thread.sleep(3000);
	}

	@Test(priority = 10, dependsOnMethods = {"shouldOpenCompaniesFile"})
	public void shouldClickFilterButton() throws InterruptedException {
		actions.getDataSearchButton(driver, DataSearchButtons.FILTER).click();
		Thread.sleep(3000);
	}

	@Test(priority = 11, dependsOnMethods = {"shouldClickFilterButton"}, enabled = true)
	public void shouldFilterData() throws InterruptedException, NoSuchColumnException {
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
		new TableCell(driver, "Company Name").insertValue("app");
		new TableCell(driver, "Enterprise value").insertValue(">50000");
		new TableCell(driver, "Price To Book").insertValue(">2").submit();

		Thread.sleep(5000);
		List<WebElement> records = driver.findElements(By.className("windowBody"));

		AssertJUnit.assertTrue(records.get(1).getText().contains("12 records"));
	}

	@AfterClass
	public void closeDriver() throws InterruptedException
	{
		/*try{
			actions.deleteTestDir(driver);
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			Thread.sleep(8000);
		}catch (Exception e){}
*/
		actions.appiaLogout(driver);
		Thread.sleep(2000);
		driver.close();
	}

}
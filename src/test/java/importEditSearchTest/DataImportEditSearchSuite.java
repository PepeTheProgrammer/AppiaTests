package importEditSearchTest;



import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import reusableElements.tableFilesHandlers.DataSearchButtons;
import reusableElements.WindowButtons;
import reusableElements.tableFilesHandlers.TableCell;
import reusableElements.tableFilesHandlers.tableExceptions.NoSuchColumnException;

import java.awt.*;
import java.util.List;


public class DataImportEditSearchSuite {

	private WebDriver driver;
	private UserActions actions;
	private String filePath;

	@BeforeSuite
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

	@BeforeClass
	public void createDirectoryAndUploadFile() throws InterruptedException, AWTException {
		String title = driver.getTitle();
		Assert.assertEquals("Applitopia", title);
		try{
			actions.deleteTestDir(driver);
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			Thread.sleep(8000);
		}catch (Exception e){
			System.out.println("INITIAL CLEANUP ERROR");
			e.printStackTrace();
		}

		actions.clickFiles(driver);
		Thread.sleep(3000);
		actions.rightClickAdd(driver);
		actions.chooseDir(driver);
		actions.nameDir(driver);
		actions.openDir(driver);
		actions.rightClickUpload(driver);
		actions.uploadFiles(driver, filePath);
		actions.clickOnTestDirTab(driver);
		Thread.sleep(30000);
	}

	@Test(priority = 1, enabled = true)
	public void openCompaniesFile() throws InterruptedException {
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
		Thread.sleep(2000);
		actions.clickFiles(driver);
		Thread.sleep(2000);
		actions.openDir(driver);
		Thread.sleep(2000);
		actions.clickOnCompaniesFile(driver);
		Thread.sleep(3000);
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
	}


	@Test(priority = 2, dependsOnMethods = {"openCompaniesFile"}, enabled = true)
	public void filterData() throws InterruptedException, NoSuchColumnException {
		actions.getDataSearchButton(driver, DataSearchButtons.FILTER).click();
		Thread.sleep(3000);
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
		try{
			actions.deleteTestDir(driver);
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			Thread.sleep(8000);
		}catch (Exception e){}

		actions.appiaLogout(driver);
		Thread.sleep(2000);
		driver.close();
	}

}
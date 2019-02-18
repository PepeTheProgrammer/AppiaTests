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
import reusableElements.tableFilesHandlers.DataSearchButton;
import reusableElements.WindowButtons;
import reusableElements.tableFilesHandlers.TableCell;
import reusableElements.tableFilesHandlers.TableTags;
import reusableElements.tableFilesHandlers.TableWindowTab;
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

	@BeforeClass(enabled = false)
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
        actions.getWindowButton(driver, WindowButtons.CLOSE).click();
        Thread.sleep(2000);
	}

	@Test(priority = 1, enabled = true)
	public void openCompaniesFile() throws InterruptedException {
		actions.clickFiles(driver);
		Thread.sleep(2000);
		actions.openDir(driver);
		Thread.sleep(2000);
		actions.clickOnCompaniesFile(driver);
		Thread.sleep(3000);
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
	}


	@Test(priority = 2, dependsOnMethods = {"openCompaniesFile"}, enabled = false)
	public void filterData() throws InterruptedException, NoSuchColumnException {
		new DataSearchButton(driver, "Filter").click();
		Thread.sleep(3000);
		new TableCell(driver, "Company Name").insertValue("app");
		new TableCell(driver, "Enterprise value").insertValue(">50000");
		new TableCell(driver, "Price To Book").insertValue(">2").submit();

		Thread.sleep(5000);
		List<WebElement> records = driver.findElements(By.className("windowBody"));

		AssertJUnit.assertTrue(records.get(1).getText().contains("12 records"));
	}

	@Test(priority = 3, enabled = false)
	public void createTags() throws InterruptedException {
		new TableWindowTab(driver, "Tags").click();
		Thread.sleep(2000);
		TableTags.cleanNumberOfTagsFromTop(driver, 3);
		TableTags.createTag(driver, "Buy", "green");
		Thread.sleep(3000);
		TableTags.createTag(driver, "Sell", "red");
		Thread.sleep(3000);
		TableTags.createTag(driver, "Watch", "yellow");
		Thread.sleep(3000);
		new DataSearchButton(driver, "Save Tag(s)").click();
	}

	@Test(priority = 4, /*dependsOnMethods = {"createTags"}, */enabled = true)
	public void tagFilteredRecords() throws InterruptedException, NoSuchColumnException {
		new TableWindowTab(driver, "Data").click();
		filterData();
		new TableWindowTab(driver, "Data").click();
		Thread.sleep(2000);
		new TableCell(driver, "Attachments").scrollForCell(false);
		Thread.sleep(1000);
		new TableWindowTab(driver, "Tags").click();
		new TableWindowTab(driver, "Data").click();
		new DataSearchButton(driver, "Edit Record").click();
		Thread.sleep(500);
		WebElement table = actions.markRecords(driver, 12);
		Thread.sleep(500);
		table.findElement(By.xpath("//tr[@role='listitem' and @aria-posinset='"+12+"']")).click();
		Thread.sleep(2000);
		driver.findElement(By.className("selectItemText")).click();
		Thread.sleep(1000);
		TableTags.tagMarkedRecords(driver, "Buy");
		Thread.sleep(5000);
	}

	@AfterClass
	public void closeDriver() throws InterruptedException
	{
	/*	try{
			actions.deleteTestDir(driver);
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			Thread.sleep(8000);
		}catch (Exception e){
			e.printStackTrace();
		}*/

		actions.appiaLogout(driver);
		Thread.sleep(2000);
		driver.close();
	}


}
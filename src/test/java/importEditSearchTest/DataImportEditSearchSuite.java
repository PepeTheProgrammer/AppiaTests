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
		actions.chooseTypeToAdd(driver, "Directory");
		actions.nameAddedFile(driver, "Testdir");
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

		Thread.sleep(2000);
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

	@Test(priority = 4, /*dependsOnMethods = {"createTags"}, */enabled = false)
	public void tagFilteredRecords() throws InterruptedException, NoSuchColumnException {
		new TableWindowTab(driver, "Data").click();
		filterData();
		new TableWindowTab(driver, "Data").click();
		new TableCell(driver, "Attachments").scrollForCell(false);
		Thread.sleep(1000);
		WebElement table = actions.markRecords(driver, 12);
		WebElement record = table.findElement(By.xpath("//tr[@role='listitem' and @aria-posinset='"+12+"']"));
		Thread.sleep(2000);
		new DataSearchButton(driver, "Edit Record").click();
		List<WebElement> cells = record.findElements(By.xpath("//td[contains(@class, 'cellSelected Selected')]"));
		cells.get(3).click();
		Thread.sleep(3000);
		table.findElement(By.xpath("//td[contains(@class, 'comboBoxItemPickerCell')]")).click();
	//	TableTags.tagMarkedRecords(driver, "Buy");
	//	Thread.sleep(5000);
		new DataSearchButton(driver, "Commit").click();
		new DataSearchButton(driver, "Exit").click();
	}
	@Test(priority = 5, enabled = false)
	public void createSql() throws InterruptedException {
		String sql = "select sector, sum(enterprise_value) sector_value from companies group by sector order by sector_value desc";
		actions.clickFiles(driver).openDir(driver).rightClickAdd(driver);
		actions.chooseTypeToAdd(driver, "SQL");
		driver.findElement(By.xpath("//textarea[contains(@class, 'textItem borderless fontsql')]")).sendKeys(sql);
		new DataSearchButton(driver, "Execute").click();
		Thread.sleep(2000);
		new TableWindowTab(driver, "Data 1").click();
		Thread.sleep(2000);
		List<WebElement> records = driver.findElements(By.className("windowBody"));

		AssertJUnit.assertTrue(records.get(3).getText().contains("10 records"));
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
		Thread.sleep(2000);
	}

	@Test(priority = 6, enabled = true)
	public void createForm() throws InterruptedException, NoSuchColumnException {
		actions.clickFiles(driver).openDir(driver).rightClickAdd(driver);
		actions.chooseTypeToAdd(driver, "Form");
		driver.findElements(By.className("formCell")).get(1).sendKeys("companies");
		new DataSearchButton(driver, "Save").click();
		Thread.sleep(2000);
		new TableCell(driver, "Schema").insertValue("DEMO").submit();
	}

	@AfterClass(enabled = false)
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
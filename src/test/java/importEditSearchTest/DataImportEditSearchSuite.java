package importEditSearchTest;



import dataProviderClasses.ReadExcelFile;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.*;
import reusableElements.tableFilesHandlers.*;
import reusableElements.WindowButtons;
import reusableElements.tableFilesHandlers.tableExceptions.NoSuchColumnException;

import java.awt.*;
import java.io.IOException;
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

	@Test(priority = 0, dataProvider = "credentials", enabled = false)
	public void testLogin(String login, String password) throws InterruptedException {
		actions.appiaLogin(driver, login, password);
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


	@Test(priority = 2, dependsOnMethods = {"openCompaniesFile"}, enabled = true)
	public void filterData() throws InterruptedException, NoSuchColumnException {
		new DataSearchButton(driver, "Filter").click();
		Thread.sleep(3000);
		new TableCell(driver, "Company Name").insertValue("app");
		new TableCell(driver, "Enterprise value").insertValue(">50000");
		new TableCell(driver, "Price To Book").insertValue(">2").submit();

		Thread.sleep(2000);
		List<WebElement> records = driver.findElements(By.className("windowBody"));
		WebElement windowBody = null;
		for (WebElement record:records) {
			if (record.getText().length()>5){
				windowBody = record;
			}
		}
		if(windowBody==null){
			throw new NoSuchElementException("windowBody");
		}
		AssertJUnit.assertTrue(windowBody.getText().contains("12 records"));
	}

	@Test(priority = 3, dependsOnMethods = {"openCompaniesFile","filterData"},enabled = true)
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

	@Test(priority = 4,dependsOnMethods = {"openCompaniesFile","filterData", "createTags"}, enabled = true)
	public void tagFilteredRecords() throws InterruptedException, NoSuchColumnException, AWTException {
		new TableWindowTab(driver, "Data").click();
		new TableCell(driver, "Attachments").scrollForCell(false);
		Thread.sleep(1000);
		new TableWindowTab(driver, "Data").click();
		actions.markRecords(driver, 1, 12);
		WebElement table = actions.getListTableElement(driver);
		WebElement record = table.findElement(By.xpath("//tr[@role='listitem' and @aria-posinset='"+12+"']"));
		Thread.sleep(2000);
		new DataSearchButton(driver, "Edit Record").click();
		List<WebElement> cells = record.findElements(By.xpath("//td[contains(@class, 'cellSelected Selected')]"));
		cells.get(3).click();
		Thread.sleep(3000);
		actions.getListTableElement(driver).findElement(By.cssSelector("div[class^='selectItemText']")).click();
		Thread.sleep(2000);
		TableTags.tagMarkedRecords(driver, "Buy");
		Thread.sleep(5000);
		new DataSearchButton(driver, "Commit").click();
		Thread.sleep(2000);
		new DataSearchButton(driver, "Exit").click();
	}

	@Test(priority = 5, enabled = true)
	public void createSql() throws InterruptedException {
		String sql = "select sector, sum(enterprise_value) sector_value from companies group by sector order by sector_value desc";
		actions.clickFiles(driver);
		actions.openDir(driver);
		Thread.sleep(3000);
		actions.rightClickAdd(driver);
		actions.chooseTypeToAdd(driver, "SQL");
		driver.findElement(By.xpath("//textarea[contains(@class, 'textItem borderless fontsql')]")).sendKeys(sql);
		new DataSearchButton(driver, "Execute").click();
		Thread.sleep(2000);
		new TableWindowTab(driver, "Data 1").click();
		Thread.sleep(2000);
		List<WebElement> records = driver.findElements(By.className("windowBody"));
		WebElement result = null;
		for (WebElement rec:records) {
			if(rec.getText().contains("records")){
				result = rec;
				break;
			}
		}
		AssertJUnit.assertTrue(result.getText().contains("10 records"));
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
		Thread.sleep(2000);
	}

	@Test(priority = 6, enabled = true)
	public void createForm() throws InterruptedException, NoSuchColumnException {
		try{
			actions.deleteFileFromTestdir(driver, "Companyform");
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
		}catch (Exception e){
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			e.printStackTrace();
		}
		actions.clickFiles(driver).openDir(driver).rightClickAdd(driver);
		actions.chooseTypeToAdd(driver, "Form");
		driver.findElement(By.cssSelector("input[name='FORM']")).sendKeys("companyForm");
		new DataSearchButton(driver, "Save").click();
		Thread.sleep(1000);
		new DataSearchButton(driver, "Table Lookup").click();
		Thread.sleep(3000);
		new TableCell(driver, "Schema").insertValue("DEMO").submit();
		new TableCell(driver, "Primary key").insertValue("RECORD").submit();
		driver.findElement(By.xpath("//div[text()='Discovered']")).click();
		Thread.sleep(1000);
		actions.markRecords(driver, 2, 5);
		Thread.sleep(1000);
		List<WebElement> toolstrips = driver.findElements(By.className("toolStrip"));
		toolstrips.get(toolstrips.size()-1).click();
		List<WebElement> returnDataButtons = new DataSearchButton(driver, "Return Data").getButtonList();
		returnDataButtons.get(returnDataButtons.size()-1).click();
		Thread.sleep(20000);
		new DataSearchButton(driver, "Save Record").click();
		Thread.sleep(1000);
		new DataSearchButton(driver, "Create Form").click();

	}
	@AfterClass(enabled = false)
	public void closeDriver() throws InterruptedException
	{
	/*	try{
			actions.deleteFile(driver);
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			Thread.sleep(8000);
		}catch (Exception e){
			e.printStackTrace();
		}*/

		actions.appiaLogout(driver);
		Thread.sleep(2000);
		driver.close();
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
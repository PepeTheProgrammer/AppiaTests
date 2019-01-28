package importEditSearchTest;



import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import reusableElements.DataSearchButtons;
import reusableElements.NestedElements;
import reusableElements.WindowButtons;

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
		}*/
	}


/*	@Test(priority = 2, enabled = true)
	public void shouldRightClickOnTabSetContainer() throws InterruptedException {
		actions.rightClickAdd(driver);
	}*/
/*
	@Test(priority = 3, dependsOnMethods = { "shouldRightClickOnTabSetContainer" }, enabled = true)
	public void shouldCreateDirectory() throws InterruptedException {
		actions.chooseDir(driver);
	}

	@Test(priority = 4, dependsOnMethods = { "shouldCreateDirectory" }, enabled = true)
	public void shouldEnterDirName() throws InterruptedException {
		actions.nameDir(driver);
	}
*/
	@Test(priority = 5,/* dependsOnMethods = { "shouldEnterDirName" }, */ enabled = true)
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

	@Test(priority = 8, dependsOnMethods = { "shouldUploadFile" }, enabled = true)
	public void shouldClickOnTestDirTab() throws InterruptedException {
		actions.clickOnTestDirTab(driver);
		Thread.sleep(30000);
	}
*/
	@Test(priority = 9,/* dependsOnMethods = { "shouldClickOnTestDirTab" }, */enabled = true)
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
	public void shouldFilterData() throws InterruptedException {
		actions.getWindowButton(driver, WindowButtons.CLOSE).click();
		Actions selActions = new Actions(driver);
		By cellInput = By.className("textItem");
		List<WebElement> numberOfRecords = driver.findElements(By.className("listTable"));
		List<WebElement> editorCells =  numberOfRecords.get(numberOfRecords.size()-1).findElements(cellInput);
		System.out.println(editorCells.size());
		editorCells.get(4).sendKeys("services");
		List<WebElement> scrolls = driver.findElements(By.className("hScrollThumb"));
		WebElement scroll = scrolls.get(scrolls.size()-1);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView()", editorCells.get(8));
		editorCells.get(8).sendKeys(">4000000");
		editorCells.get(8).sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		List<WebElement> records = driver.findElements(By.className("windowBody"));

		AssertJUnit.assertTrue(records.get(1).getText().contains("214 records"));
	}

	@AfterClass
	public void closeDriver() throws InterruptedException
	{
		/*try{
			actions.deleteTestDir(driver);
			actions.getWindowButton(driver, WindowButtons.CLOSE).click();
			Thread.sleep(8000);
		}catch (Exception e){}*/

		actions.appiaLogout(driver);
		Thread.sleep(2000);
		driver.close();
	}

}
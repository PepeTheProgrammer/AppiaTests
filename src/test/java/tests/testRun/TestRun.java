package tests.testRun;



import dataProviderClasses.MethodInvocation;
import dataProviderClasses.ReadXmlFile;
import dataProviderClasses.dataObjects.TestSuite;
import dataProviderClasses.fileGenerators.ResultsXmlGenerator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import tests.SetUpClass;

import java.awt.*;
import java.io.File;
import java.util.List;


public class TestRun {

	private WebDriver driver;
	private UserActions actions;
	private String filePath;
	private MethodInvocation invocation;

/**
 * to run tests from file run the methods as below.
 */

	@Test(priority = 1, enabled = true)
	public void readTestsFromFile() throws InterruptedException, AWTException {
		SetUpClass.setUp();
		try {
			List<TestSuite> suites = ReadXmlFile.readTestSuite(new File("/home/applitopia/IdeaProjects/AppiaTests/src/test/resources/exampleTest.xml"));
			for (TestSuite suite: suites) {
				suite.execute();
			}
			ResultsXmlGenerator generator =new ResultsXmlGenerator();
			generator.generateResultsXmlFile(suites);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/*  Deprecated - old format selenium tests
	@Test(priority = 2, enabled = true)
	public void openCompaniesFile() throws InterruptedException {
		actions.clickFiles();
		Thread.sleep(2000);
		actions.openDir();
		Thread.sleep(2000);
		actions.clickOnCompaniesFile();
		Thread.sleep(3000);
		actions.getWindowButton(WindowButtons.CLOSE).click();
	}


	@Test(priority = 3, dependsOnMethods = {"openCompaniesFile"}, enabled = true)
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

	@Test(priority = 4, dependsOnMethods = {"openCompaniesFile","filterData"},enabled = true)
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

	@Test(priority = 5,dependsOnMethods = {"openCompaniesFile","filterData", "createTags"}, enabled = true)
	public void tagFilteredRecords() throws InterruptedException, NoSuchColumnException, AWTException {
		new TableWindowTab(driver, "Data").click();
		new TableCell(driver, "Attachments").scrollForCell(false);
		Thread.sleep(1000);
		new TableWindowTab(driver, "Data").click();
		actions.markRecords(1, 12);
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

	@Test(priority = 6, enabled = true)
	public void createSql() throws InterruptedException {
		String sql = "select sector, sum(enterprise_value) sector_value from companies group by sector order by sector_value desc";
		actions.clickFiles();
		actions.openDir();
		Thread.sleep(3000);
		actions.rightClickAdd();
		actions.chooseTypeToAdd("SQL");
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
		actions.getWindowButton(WindowButtons.CLOSE).click();
		actions.getWindowButton(WindowButtons.CLOSE).click();
		Thread.sleep(2000);
	}

/*	@Test(priority = 7, enabled = true)
	public void createForm() throws InterruptedException, NoSuchColumnException {
		try{
			actions.deleteFileFromTestdir("Companyform");
			actions.getWindowButton(WindowButtons.CLOSE).click();
		}catch (Exception e){
			actions.getWindowButton(WindowButtons.CLOSE).click();
			e.printStackTrace();
		}
		actions.clickFiles().openDir().rightClickAdd();
		actions.chooseTypeToAdd("Form");
		driver.findElement(By.cssSelector("input[name='FORM']")).sendKeys("companyForm");
		new DataSearchButton(driver, "Save").click();
		Thread.sleep(1000);
		new DataSearchButton(driver, "Table Lookup").click();
		Thread.sleep(3000);
		new TableCell(driver, "Schema").insertValue("DEMO").submit();
		new TableCell(driver, "Primary key").insertValue("RECORD").submit();
		driver.findElement(By.xpath("//div[text()='Discovered']")).click();
		Thread.sleep(2000);
		actions.markRecords(2, 5);
		Thread.sleep(1000);
		List<WebElement> toolstrips = driver.findElements(By.className("toolStrip"));
		toolstrips.get(toolstrips.size()-1).click();
		List<WebElement> returnDataButtons = new DataSearchButton(driver, "Return Data").getButtonList();
		returnDataButtons.get(returnDataButtons.size()-1).click();
		Thread.sleep(5000);
		new DataSearchButton(driver, "Save Record").click();
		Thread.sleep(1000);
		new DataSearchButton(driver, "Create Form").click();
		Thread.sleep(2000);
		actions.getWindowButton(WindowButtons.CLOSE).click();
	}

	@AfterClass(enabled = true)
	public void closeDriver() throws InterruptedException
	{
		try {
			actions.appiaLogout();
		}catch (Exception e){
			e.printStackTrace();
		}
		Thread.sleep(2000);
		driver.close();
	}

*/

}
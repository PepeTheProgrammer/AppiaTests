package importEditSearchTest;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import reusableElements.tableFilesHandlers.DataSearchButton;
import reusableElements.NestedElements;
import reusableElements.Selectors;
import reusableElements.WindowButtons;
import reusableElements.tableFilesHandlers.TableWindowTab;

public class UserActions
{

	private String testName = "Testdir";

	// private By testDir = Selectors.getByFileName(testName);

	public UserActions appiaLogin(WebDriver driver, String username, String password) throws InterruptedException
	{
		WebElement login = driver.findElement(Selectors.LOGIN);
		login.click();
		Thread.sleep(1000);
		login = driver.findElement(Selectors.usernameInput);
		login.sendKeys(username);
		login = driver.findElement(Selectors.passwordInput);
		login.sendKeys(password);
		login.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		return this;
	}

	public UserActions appiaLogout(WebDriver driver) throws InterruptedException
	{
		driver.findElement(Selectors.LOGIN).click();
		Thread.sleep(3000);
		clickYesButton(driver);
		return this;
	}

	public UserActions clickFiles(WebDriver driver) throws InterruptedException
	{
		WebElement element = driver.findElement(Selectors.files);
		element.click();
		Thread.sleep(4000);
		return this;
	}

	private Actions rightClickElement(WebDriver driver, WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 20);
		Actions action = new Actions(driver);
		String dropdown = Keys.chord(Keys.SHIFT, Keys.F10);
		//wait.until(ExpectedConditions.visibilityOf(element));
        //action.sendKeys(element, dropdown).build().perform();
		action.contextClick(element).build().perform();
		return action;
	}

	private Actions rightClick(WebDriver driver)
	{
		Actions actions = new Actions(driver);
		String dropdown = Keys.chord(Keys.SHIFT, Keys.F10);
		actions.sendKeys(dropdown);
		return actions;
	}

	private void maximizeWindow(WebDriver driver){
		WebElement button = driver.findElement(Selectors.MAXIMIZE_BUTTON);
		button.click();
	}

	private Actions rightClickOnWindow(WebDriver driver) throws InterruptedException
	{
		maximizeWindow(driver);
		Thread.sleep(4000);
		WebElement filesTab = null;
		List<WebElement> tabs =  driver.findElements(Selectors.FILES_CONTAINER);
        List<WebElement> tabsetContainers =  driver.findElements(Selectors.tabSetContainer);
		for (WebElement el: tabsetContainers) {
			if(el.getText().length()>8){
				filesTab = el;
				break;
			}
		}
		if(filesTab==null){
			for (WebElement el: tabsetContainers) {
				if(el.getText().length()>1){
					filesTab = el;
					break;
				}
			}
		}
		Actions action = new Actions(driver);
		rightClickElement(driver, filesTab);
		Thread.sleep(3000);

		return action;
	}

	public UserActions rightClickAdd(WebDriver driver) throws InterruptedException
	{
		Actions action = rightClickOnWindow(driver);
		action.sendKeys(Keys.ARROW_DOWN).perform();
		Thread.sleep(2000);
		action.sendKeys(Keys.ENTER).perform();
		Thread.sleep(2000);
		return this;
	}

	public UserActions chooseTypeToAdd(WebDriver driver, String type) throws InterruptedException
	{
		WebElement element = driver.findElement(By.xpath("//nobr[text()='"+type+"']"));
		element.click();

		Thread.sleep(3000);
		return this;
	}

	public UserActions nameAddedFile(WebDriver driver, String name) throws InterruptedException
	{
		// WebElement element = driver.findElement(Selectors.dirNameInput);
		Actions action = new Actions(driver);
		action.sendKeys(name).perform();
		Thread.sleep(2000);
		action.sendKeys(Keys.ENTER).perform();
		Thread.sleep(3000);
		return this;
	}

	public UserActions openDir(WebDriver driver) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement testdir = NestedElements.getNestedElementByText(driver, Selectors.TILES, "Testdir");
		if(testdir==null){
			testdir = NestedElements.getNestedElementByText(driver, Selectors.TILE_OVER, "Testdir");
		}
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOf(testdir));
		testdir.click();
		Thread.sleep(5000);
		return this;
	}


	public UserActions rightClickUpload(WebDriver driver) throws InterruptedException
	{
		Actions action = rightClickOnWindow(driver);
		pushKey(action, Keys.ARROW_DOWN, 4).sendKeys(Keys.ENTER).perform();
		Thread.sleep(3000);
		return this;
	}

	public void uploadFiles(WebDriver driver, String filePath) throws InterruptedException, AWTException
	{
		WebElement iframe = driver.findElement(Selectors.gwtFrame);
		driver.switchTo().frame(iframe);
		WebElement innerBody = driver.findElement(By.id("clickable"));
		innerBody.click();
		RobotUpload.uploadFile(filePath);
		Thread.sleep(10000);
		driver.switchTo().defaultContent();

	}

	public Actions pushKey(Actions action, Keys key, int nTimes)
	{
		for (int i = 0; i < nTimes; i++)
		{
			action.sendKeys(key);
		}
		return action;
	}


	public void deleteTestDir(WebDriver driver) throws InterruptedException
	{
		Actions actions = new Actions(driver);
		clickFiles(driver);
		Thread.sleep(2000);
		WebElement testdir = NestedElements.getNestedElementByText(driver, Selectors.TILES, "Testdir");
		actions.contextClick(testdir).perform();
		Thread.sleep(2000);
		pushKey(actions, Keys.ARROW_DOWN, 8).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		clickYesButton(driver);
		Thread.sleep(5000);
	}

	public void deleteFileFromTestdir(WebDriver driver, String filename) throws InterruptedException {
		Actions actions = new Actions(driver);
		clickFiles(driver);
		Thread.sleep(2000);
		openDir(driver);
		Thread.sleep(2000);
		WebElement testdir = NestedElements.getNestedElementByText(driver, Selectors.TILES, filename);
		actions.contextClick(testdir).perform();
		Thread.sleep(2000);
		pushKey(actions, Keys.ARROW_DOWN, 8).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		clickYesButton(driver);
		Thread.sleep(5000);
	}

	public void clickYesButton(WebDriver driver)
	{
		WebElement element = driver.findElement(Selectors.yesButton);
		element.click();
	}

	private void clickOnTab(String name, WebDriver driver)
	{
		WebElement tab = driver.findElement(Selectors.getByTabName(name));
		tab.click();
	}

	public void clickOnTestDirTab(WebDriver driver) throws InterruptedException
	{
		this.clickOnTab(this.testName, driver);
		Thread.sleep(2000);
	}

	public void clickOnCompaniesFile(WebDriver driver) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(Selectors.companiesFile)));
		WebElement companiesFile = driver.findElement(Selectors.companiesFile);
		companiesFile.click();
	}

	public WebElement getWindowButton(WebDriver driver, WindowButtons windowButton){
		List<WebElement> buttons = driver.findElements(By.xpath("//div[contains(@role, 'button') and contains(@onscroll, '" + windowButton.getValue() + "')]"));
		for (WebElement button: buttons) {
			if(button.isDisplayed())
				return button;
		}
		return null;
	}


	public WebElement markRecords(WebDriver driver, int firstRecord, int lastRecord) throws InterruptedException {
		WebElement table = getListTableElement(driver);
		if(firstRecord>1) {
			table.findElement(By.xpath("//tr[@role='listitem' and @aria-posinset='" + firstRecord + "']")).click();
			Thread.sleep(1500);
		}
		WebElement lastRecordElement = table.findElement(By.xpath("//tr[@role='listitem' and @aria-posinset='"+lastRecord+"']"));
		if(!lastRecordElement.isDisplayed()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView()", lastRecordElement);
		}
		new Actions(driver).keyDown(Keys.SHIFT).click(lastRecordElement).keyUp(Keys.SHIFT).build().perform();
		return table;//last record changed after selection, so it must be located again
	}
	 public WebElement getListTableElement(WebDriver driver){
		List<WebElement> tables = driver.findElements(By.className("listTable"));
		 for (WebElement t:tables) {
			 if(t.getText().length()>10){
			 	return t;
			 }
		 }
		 throw new NoSuchElementException("listTable");
	 }
}
package importEditSearchTest;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserActions
{

	private String testName = "Testdir";

	// private By testDir = Selectors.getByFileName(testName);

	public UserActions appiaLogin(WebDriver driver, String username, String password) throws InterruptedException
	{
		WebElement login = driver.findElement(Selectors.LOGIN);
		login.click();
		login = driver.findElement(Selectors.usernameInput);
		login.sendKeys(username);
		login = driver.findElement(Selectors.passwordInput);
		login.sendKeys(password);
		login.sendKeys(Keys.ENTER);
		Thread.sleep(3000);
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
		wait.until(ExpectedConditions.visibilityOf(element));
		action.sendKeys(element, dropdown).build().perform();
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
		WebElement filesTab = driver.findElement(Selectors.FILES_CONTAINER);
		Actions action = new Actions(driver);
		rightClickElement(driver, filesTab);
		Thread.sleep(3000);

		return action;
	}

	public UserActions rightClickAdd(WebDriver driver) throws InterruptedException
	{
		Actions action = rightClickOnWindow(driver);
		action.sendKeys(Keys.ARROW_DOWN).perform();

		action.sendKeys(Keys.ENTER).perform();
		Thread.sleep(2000);
		return this;
	}

	public UserActions chooseDir(WebDriver driver) throws InterruptedException
	{
		WebElement element = driver.findElement(Selectors.directory);
		element.click();

		Thread.sleep(3000);
		return this;
	}

	public UserActions nameDir(WebDriver driver) throws InterruptedException
	{
		// WebElement element = driver.findElement(Selectors.dirNameInput);
		Actions action = new Actions(driver);
		action.sendKeys(this.testName).perform();
		Thread.sleep(1000);
		action.sendKeys(Keys.ENTER).perform();
		return this;
	}

	public UserActions openDir(WebDriver driver) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(Selectors.testDir)));
		WebElement testDir = driver.findElement(Selectors.testDir);
		testDir.click();
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

	private Actions pushKey(Actions action, Keys key, int nTimes)
	{
		for (int i = 0; i < nTimes; i++)
		{
			action.sendKeys(key);
		}
		return action;
	}

	private WebElement findElementByName(WebDriver driver, By selector, String name)
	{
		List<WebElement> elements = driver.findElements(selector);
		for (WebElement el : elements)
		{
			if (el.getText().equals(name))
			{
				return el;
			}
		}
		return null;
	}

	private void clickWindowButton(WebDriver driver, int fromLeft) throws InterruptedException
	{
		List<WebElement> buttons = driver.findElement(Selectors.windowHeader).findElements(Selectors.imgButton);
		buttons.get(fromLeft - 1).click();
		Thread.sleep(3000);
	}

	public void deleteTestDir(WebDriver driver) throws InterruptedException
	{
		WebElement closeButton = driver.findElement(Selectors.CLOSE_BUTTON);
		closeButton.click();
		Thread.sleep(2000);
		clickFiles(driver);
		Thread.sleep(2000);
		WebElement element = driver.findElement(Selectors.testDir);
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		Thread.sleep(2000);
		action.contextClick().build().perform();
		Thread.sleep(2000);
		pushKey(action, Keys.ARROW_DOWN, 6).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		clickYesButton(driver);
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

}
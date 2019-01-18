package reusableElements;

import org.openqa.selenium.By;

public class Selectors {

	public static final By files = By.cssSelector("div[eventproxy=apiaDesktopFilesButton]");

	public static final By FILES_TAB = By.cssSelector("div[role=tab][aria-label=Files]");

	public static final By TILES = By.cssSelector("div[class^='simpleTile']");

	public static final By TILE_OVER = By.cssSelector("div[class^='simpleTileOver']");

	public static final By BUTTONS = By.className("imgButton");

	public static final By MAXIMIZE_BUTTON = By.cssSelector("div[eventproxy=isc_FileBrowser_0_maximizeButton]");

	public static final By CLOSE_BUTTON = By.xpath("//div[contains(@role, 'button') and contains(@onscroll, 'closeButton')]");

	public static final By tabSetContainer = By.className("tabSetContainer");

	public static final By FILES_CONTAINER = By.cssSelector("div[eventproxy=isc_HighTabSet_1_paneContainer]");

	public static final By dirNameInput = By.className("formCell");

	public static final By testDir = By.xpath("//div[contains(@title, 'Testdir')]");

	public static final By clickableUploadField = By.xpath("//iframe");

	public static final By gwtFrame = By.className("gwt-Frame");

	public static final By directory = By.xpath("//nobr[text()='Directory']");

	public static final By LOGIN = By.cssSelector("div[eventproxy=apiaDesktopLoginButton]");

	public static final By usernameInput = By.name("Username");

	public static final By passwordInput = By.name("Password");

	public static final By windowHeader = By.className("windowHeader");

	public static final By imgButton = By.className("imgButton");

	public static final By yesButton = By.xpath("//div[contains(@role, 'button') and contains(., 'Yes')]");

	public static final By selectedTile = By.className("tileValueSelectedOver");

	public static final By FILTER_BUTTON = By.className("tileValueSelectedOver");

	public static final By companiesFile = By.xpath("//div[contains(@title, 'COMPANIES')]");
	public static By getByTabName(String tabName)
	{
		return By
				.xpath("html/body/div[2]/div/div[3]/div[2]/div/div/div[1]/div[1]//div[contains(., '" + tabName + "')]");
	}


}
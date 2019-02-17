package reusableElements.tableFilesHandlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DataSearchButton {

    private String buttonLabel;
    private WebDriver driver;
    private By buttonSelector;

    public DataSearchButton(WebDriver driver, String buttonLabel) {
        this.buttonLabel = buttonLabel;
        this.driver = driver;
        this.buttonSelector = By.xpath("//div[contains(@role, 'button') and @aria-label='" + buttonLabel + "']");
    }

    public void click(){
        List<WebElement> buttons = driver.findElements(buttonSelector);
        for (WebElement button: buttons) {
            if(button.isDisplayed())
                button.click();
        }
    }
}

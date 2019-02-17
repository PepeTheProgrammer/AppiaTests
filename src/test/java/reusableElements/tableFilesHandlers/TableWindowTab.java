package reusableElements.tableFilesHandlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TableWindowTab {
    private String tabName;
    private By tabSelector;
    private WebDriver driver;

    public TableWindowTab(WebDriver driver, String tabName) {
        this.tabName = tabName;
        this.tabSelector = By.xpath("//td[text()='"+tabName+"']");
        this.driver = driver;
    }

    public TableWindowTab(WebDriver driver) {
        this.driver = driver;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
        this.tabSelector = By.xpath("//td[text()='"+tabName+"']");
    }

    public void click(){
        driver.findElement(tabSelector).click();
    }
}

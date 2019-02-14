package reusableElements.tableFilesHandlers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import reusableElements.tableFilesHandlers.tableExceptions.NoSuchColumnException;

import java.util.List;

public class TableCell {
    private By cell;
    private String cellName;
    private WebDriver driver;

    public TableCell(WebDriver driver) {
        this.driver = driver;
    }

    public TableCell(WebDriver driver, String cellName) {
        this.driver = driver;
        this.cellName = cellName;
        cellName = cellName.toUpperCase().replace(" ", "_");
        this.cell = By.cssSelector("input[name^='"+cellName+"']");
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
        cellName = cellName.toUpperCase().replace(" ", "_");
        this.cell = By.cssSelector("input[name^='"+cellName+"']");
    }

    public TableCell insertValue(String val) throws NoSuchColumnException {
        scrollForCell(cell);
        driver.findElement(cell).sendKeys(val);
        return this;
    }

    private boolean isLoaded(By cell){
        return driver.findElements(cell).size()!=0;
    }

    public void scrollForCell(By cell) throws NoSuchColumnException {
        Actions actions = new Actions(driver);
        WebElement scrollThumb = driver.findElements(By.className("hScrollThumb")).get(1);
        int i = 0;
        while (!isLoaded(cell)){
            actions.clickAndHold(scrollThumb).moveByOffset(50, 0).release().build().perform();
            i++;
            if (i>15 && !isLoaded(cell)){
                throw new NoSuchColumnException(cellName);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView()", driver.findElement(cell));
    }

    public void submit(){
        driver.findElement(cell).sendKeys(Keys.ENTER);
    }

}

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
        scrollForCell(true);
        driver.findElement(cell).sendKeys(val);
        return this;
    }

    private boolean isLoaded(By cell){
        return driver.findElements(cell).size()!=0;
    }

    public void scrollForCell(boolean toTheRight) throws NoSuchColumnException {
        Actions actions = new Actions(driver);
        List<WebElement> scrollThumbs = driver.findElements(By.className("hScrollThumb"));
        WebElement scrollThumb = scrollThumbs.get(scrollThumbs.size()-1);
        int i = 0;
        int offset= -50;
        if(toTheRight){
            offset = 50;
        }
        while (!isLoaded(cell)){
            actions.clickAndHold(scrollThumb).moveByOffset(offset, 0).release().build().perform();
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

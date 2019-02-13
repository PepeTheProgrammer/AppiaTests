package reusableElements.tableFilesHandlers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import reusableElements.tableFilesHandlers.tableExceptions.NoSuchColumnException;

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
        this.cell = By.xpath("input/@*[starts-with(name(), '"+cellName+"')]");
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
        cellName = cellName.toUpperCase().replace(" ", "_");
        this.cell = By.xpath("input/@*[starts-with(name(), '"+cellName+"')]");
    }

    public void insertValue(String val) throws NoSuchColumnException {
        scrollForCell(cell);
        driver.findElement(cell).sendKeys(val);
    }

    private boolean isLoaded(By cell){
        return driver.findElements(cell).size()!=0;
    }

    public void scrollForCell(By cell) throws NoSuchColumnException {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String style = driver.findElement(By.className("hScrollThumb")).getAttribute("style");
        String scrollFormerPosition = style.substring(style.indexOf("left"), style.indexOf("px"));
        int scrollPosition =Integer.parseInt(scrollFormerPosition.substring(scrollFormerPosition.indexOf(" ")).trim());
        while (!isLoaded(cell)){
            System.out.println(style);
            js.executeScript("document.getElementsByClassName('hScrollThumb')[0].setAttribute('style', '"+style+"')", "");
            if(scrollPosition>810 && !isLoaded(cell)){
                throw new NoSuchColumnException(cellName);
            }
            scrollPosition = scrollPosition + 60;
            style = style.replace(scrollFormerPosition, "left: "+scrollPosition);
        }
        js.executeScript("arguments[0].scrollIntoView()", driver.findElement(cell));
    }

}

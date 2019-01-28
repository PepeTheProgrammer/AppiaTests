package reusableElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class NestedElements {


    public static WebElement getNestedElementByText(WebDriver driver, By motherElementSelector, String text){
        List<WebElement> children = driver.findElements(motherElementSelector);
        for (WebElement child: children) {
            if(child.getText().contains(text)) {
                return child;
            }
        }
        return null;
    }
    public static List<WebElement> getRecordEditorCells(WebDriver driver){
        return driver.findElements(By.className("recordEditorCell"));
    }
}

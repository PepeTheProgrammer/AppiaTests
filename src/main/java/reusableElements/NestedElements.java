package reusableElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class NestedElements {


    public static WebElement getNestedElementByText(WebDriver driver, By motherElementSelector, String text){
        List<WebElement> children = driver.findElements(motherElementSelector);
        for (WebElement element: children) {
            if(element.getText().contains(text)) {
                return element;
            }
        }
        return null;
    }
}

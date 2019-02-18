package reusableElements.tableFilesHandlers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class TableTags {

    public static void createTag(WebDriver driver, String tagName, String color) throws InterruptedException {

        new DataSearchButton(driver, "Add Tag").click();
        Thread.sleep(2000);
        driver.findElement(By.name("LABEL")).sendKeys(tagName, Keys.TAB);
        driver.findElement(By.name("textArea")).sendKeys(Keys.TAB);
        driver.findElement(By.name("COLOR")).sendKeys(color);
        Thread.sleep(1000);
        driver.findElement(By.name("COLOR")).sendKeys(Keys.ENTER);
        new DataSearchButton(driver, "Add Tag").click();
        Thread.sleep(3000);

    }

    public static void cleanNumberOfTagsFromTop(WebDriver driver, int numberOfTags) throws InterruptedException {
        for (int i = 0; i <numberOfTags ; i++) {
            new DataSearchButton(driver, "Delete Tag(s)").click();
            Thread.sleep(3000);
            new DataSearchButton(driver, "Save Tag(s)").click();
            Thread.sleep(2000);
        }
    }
    public static void cleanupTags(WebDriver driver, String... tagNames) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.LEFT_CONTROL);
        for (String tagName:tagNames) {
            if(driver.findElements(By.xpath("//div[text()='"+tagName+"']")).size()>0) {
                actions.click(driver.findElement(By.xpath("//div[text()='" + tagName + "']")));
            }
        }
        actions.build().perform();
        Thread.sleep(2000);
        new DataSearchButton(driver, "Delete Tag(s)").click();
        Thread.sleep(2000);
        new DataSearchButton(driver, "Save Tag(s)").click();
    }

    public static void tagMarkedRecords(WebDriver driver, String tagName){
        List<WebElement> availableTags = driver.findElements(By.xpath("//tr[contains(@id, 'isc_PickListMenu_']"));
        for (WebElement tag:availableTags) {
            if(tag.getText().contains(tagName)){
                tag.findElement(By.className("checkboxFalse")).click();
                return;
            }
        }
    }
}

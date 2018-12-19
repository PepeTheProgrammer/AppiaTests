package importEditSearchTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class RobotUpload
{
	public static void uploadFile(String filePath) throws AWTException, InterruptedException
	{
		Robot robot = new Robot();

		StringSelection selection = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

		Thread.sleep(500);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}

}

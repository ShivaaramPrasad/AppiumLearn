package week4.day1;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class LearnTouchActions_swipe_002 {
	
	public static void main(String[] args) throws MalformedURLException {
		// Step01:- Set Desired Capabilities
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability("appPackage", "com.the511plus.MultiTouchTester");
				dc.setCapability("appActivity", "com.the511plus.MultiTouchTester.MultiTouchTester");
				dc.setCapability("deviceName", "OnePlus 7T Pro");
				dc.setCapability("platformName", "Android");
				dc.setCapability("automationName", "UiAutomator2");
				dc.setCapability("noReset", true);
				
				// Step02:- Create the driver and hit the appium server
				AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
				
				
	// swipe
				// Get the Screen size
				Dimension size = driver.manage().window().getSize();
				System.out.println(size);
				int width = size.getWidth();
				int height = size.getHeight();
				
				// find the position where you need touch - starts and ends - x and y
				int startX = (int)(width * 0.2);
				int startY = (int) (height * 0.5);
				
				int endX = (int)(width * 0.8);
				int endY = (int) (height * 0.5);
				
				System.out.println(width +"========"+ height);
				System.out.println(startX +"========"+ startY);
				System.out.println(endX +"========"+ endY);
				
				// Actions builder = new Actions(driver);
				
				TouchAction<?> action1 = new TouchAction<>(driver)
						.press(PointOption.point(startX, startY))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
						.moveTo(PointOption.point(endX, endY))
						.release();
				//		.perform();
				
				TouchAction<?> action2 = new TouchAction<>(driver)
						.press(PointOption.point(startX, startY + 400))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(5)))
						.moveTo(PointOption.point(endX, endY + 400))
						.release();
				//		.perform();
				
				// open bug
				/*
				 * new MultiTouchAction(driver) 
				 * .add(action1) 
				 * .add(action2) 
				 * .perform();
				 */
						
						
						
				
				
				
	}

}













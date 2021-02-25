package week4.day1;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class LearnTouchActionUsingPointerInput_004 {
	
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
				
				
	// swipe using Pointer input
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
				
				// Action 1
				PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
				Sequence dragNdrop = new Sequence(finger, 1);
				
				
				dragNdrop.addAction(finger.createPointerMove(Duration.ofMillis(0), 
						PointerInput.Origin.viewport(), startX, startY));
				dragNdrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				
				
				
				dragNdrop.addAction(finger.createPointerMove(Duration.ofMillis(1000), 
						PointerInput.Origin.viewport(), endX, endY));
				dragNdrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
				
				// Action 2
				
				PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");
				Sequence dragNdrop2 = new Sequence(finger2, 2);
				
				
				dragNdrop2.addAction(finger2.createPointerMove(Duration.ofMillis(0), 
						PointerInput.Origin.viewport(), startX, startY + 500));
				dragNdrop2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
				
				
				
				dragNdrop2.addAction(finger2.createPointerMove(Duration.ofMillis(1000), 
						PointerInput.Origin.viewport(), endX, endY + 500));
				dragNdrop2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
				
				driver.perform(Arrays.asList(dragNdrop, dragNdrop2));
				
				
						
						
						
						
				
				
				
	}

}













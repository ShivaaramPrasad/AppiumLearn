package week2.day1;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class LearnPoinerInputforZoomOut_7 {

	public static void main(String[] args) throws MalformedURLException {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "com.the511plus.MultiTouchTester");
		dc.setCapability("appActivity", "com.the511plus.MultiTouchTester.MultiTouchTester");
		dc.setCapability("deviceName", "OnePlus 6");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);

		// launch the app
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(
				new URL("http://0.0.0.0:4723/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


		// Fetch the screen size
		Dimension size = driver.manage().window().getSize();
		System.out.println(size);
		int width = size.getWidth();
		int height = size.getHeight();

		//Zoom out
		// action one

		int startX = (int)(width*0.8);
		int startY = (int)(height*0.1);

		int endX = (int)(width*0.5);
		int endY = (int)(height*0.5);

		// action two

		int startX1 = (int)(width*0.1);
		int startY1 = (int)(height*0.8);

		int endX1 = (int)(width*0.5);
		int endY1 = (int)(height*0.5);


		//action 1

		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence dragNDrop = new Sequence(finger, 1);

		dragNDrop.addAction(finger.createPointerMove(
				Duration.ofMillis(0), 
				PointerInput.Origin.viewport(),
				startX, startY));
		dragNDrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

		dragNDrop.addAction(finger.createPointerMove(
				Duration.ofMillis(1000), 
				PointerInput.Origin.viewport(),
				endX, endY));
		dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));


		// action 2

		PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");
		Sequence dragNDrop2 = new Sequence(finger2, 2);

		dragNDrop2.addAction(finger2.createPointerMove(
				Duration.ofMillis(0), 
				PointerInput.Origin.viewport(),
				startX1, startY1));
		dragNDrop2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

		dragNDrop2.addAction(finger2.createPointerMove(
				Duration.ofMillis(1000), 
				PointerInput.Origin.viewport(),
				endX1, endY1));
		dragNDrop2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		// perform

		driver.perform(Arrays.asList(dragNDrop, dragNDrop2));

	}

}

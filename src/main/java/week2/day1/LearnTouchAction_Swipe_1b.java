package week2.day1;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class LearnTouchAction_Swipe_1b {

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
		
		// find position that where u need to touch
		int startX = (int) (width*0.5);  
        int startY = (int) (height*0.8);  
        
        int endX = (int) (height*0.5);  
        int endY = (int) (width*0.2);
		
		// Swipe - Left to right
		TouchAction<?> action1 = new TouchAction<>(driver)
				.press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))        // Duration.seconds ===> 3
				.moveTo(PointOption.point(endX, endY))
				.release()
				.perform();


	}

}

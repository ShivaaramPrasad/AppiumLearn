package week4.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class LearnNotifications2 {
	
	public static AndroidDriver<WebElement> driver;
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "net.oneplus.launcher");
		dc.setCapability("appActivity", "net.oneplus.launcher.Launcher");
		dc.setCapability("deviceName", "OnePlus 7T Pro");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);

		// Step02:- Create the driver and hit the appium server
		driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.openNotifications();
		
		String text = driver.findElementByXPath("//*[@text='USB debugging connected']").getText();
		System.out.println(text);
		
		Thread.sleep(2000);
		
		driver.findElementByXPath("//*[@text='USB debugging connected']").click();
		
		Thread.sleep(2000);
		
		List<WebElement> allEle = driver.findElementsById("android:id/title");
		for (WebElement eachEle : allEle) {
			System.out.println(eachEle.getText());
		}
		Thread.sleep(3000);
		Dimension size = driver.manage().window().getSize();
		int startX = (int)(size.getWidth() * 0.5);
		int startY = (int) (size.getHeight() * 0.2);
		
		int endX = (int)(size.getWidth() * 0.5);
		int endY = (int) (size.getHeight() * 0.8);
		
		
		if(!eleIsDisplayed(driver.findElementByXPath("//android.widget.TextView[@text='Background check']"))) {
		
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence dragNdrop = new Sequence(finger, 1);
		
		
		dragNdrop.addAction(finger.createPointerMove(Duration.ofMillis(0), 
				PointerInput.Origin.viewport(), startX, startY));
		dragNdrop.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		
		
		
		dragNdrop.addAction(finger.createPointerMove(Duration.ofMillis(1000), 
				PointerInput.Origin.viewport(), endX, endY));
		dragNdrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		
	//	driver.perform(Arrays.asList(dragNdrop));
		}
		
		
	}
	
	public void getElement() {
		driver.findElementByXPath("//android.widget.TextView[@text='Background check']");
	}
	
	public static boolean eleIsDisplayed(WebElement ele) {
		try {
			if(ele.isDisplayed())
				return true;
			
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	

}

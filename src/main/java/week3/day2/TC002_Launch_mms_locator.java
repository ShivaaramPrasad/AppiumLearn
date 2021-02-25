package week3.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class TC002_Launch_mms_locator {

	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		
		// Step01:- Set Desired Capabilities
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "com.oneplus.mms");
		dc.setCapability("appActivity", "com.android.mms.ui.ConversationList");
		dc.setCapability("deviceName", "OnePlus 7T Pro");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);
		
		// Step02:- Create the driver and hit the appium server
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		Thread.sleep(2000);
		driver.findElementByAccessibilityId("Search messages").click();
		
		Thread.sleep(2000);
		driver.findElementById("com.oneplus.mms:id/search_src_text").sendKeys("Tl Sarath");
		
		Thread.sleep(2000);
		driver.findElementById("com.oneplus.mms:id/title").click();
		
		Thread.sleep(2000);
		driver.findElementById("com.oneplus.mms:id/compose_message_text").sendKeys("vai code");
		
		
		driver.findElementByAccessibilityId("Send Message").click();
		
		//driver.findElementsByAccessibilityId("");
		
		
		
		
		
		
	}

}

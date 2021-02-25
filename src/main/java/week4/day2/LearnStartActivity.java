package week4.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

public class LearnStartActivity {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "com.whatsapp");
		dc.setCapability("appActivity", "com.whatsapp.HomeActivity");
		dc.setCapability("deviceName", "OnePlus 7T Pro");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);

		// Step02:- Create the driver and hit the appium server
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElementByAccessibilityId("Search").click();

		driver.findElementById("com.whatsapp:id/search_src_text").sendKeys("APPIUM MAY 2020");
		Thread.sleep(2000);

		driver.findElementById("com.whatsapp:id/conversations_row_contact_name").click();


		// Step03:- Switch the control from one Activity to another Activity(diff App)
		Activity act = new Activity("com.oneplus.mms", "com.android.mms.ui.ConversationList");
		driver.startActivity(act);

		//	driver.startActivity(new Activity("com.oneplus.mms", "com.android.mms.ui.ConversationList"));

		Thread.sleep(2000);
		driver.findElementByAccessibilityId("Search messages").click();

		Thread.sleep(2000);
		driver.findElementById("com.oneplus.mms:id/search_src_text").sendKeys("Tl Sarath");

		Thread.sleep(2000);
		driver.findElementById("com.oneplus.mms:id/title").click();

		Thread.sleep(2000);
		driver.findElementById("com.oneplus.mms:id/compose_message_text").sendKeys("vai code");


		driver.findElementByAccessibilityId("Send Message").click();

//		act = new Activity("com.oneplus.mms", "com.android.mms.ui.ConversationList").setStopApp(false);

		// Step03:- Switch the control from one Activity to another Activity(diff App)
		Activity act1 = new Activity("com.whatsapp", "com.whatsapp.HomeActivity");
		driver.startActivity(act1);

	}

}

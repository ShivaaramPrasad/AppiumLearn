package week1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class MMS_APP {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {


		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "com.oneplus.mms");
		dc.setCapability("appActivity", "com.android.mms.ui.ConversationList");
		dc.setCapability("deviceName", "OnePlus 6");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElementByAccessibilityId("Search messages").click();
		Thread.sleep(2000);

		driver.findElementById("com.oneplus.mms:id/search_src_text").sendKeys("IT Raize Ahamed");

		Thread.sleep(2000);

		driver.findElementById("com.oneplus.mms:id/title").click();

		Thread.sleep(2000);
		driver.findElementByAccessibilityId("Send Message").click();

		driver.findElementById("com.oneplus.mms:id/compose_message_text").sendKeys("sending automation msg via Appium");

		driver.findElementByAccessibilityId("Send Message").click();
		driver.findElementByAccessibilityId("You sent a message").click();
		driver.findElementByAccessibilityId("Send Message").click();

		driver.findElementById("com.oneplus.mms:id/self_send_icon").click();

		driver.findElementByAccessibilityId("Send Message").click();

	}

}

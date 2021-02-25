package week4.day1;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class LearnTouchActions_005 {

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
		
		
		WebElement eleMsg = driver.findElementByXPath("(//android.widget.FrameLayout[@content-desc=\"You sent a message\"])[4]"
				+ "/android.widget.LinearLayout[1]/android.widget.LinearLayout");
		
		
		TouchAction<?> action = new TouchAction<>(driver)
				.longPress(LongPressOptions.longPressOptions()
						.withElement(ElementOption.element(eleMsg))
						.withDuration(Duration.ofSeconds(2)))
				.release()
				.perform();







	}

}













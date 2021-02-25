package week2.day1;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class LearnTouchActionforLongPress {


	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "com.oneplus.mms");
		dc.setCapability("appActivity", "com.android.mms.ui.ConversationList");
		dc.setCapability("deviceName", "OnePlus 6");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);


		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(
				new URL("http://0.0.0.0:4723/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElementByAccessibilityId("Search messages").click();
		Thread.sleep(2000);

		driver.findElementById("com.oneplus.mms:id/search_src_text").sendKeys("Amma");

		Thread.sleep(2000);

		driver.findElementById("com.oneplus.mms:id/title").click();

		Thread.sleep(2000);

		WebElement eleMsg = driver.findElementByLinkText("Automation message");

		TouchAction<?> action = new TouchAction<>(driver)
				.longPress(LongPressOptions.longPressOptions()
						.withElement(ElementOption.element(eleMsg))
						.withDuration(Duration.ofSeconds(2)))
				.release()
				.perform();

	}

}

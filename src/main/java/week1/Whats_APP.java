package week1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class Whats_APP {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "com.whatsapp");
		dc.setCapability("appActivity", "com.whatsapp.HomeActivity");
		dc.setCapability("deviceName", "OnePlus 6");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElementByAccessibilityId("Search").click();
		driver.findElementById("com.whatsapp:id/search_src_text").sendKeys("IT Raize Ahamed");
		driver.findElementById("com.whatsapp:id/conversations_row_contact_name").click();
		driver.findElementById("com.whatsapp:id/entry").sendKeys("Automated messsge from Appium");
		driver.findElementByAccessibilityId("Send").click();

  
	}

}

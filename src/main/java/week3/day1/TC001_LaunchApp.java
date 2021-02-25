package week3.day1;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class TC001_LaunchApp {
	
	// if the app is already installed in mobile -> 
	
    //3 capabilities are mandatory
				// appPackage
				// appActivity
				// deviceName
    // Optional
				// platform
	
	public static void main(String[] args) throws MalformedURLException {
		
		// Step01:- Set Desired Capabilities
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "com.android.dialer");
		dc.setCapability("appActivity", "com.oneplus.contacts.activities.OPDialtactsActivity");
		dc.setCapability("deviceName", "OnePlus 7T Pro");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);
		
		// Step02:- Create the driver and hit the appium server
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
		
		
		
		
	}

}

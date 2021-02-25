package week3.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class TC003_Install_App {
	
	
// if the app is not install in mobile -> 
	
    //2 capabilities are mandatory
				// deviceName
				// app
    // Optional
				// platform

	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		
		// Step01:- Set Desired Capabilities
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("deviceName", "OnePlus 7T Pro");
		dc.setCapability("platformName", "Android");
		dc.setCapability("app", "C:\\Users\\dell\\Desktop\\appium\\apks\\leaforg.apk");
		
		// Step02:- Create the driver and hit the appium server
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.findElementById("").sendKeys("rajkumar@testleaf.com");
		driver.findElementById("").sendKeys("Leaf@123");
		
		
		
	}

}

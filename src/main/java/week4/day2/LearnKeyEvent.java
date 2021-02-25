package week4.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class LearnKeyEvent {
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		// Step01:- Set Desired Capabilities
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability("appPackage", "com.testleaf.leaforg");
				dc.setCapability("appActivity", "com.testleaf.leaforg.MainActivity");
				dc.setCapability("deviceName", "OnePlus 7T Pro");
				dc.setCapability("platformName", "Android");
				dc.setCapability("automationName", "UiAutomator2");
				dc.setCapability("noReset", true);

				// Step02:- Create the driver and hit the appium server
				AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
				driver.rotate(ScreenOrientation.LANDSCAPE);
				Thread.sleep(2000);
				driver.rotate(ScreenOrientation.PORTRAIT);
				
				
				
				driver.findElementByXPath("(//android.widget.EditText)[1]").click();
				Thread.sleep(2000);
				
				KeyEvent key = new KeyEvent(AndroidKey.A);
				driver.pressKey(key);
				
				Thread.sleep(2000);
				driver.hideKeyboard();
				
	}

}

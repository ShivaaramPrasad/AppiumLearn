package week2.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class LearnKeyboardEvent {

		public static void main(String[] args) throws MalformedURLException, InterruptedException {
			DesiredCapabilities dc = new DesiredCapabilities();

			dc.setCapability("appPackage", "com.testleaf.leaforg");
			dc.setCapability("appActivity", "com.testleaf.leaforg.MainActivity");
			dc.setCapability("deviceName", "OnePlus 6");
			dc.setCapability("platformName", "Android");
			dc.setCapability("automationName", "UiAutomator2");

			dc.setCapability("noReset", true);

			// launch the app

			AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(

					new URL("http://0.0.0.0:4723/wd/hub"), dc);

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElementByXPath("//android.widget.EditText[@text='']").click();
			Thread.sleep(3000);
			KeyEvent key = new KeyEvent(AndroidKey.G);
			driver.pressKey(key);
		//	driver.pressKey(new KeyEvent(AndroidKey.G));

			

	        


		
		
		
		}

}

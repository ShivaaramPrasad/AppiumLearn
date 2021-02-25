package week2.day2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidBatteryInfo;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;

public class LearnwifiOn {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("appPackage", "net.oneplus.launcher");
		dc.setCapability("appActivity", "net.oneplus.launcher.Launcher");
		dc.setCapability("deviceName", "OnePlus 6");
		dc.setCapability("platformName", "Android");
		dc.setCapability("automationName", "UiAutomator2");
		dc.setCapability("noReset", true);

		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		ConnectionStateBuilder csb = new ConnectionStateBuilder();
		ConnectionState state = csb.withAirplaneModeDisabled().build();
		driver.setConnection(state);
		driver.openNotifications();


		ConnectionStateBuilder csb1 = new ConnectionStateBuilder();
		ConnectionState state1 = csb1.withAirplaneModeDisabled().build();
		driver.setConnection(state1);

		driver.setConnection(new ConnectionStateBuilder().withAirplaneModeEnabled().build());		
		driver.openNotifications();


	}

}

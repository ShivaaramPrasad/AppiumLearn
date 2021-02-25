package mobile.api;

import static io.appium.java_client.touch.offset.PointOption.point;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.connection.HasNetworkConnection;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.touch.WaitOptions;
import utils.HTMLReporter;

public class MobileWrapper extends HTMLReporter {

	public static AppiumDriver<WebElement> driver;
	public static final ThreadLocal<AppiumDriver<WebElement>> tlDriver = new ThreadLocal<AppiumDriver<WebElement>>();
	WebDriverWait wait;

	public boolean launchApp(String appPackage, String appActivity, String deviceName, String udid) {
		try {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability("appPackage", appPackage);
			dc.setCapability("appActivity", appActivity);
			dc.setCapability("deviceName", deviceName);
			dc.setCapability("automationName", "UiAutomator2");
			dc.setCapability("noReset", true);
			dc.setCapability("udid", udid);
			driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
			tlDriver.set(driver);
			wait = new WebDriverWait(getDriver(), 30);
			getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {

		}
		return true;
	}

	public boolean launchBrowser(String browserName, String deviceName, String URL) {
		try {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setCapability("browserName", browserName);
			dc.setCapability("deviceName", deviceName);
			driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), dc);
			tlDriver.set(driver);
			getDriver().get(URL);
			wait = new WebDriverWait(getDriver(), 30);
			getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean loadURL(String URL) {
		getDriver().get(URL);
		return true;
	}

	public boolean verifyAndInstallApp(String appPackage, String appPath) {
		boolean bInstallSuccess = false;

		if (getDriver().isAppInstalled(appPackage))
			getDriver().removeApp(appPackage);

		getDriver().installApp(appPath);
		bInstallSuccess = true;

		return bInstallSuccess;
	}
	
	

	// Only for Android
	public boolean switchBetweenAppsInAndroid(String appPackage, String appActivity) {
		try {
			Activity activity = new Activity(appPackage, appActivity);
			((StartsActivity) getDriver()).startActivity(activity);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void printContext() {
		try {
			Set<String> contexts = getDriver().getContextHandles();
			for (String string : contexts) {
				System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean switchContext(String Context) {
		try {
			getDriver().context(Context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean switchNativeview() {
		try {
			Set<String> contextNames = getDriver().getContextHandles();
			for (String contextName : contextNames) {
				if (contextName.contains("NATIVE"))
					getDriver().context(contextName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean verifyText(String Expected, String locator, String locValue, String eleName) {
		boolean val = false;
		WebElement ele = locateElement(locator, locValue);
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(ExpectedConditions.visibilityOf(ele));
			String name = ele.getText();
			if (name.contains(Expected)) {
				reportStep(
						"The element " + eleName + " text :" + name + " is matching with expected text : " + Expected,
						"PASS");
				val = true;
			} else {
				reportStep("The element " + eleName + " text :" + name + " is not matching with expected text : "
						+ Expected, "FAIL");
				val = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public void verifyPartialAttributeForListOfElements(List<MobileElement> eles, String attribute, String value, String eleName) {
		try {
			for (MobileElement ele : eles) {
				if(getAttribute(ele, attribute, eleName).contains(value)) {
					reportStep("The expected attribute :"+attribute+"for "+getText(ele, eleName)+" value contains the actual "+value,"PASS");
				}else {
					reportStep("The expected attribute :"+attribute+"for "+getText(ele, eleName)+" value does not contains the actual "+value,"FAIL");
				}
			}
			
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while verifying the Attribute Text", "FAIL");
		}
	}
	
	public String getText(MobileElement ele, String eleName) {	
		String bReturn = "";
		try {
			bReturn = ele.getText();
		} catch (WebDriverException e) {
			reportStep("The element: "+eleName+" could not be found.", "FAIL");
		}
		return bReturn;
	}
	
	public String getAttribute(MobileElement ele, String attribute, String eleName) {		
		String bReturn = "";
		try {
			bReturn=  ele.getAttribute(attribute);
		} catch (WebDriverException e) {
			reportStep("The element: "+eleName+" could not be found.", "FAIL");
		} 
		return bReturn;
	}
	public void showNotificationMenu() {
		((AndroidDriver<WebElement>) getDriver()).openNotifications();
	}

	public void getBatteryInfoInAndroid() {
		Map<String, Object> args = new HashMap<>();
		args.put("command", "dumpsys");
		args.put("args", "battery");
		Object executeScript = getDriver().executeScript("mobile:shell", args);
		System.out.println(executeScript.toString());
	}

	public void sleep(int mSec) {
		try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean pressEnter() {
		((PressesKey) getDriver()).pressKey(new KeyEvent(AndroidKey.ENTER));
		return true;
	}

	public boolean pressBack() {
		((PressesKey) getDriver()).pressKey(new KeyEvent(AndroidKey.BACK));
		return true;
	}

	protected WebElement locateElement(String locator, String locValue) {
		switch (locator) {
		case "id":
			return getDriver().findElementById(locValue);
		case "name":
			return getDriver().findElementByName(locValue);
		case "className":
			return getDriver().findElementByClassName(locValue);
		case "link":
			return getDriver().findElementByLinkText(locValue);
		case "partialLink":
			return getDriver().findElementByPartialLinkText(locValue);
		case "tag":
			return getDriver().findElementByTagName(locValue);
		case "css":
			return getDriver().findElementByCssSelector(locValue);
		case "xpath":
			return getDriver().findElementByXPath(locValue);
		case "accessibilityId":
			return getDriver().findElementByAccessibilityId(locValue);
		case "image":
			return getDriver().findElementByImage(locValue);
		}
		return null;
	}
	
	public List<WebElement> locateElements(String locator, String locValue) {
		try {
			switch (locator) {
				case "id": return getDriver().findElements(By.id(locValue));
				case "name": return getDriver().findElements(By.name(locValue));
				case "class": return getDriver().findElements(By.className(locValue));
				case "link" : return getDriver().findElements(By.linkText(locValue));
				case "xpath": return getDriver().findElements(By.xpath(locValue));		
				default: break;
			}

		} catch (NoSuchElementException e) {
			reportStep("The elements with locator "+locator+" not found.","FAIL");
		} catch (WebDriverException e) {
			reportStep("Unknown exception occured while finding "+locator+" with the value "+locValue, "FAIL");
		}
		return null;
	}
	

	public boolean verifyTextString(String Expected, String locator, String locValue) {

		boolean val = false;
		try {
			wait.until(ExpectedConditions.visibilityOf(locateElement(locator, locValue)));
			String name = locateElement(locator, locValue).getText();
			if (name.contains(Expected)) {
				val = true;
			} else
				val = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	// Applicable only for Mobile Web/Browser
	public boolean scrollDownInBrowser(int val) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript("window.scrollBy(0," + val + "\")", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void scrollDownInBrowser() {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		js.executeScript("mobile: scroll", scrollObject);
	}

	// Applicable only for Mobile Web/Browser
	public boolean navigateBackInBrowser() {
		try {
			getDriver().navigate().back();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean scrollFromDownToUpinAppUsingTouchActions() {
		try {
			Dimension size = getDriver().manage().window().getSize();
			int x1 = (int) (size.getWidth() * 0.5);
			int y1 = (int) (size.getHeight() * 0.8);
			int x0 = (int) (size.getWidth() * 0.5);
			int y0 = (int) (size.getHeight() * 0.2);
			MultiTouchAction touch = new MultiTouchAction(getDriver());
			touch.add(new TouchAction<>(getDriver()).press(point(x1, y1))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(point(x0, y0)).release())
					.perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void scrollFromDownToUpinAppUsingPointerInput() {
		Dimension size = getDriver().manage().window().getSize();
		int x1 = (int) (size.getWidth() * 0.5);
		int y1 = (int) (size.getHeight() * 0.8);
		int x0 = (int) (size.getWidth() * 0.5);
		int y0 = (int) (size.getHeight() * 0.2);
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence sequence = new Sequence(finger, 1);
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x1, y1));
		sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x0, y0));
		sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
		getDriver().perform(Arrays.asList(sequence));
	}

	public boolean scrollFromUpToDowninAppUsingTouchActions() {
		try {
			Dimension size = getDriver().manage().window().getSize();
			int x1 = (int) (size.getWidth() * 0.5);
			int y1 = (int) (size.getHeight() * 0.2);
			int x0 = (int) (size.getWidth() * 0.5);
			int y0 = (int) (size.getHeight() * 0.8);
			MultiTouchAction touch = new MultiTouchAction(getDriver());
			touch.add(new TouchAction<>(getDriver()).press(point(x1, y1))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(point(x0, y0)).release())
					.perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void scrollFromUpToDowninAppUsingPointerInput() {
		Dimension size = getDriver().manage().window().getSize();
		int x1 = (int) (size.getWidth() * 0.5);
		int y1 = (int) (size.getHeight() * 0.8);
		int x0 = (int) (size.getWidth() * 0.5);
		int y0 = (int) (size.getHeight() * 0.2);
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence sequence = new Sequence(finger, 1);
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x0, y0));
		sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x1, y1));
		sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
		getDriver().perform(Arrays.asList(sequence));
	}

	public boolean scrollFromRightToLeftInAppUsingTouchActions() {
		try {
			Dimension size = getDriver().manage().window().getSize();
			int x1 = (int) (size.getWidth() * 0.8);
			int y1 = (int) (size.getHeight() * 0.5);
			int x0 = (int) (size.getWidth() * 0.2);
			int y0 = (int) (size.getHeight() * 0.5);
			MultiTouchAction touch = new MultiTouchAction(getDriver());
			touch.add(new TouchAction<>(getDriver()).press(point(x1, y1))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(point(x0, y0)).release())
					.perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void scrollFromRightToLeftInAppUsingPointerInput() {
		Dimension size = getDriver().manage().window().getSize();
		int x1 = (int) (size.getWidth() * 0.8);
		int y1 = (int) (size.getHeight() * 0.5);
		int x0 = (int) (size.getWidth() * 0.2);
		int y0 = (int) (size.getHeight() * 0.5);
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence sequence = new Sequence(finger, 1);
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x1, y1));
		sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x0, y0));
		sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
		getDriver().perform(Arrays.asList(sequence));
	}

	public boolean scrollFromLeftToRightInAppUsingTouchActions() {
		try {
			Dimension size = getDriver().manage().window().getSize();
			int x1 = (int) (size.getWidth() * 0.8);
			int y1 = (int) (size.getHeight() * 0.5);
			int x0 = (int) (size.getWidth() * 0.2);
			int y0 = (int) (size.getHeight() * 0.5);
			MultiTouchAction touch = new MultiTouchAction(getDriver());
			touch.add(new TouchAction<>(getDriver()).press(point(x0, y0))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(point(x1, y1)).release())
					.perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void scrollFromLeftToRightInAppUsingPointerInput() {
		Dimension size = getDriver().manage().window().getSize();
		int x1 = (int) (size.getWidth() * 0.8);
		int y1 = (int) (size.getHeight() * 0.5);
		int x0 = (int) (size.getWidth() * 0.2);
		int y0 = (int) (size.getHeight() * 0.5);
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence sequence = new Sequence(finger, 1);
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x0, y0));
		sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
		sequence.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x1, y1));
		sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
		getDriver().perform(Arrays.asList(sequence));
	}

	public void pinchUsingPointerInput() {

		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

		Dimension size = getDriver().manage().window().getSize();
		Point source = new Point(size.getWidth(), size.getHeight());

		Sequence pinchAndZoom1 = new Sequence(finger, 0);
		pinchAndZoom1.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
				source.x / 3, source.y / 3));
		pinchAndZoom1.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		pinchAndZoom1.addAction(new Pause(finger, Duration.ofMillis(100)));
		pinchAndZoom1.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
				source.x / 2, source.y / 2));
		pinchAndZoom1.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		Sequence pinchAndZoom2 = new Sequence(finger2, 1);
		pinchAndZoom2.addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
				source.x * 3 / 4, source.y * 3 / 4));
		pinchAndZoom2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		pinchAndZoom2.addAction(new Pause(finger, Duration.ofMillis(100)));
		pinchAndZoom2.addAction(finger2.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
				source.x / 2, source.y / 2));
		pinchAndZoom2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		getDriver().perform(Arrays.asList(pinchAndZoom1, pinchAndZoom2));
	}

	// Pinch using Touch Action will not work. Known Bug.
	public void pinchUsingTouchActions() {
		Dimension size = getDriver().manage().window().getSize();
		Point source = new Point(size.getWidth(), size.getHeight());
		MultiTouchAction multiTouch = new MultiTouchAction(getDriver());
		TouchAction<?> tAction0 = new TouchAction<>(getDriver());
		TouchAction<?> tAction1 = new TouchAction<>(getDriver());
		tAction0.press(point(source.x / 3, source.y / 3)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(point(source.x / 2, source.y / 2)).release();
		tAction1.press(point(source.x * 3 / 4, source.y * 3 / 4))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).moveTo(point(source.x / 2, source.y / 2))
				.release();

		multiTouch.add(tAction0).add(tAction1);
		multiTouch.perform();
	}

	// using pointer input
	public void ZoomUsingPointerInput() {

		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

		Dimension size = getDriver().manage().window().getSize();
		Point source = new Point(size.getWidth(), size.getHeight());

		Sequence pinchAndZoom1 = new Sequence(finger, 0);
		pinchAndZoom1.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
				source.x / 2, source.y / 2));
		pinchAndZoom1.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		pinchAndZoom1.addAction(new Pause(finger, Duration.ofMillis(100)));
		pinchAndZoom1.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
				source.x / 3, source.y / 3));
		pinchAndZoom1.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		Sequence pinchAndZoom2 = new Sequence(finger2, 0);
		pinchAndZoom2.addAction(finger2.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(),
				source.x / 2, source.y / 2));
		pinchAndZoom2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		pinchAndZoom2.addAction(new Pause(finger, Duration.ofMillis(100)));
		pinchAndZoom2.addAction(finger2.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
				source.x * 3 / 4, source.y * 3 / 4));
		pinchAndZoom2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		getDriver().perform(Arrays.asList(pinchAndZoom1, pinchAndZoom2));
	}

	public void zoomUsingTouchActions() {
		Dimension size = getDriver().manage().window().getSize();
		Point source = new Point(size.getWidth(), size.getHeight());
		MultiTouchAction multiTouch = new MultiTouchAction(getDriver());
		TouchAction<?> tAction0 = new TouchAction<>(getDriver());
		TouchAction<?> tAction1 = new TouchAction<>(getDriver());
		tAction0.press(point(source.x / 2, source.y / 2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(point(source.x / 3, source.y / 3)).release();
		tAction1.press(point(source.x * 2, source.y * 2)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(point(source.x * 3 / 4, source.y * 3 / 4)).release();

		multiTouch.add(tAction0).add(tAction1);
		multiTouch.perform();
	}

	private boolean eleIsDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed())
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;

	}

	public boolean scrollFromUpToDowninAppWithWebElementUsingPointerInput(String locator, String locValue) {
		try {
			WebElement ele = locateElement(locator, locValue);
			// int startX = ele.getLocation().getX();
			int startY = ele.getLocation().getY();

			int centerX = ((MobileElement) ele).getCenter().getX();
			int centerY = ((MobileElement) ele).getCenter().getY();
			// int endX = (centerX*2)-startX;
			int endY = (centerY * 2) - startY;

			int x0 = (int) centerX;
			int y0 = (int) ((endY - startY) * 0.2) + startY;
			int x1 = (int) centerX;
			int y1 = (int) ((endY - startY) * 0.8) + startY;

			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
			Sequence sequence = new Sequence(finger, 1);
			sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x0, y0));
			sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
			sequence.addAction(
					finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x1, y1));
			sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
			getDriver().perform(Arrays.asList(sequence));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean scrollFromDownToUpinAppWithWebElementUsingPointerInput(String locator, String locValue) {
		try {
			WebElement ele = locateElement(locator, locValue);
			// int startX = ele.getLocation().getX();
			int startY = ele.getLocation().getY();

			int centerX = ((MobileElement) ele).getCenter().getX();
			int centerY = ((MobileElement) ele).getCenter().getY();
			// int endX = (centerX*2)-startX;
			int endY = (centerY * 2) - startY;

			int x0 = (int) centerX;
			int y0 = (int) ((endY - startY) * 0.8) + startY;
			int x1 = (int) centerX;
			int y1 = (int) ((endY - startY) * 0.2) + startY;

			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
			Sequence sequence = new Sequence(finger, 1);
			sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x0, y0));
			sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
			sequence.addAction(
					finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x1, y1));
			sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
			getDriver().perform(Arrays.asList(sequence));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	public boolean scrollFromLeftToRightinAppWithWebElementUsingPointerInput(String locator, String locValue) {
		try {
			WebElement ele = locateElement(locator, locValue);
			int startX = ele.getLocation().getX();
			// int startY = ele.getLocation().getY();

			int centerX = ((MobileElement) ele).getCenter().getX();
			int centerY = ((MobileElement) ele).getCenter().getY();
			int endX = (centerX * 2) - startX;
			// int endY = (centerY*2)-startY;

			int x0 = (int) ((endX - startX) * 0.2) + startX;
			int y0 = (int) centerY;
			int x1 = (int) ((endX - startX) * 0.8) + startX;
			int y1 = (int) centerY;

			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
			Sequence sequence = new Sequence(finger, 1);
			sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x0, y0));
			sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
			sequence.addAction(
					finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x1, y1));
			sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
			getDriver().perform(Arrays.asList(sequence));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean scrollFromRightToLeftinAppWithWebElementUsingPointerInput(String locator, String locValue) {
		try {
			WebElement ele = locateElement(locator, locValue);
			int startX = ele.getLocation().getX();
			// int startY = ele.getLocation().getY();

			int centerX = ((MobileElement) ele).getCenter().getX();
			int centerY = ((MobileElement) ele).getCenter().getY();
			int endX = (centerX * 2) - startX;
			// int endY = (centerY*2)-startY;

			int x0 = (int) ((endX - startX) * 0.8) + startX;
			int y0 = (int) centerY;
			int x1 = (int) ((endX - startX) * 0.2) + startX;
			int y1 = (int) centerY;

			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
			Sequence sequence = new Sequence(finger, 1);
			sequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x0, y0));
			sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
			sequence.addAction(
					finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), x1, y1));
			sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
			getDriver().perform(Arrays.asList(sequence));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean scrollFromDownToUpinAppUsingPointerInputUntilElementIsVisible(String locator, String locValue) {
		while (!eleIsDisplayed(locateElement(locator, locValue))) {
			scrollFromDownToUpinAppUsingPointerInput();
		}
		return true;
	}

	public boolean scrollFromUpToDowninAppUsingPointerInputUntilElementIsVisible(String locator, String locValue) {
		while (!eleIsDisplayed(locateElement(locator, locValue))) {
			scrollFromUpToDowninAppUsingPointerInput();
		}
		return true;
	}

	public boolean clickInCoOrdinatesOfApp(int x0, int y0) {
		try {
			TouchAction<?> touch = new TouchAction<>(getDriver());
			touch.press(point(x0, y0)).release().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void resetApp() {
		getDriver().resetApp();
	}

	public void closeApp() {
		if (getDriver() != null) {
			try {
				getDriver().closeApp();
			} catch (Exception e) {
			}
		}

	}

	// Only for Android
	public boolean WiFiOffInAndorid() {
		((HasNetworkConnection) getDriver())
				.setConnection(new ConnectionStateBuilder().withAirplaneModeEnabled().build());
		return true;
	}

	// Only for Android
	public boolean WiFiOnInAndroid() {
		((HasNetworkConnection) getDriver()).setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());
		return true;
	}

	public boolean setPortraitOrientation() {
		getDriver().rotate(ScreenOrientation.PORTRAIT);
		return true;
	}

	public boolean setLanscapeOrientation() {
		getDriver().rotate(ScreenOrientation.LANDSCAPE);
		return true;
	}

	public void hideKeyboard() {
		try {
			getDriver().hideKeyboard();
		} catch (Exception e) {
		}
	}

	public String getOrientation() {
		return getDriver().getOrientation().toString();
	}

	public boolean enterValue(String data, String locator, String locValue, String name) {
		try {
			WebElement ele = locateElement(locator, locValue);
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.clear();
			ele.sendKeys(data);
			reportStep("The element :" + name + " is entered with value : " + data + " successfully", "PASS");

			return true;
		} catch (Exception e) {
			reportStep("The element :" + name + " could not be entered with value : " + data, "FAIL");
			return false;
		}
	}

	public boolean click(String locator, String locValue, String name) {
		try {
			WebElement ele = locateElement(locator, locValue);
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
			reportStep("The element : " + name + " clicked Successfully", "PASS");
		} catch (Exception e) {
			reportStep("The element : " + name + " could not be clicked", "FAIL");
			return false;
		}
		return true;
	}

	public boolean clickAndEnter(String locator, String locValue) {
		try {
			WebElement ele = locateElement(locator, locValue);
			WebDriverWait wait = new WebDriverWait(getDriver(), 30);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.sendKeys(Keys.ENTER);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getText(String locator, String locValue) {
		WebElement ele = locateElement(locator, locValue);
		WebDriverWait wait = new WebDriverWait(getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOf(ele));
		return ele.getText();
	}

	@Override
	public long takeSnap() {
		long number = 10000000L;
		try {
			number = (long) Math.floor((Math.random() * 90000000L) + 10000000L);
			File src = getDriver().getScreenshotAs(OutputType.FILE);
			File des = new File("./reports/images/" + number + ".png");
			FileUtils.copyFile(src, des);
			return number;
		} catch (IOException e) {
		}
		return number;
	}

	public AppiumDriver<WebElement> getDriver() {
		return tlDriver.get();
	}
	
	public static String scrollDown() {
		//APPLICATION_LOGS.info(" Executing scroll down Keyword");
		try {
			//(sObject);
			//MobileElement Me = (MobileElement) GetWebElement(sObject);
			//System.out.println(Me);
			//Me.click();
			//MobileElement scrollToElementUsing_getChildByDescription(String scrollableList, String uiSelector, String contentDesc) {
		/*	MobileElement element = driver.findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().resourceXp(\"com.android.vending:id/data_view\")).scrollIntoView("
	/*				+ "new UiSelector().text(\"Unblock Me FREE\"))"));*/
			
		AndroidElement list = (AndroidElement)driver.findElementsByXPath("android.widget.ScrollView");
				
				MobileElement Me = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector()).scrollIntoView("+ "new UiSelector().text(\"E BANKING FUND TRANSFER\"));"));
				System.out.println(Me.getLocation())	;
				Me.click();
				
		} catch (Throwable t) {
			t.printStackTrace();
			return "Fail";
		}

		//APPLICATION_LOGS.info(" Executing scroll Down Keyword Complete");
		
		return "Pass";
	}
}

package mobile.api;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import mobile.pages.HomePage;
import utils.DataInputProvider;

public class MobileHooks extends MobileWrapper{

	public String dataSheetName, nodes;

	@BeforeSuite
	public void initReport() {
		startReport();
	}

	@BeforeClass
	public void beginTestReporting() {
		startTestCase(testCaseName, testDescription);
	}

	@BeforeMethod
	public void launchApp() {		
		String appPackage = properties.getProperty("Package"), appActivity = properties.getProperty("Activity"),  
		deviceName = properties.getProperty("DeviceName"), udid = properties.getProperty("UDID");
		startTestNode(nodes);
		launchApp(appPackage, appActivity, deviceName, udid);
	}
	
	@BeforeMethod
	public void login() {		
		new HomePage()
		.clickGetStarted()
		.enterUsername(properties.getProperty("Retail_UserName"))
		.enterPassword(properties.getProperty("Retail_Password"))
		.clickLogin();
	}

	@AfterMethod
	public void quiteApp() {
		closeApp();
	}

	@AfterSuite
	public void publishResult() {
		endResult();
	}

	@DataProvider(name = "fetchData")
	public Object[][] getData() {
		return DataInputProvider.readSheet(dataSheetName);
	}

}

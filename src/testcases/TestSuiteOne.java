package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import constants.TestDataConstants;
import functionality.ComposeMailFunctions;
import functionality.HomePageFunctions;
import functionality.LoginPageFunctions;
import pageobjects.LoginPage;
import projectsetup.Baseclass;
import reporting.Logfile;
import utilities.BasicUtilities;

@Listeners(reporting.Results.class)
public class TestSuiteOne extends Baseclass implements TestDataConstants{
	
	public static WebDriver driver;
	LoginPageFunctions lpf;
	HomePageFunctions hpf;
	ComposeMailFunctions cmpf;
	LoginPage lp;
	Logfile logger;
	BasicUtilities utilities;
	
	@Parameters({"Browser"})
	@BeforeTest
	public void SetUp(@Optional("Firefox") String Browser){
//		eyes = new Eyes();
		System.out.println(Browser);
		driver = getDriver(Browser);	
//		eyes.setApiKey("VM3w101C103Ki5hJtKdd36UM9sMSkCvxkoaObjkIFFcyt9I110");
//		eyes.checkWindow("Driver");
	}
	
	
	@AfterTest
	public void quitDriver(){
		driver.quit();
	}	
	
	@BeforeClass
	public void initiate(){
		hpf = new HomePageFunctions(driver);
		lpf = new LoginPageFunctions(driver);
		cmpf = new ComposeMailFunctions(driver);
	}

	
	@Test(priority=1)
	public void TC_001_Login(){
		lpf.login(FromEmail,FromPass);
		hpf.logOut();
	}
	
	@Test(priority=2)
	public void TC_002_SendAMailAndVerify(){
		lpf.login(FromEmail,FromPass);
		cmpf.SendAMail();
		hpf.logOut();
	}

	@Test(priority=3)
	public void TC_003_verifyReceivedMail(){
		lpf.login(ToEmail,Topass);
		hpf.selectMail(FromEmail, Subject);
//		lpf.verifyMessage(Message);
//		hpf.logOut();
	}
	
	@Test(priority=4)
	public void TC_004_DeleteMail(){
//		lpf.login(ToEmail,Topass);
//		hpf.selectMail(FromEmail, Subject);
		hpf.deleteMail();
		hpf.logOut();
	}
}

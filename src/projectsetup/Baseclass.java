package projectsetup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Baseclass {
	
	String project_location = System.getProperty("user.dir").replace("\\", "/");
	public WebDriver driver;
	
	/** Function to choose the browser 
	 * @param Browser : Browser to invoke (pass 'Firefox' or 'Chrome' or 'InternerExplorer'
	 * @return WebDriver
	 * @throws Exception
	 */
	public WebDriver getDriver(String Browser){
		switch(Browser){
			case "Firefox"  : 
				System.setProperty("webdriver.gecko.driver", project_location+"/Lib/Drivers/geckodriver.exe");
				driver = new FirefoxDriver(); 
				driver.manage().window().maximize();
//				driver.manage().window().fullscreen();
//				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				break;
				
			case "Chrome" :
				System.setProperty("webdriver.chrome.driver", project_location+"/Lib/Drivers/chromedriver.exe");
				driver = new ChromeDriver(); 
				driver.manage().window().maximize();
//				driver.manage().window().fullscreen();
//				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				break;
				
			case "InternerExplorer" :
				System.setProperty("webdriver.ie.driver", project_location+"/Lib/Drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver(); 
				driver.manage().window().maximize();
//				driver.manage().window().fullscreen();
//				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				break;
			
			default : 
			try {
				throw new Exception("Enter valid browser name");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		driver.manage().deleteAllCookies();
		return driver;
	}
	
	/**
	 * Function to navigate to AUT
	 * @param URL : URL of the application to test
	 */
	public void navigateTOURL(String URL){
		driver.get(URL);
	}
	
	public DesiredCapabilities setDesiredCapabilitiesforFirefox(){
		FirefoxProfile profile = new FirefoxProfile();
		DesiredCapabilities dc=DesiredCapabilities.firefox();
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(true);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.showWhenStarting",false); 
		profile.setPreference("browser.download.dir", project_location+"/TestData/DownloadedFiles"); 
		profile.setPreference("browser.download.downloadDir",project_location+"/TestData/DownloadedFiles"); 
		profile.setPreference("browser.download.defaultFolder",project_location+"/TestData/DownloadedFiles"); 
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;"//MIME types Of MS Excel File.
			    + "application/pdf;" //MIME types Of PDF File.
			    + "application/vnd.openxmlformats-officedocument.wordprocessingml.document;" //MIME types Of MS doc File.
			    + "text/plain;" //MIME types Of text File.
			    + "text/csv" );
		profile.setPreference("browser.helperApps.neverAsk.openFile","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;"//MIME types Of MS Excel File.
			    + "application/pdf;" //MIME types Of PDF File.
			    + "application/vnd.openxmlformats-officedocument.wordprocessingml.document;" //MIME types Of MS doc File.
			    + "text/plain;" //MIME types Of text File.
			    + "text/csv");
		dc.setCapability(FirefoxDriver.PROFILE, profile);
		return dc;
	}
	
	public DesiredCapabilities setDesiredCapabilitiesforChrome(){
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-popup-blocking", "true");
		options.addArguments("download.default_directory",project_location+"/TestData/DownloadedFiles");
		options.addArguments("download.directory_upgrade", "true");
		options.addArguments("download.prompt_for_download", "false");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY,options);
		return cap;
	}
	
	public DesiredCapabilities setDesiredCapabilitiesforIE(){
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return dc;
	}
}

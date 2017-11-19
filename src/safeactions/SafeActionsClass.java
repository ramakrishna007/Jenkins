package safeactions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import pageobjects.LoginPage;
import reporting.Logfile;
import utilities.BasicUtilities;

public class SafeActionsClass {
	
	public WebDriver driver;
	public JavascriptExecutor js;
	
	LoginPage hp;
	LoginPage lp;
	Logfile logger;
	BasicUtilities utilities;
	
	public SafeActionsClass(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor)driver;
		utilities = new BasicUtilities(driver);
		logger = new Logfile(driver);
	}
	
	public void safeType(String string, WebElement element){
		if(utilities.elementExists(element)){
			element.sendKeys(string);
		}
	}
	
	public void safeClick(WebElement element){
		if(utilities.elementExists(element)){
			utilities.clickElement(element);
		}
	}
}

package functionality;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import constants.TestDataConstants;
import pageobjects.ComposeMail;
import pageobjects.HomePage;
import reporting.Logfile;
import safeactions.SafeActionsClass;
import utilities.BasicUtilities;

public class ComposeMailFunctions extends SafeActionsClass implements TestDataConstants{

	public WebDriver driver;
	BasicUtilities utilities;
	Logfile logger;
	HomePage hp;
	ComposeMail cmp;
	
	public ComposeMailFunctions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		utilities = new BasicUtilities(driver);
		cmp = new ComposeMail(driver);
		hp = new HomePage(driver);
		logger = new Logfile(driver);
	}	
	
	public void clickCompose () {
		safeClick(hp.Btn_Compose);
	}
	
	public void setToAddress(String toaddress){
		safeClick(cmp.Tbx_To);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		safeType(toaddress, cmp.Tbx_To);
	}
	
	public void setSubject(String subject){
		safeClick(cmp.Tbx_Subject);
		safeType(subject, cmp.Tbx_Subject);
	}
	
	public void setMessage(String message){
		safeClick(cmp.Tbx_MessagBody);
		safeType(message, cmp.Tbx_MessagBody);
	}
	
	public void clickSend(){
		safeClick(cmp.Btn_Send);
	}
	
	public boolean verifyMailSent(){
		if (utilities.elementExists(cmp.WebEle_ackBanner) && utilities.elementVisible(cmp.WebEle_ackBanner))
			return true;
		else return false;
	}
	
	public void SendAMail(){
		clickCompose();
		setToAddress(ToEmail);
		utilities.pressTab();
		setSubject(Subject);
		utilities.pressTab();
		setMessage(Message);
		clickSend();
		Assert.assertTrue(verifyMailSent(), "Message is NOT sent successfully");
	}
}

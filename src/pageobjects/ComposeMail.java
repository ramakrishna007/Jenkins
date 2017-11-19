package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ComposeMail {
public WebDriver driver;
	
	public ComposeMail(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}	
	
	@FindBy(className="aaZ")
	public WebElement WebEle_Msgbox;

	@FindBy(name="to")
	public WebElement Tbx_To;
	
	@FindBy(xpath="//*[@name='subjectbox']")
	public WebElement Tbx_Subject;
	
	@FindBy(xpath="//*[@aria-label='Message Body' and contains(@class,'editable')]")
	public WebElement Tbx_MessagBody;
	
	@FindBy(xpath="//*[@aria-label='Send ‪(Ctrl-Enter)‬']")
	public WebElement Btn_Send;
	
	@FindBy(xpath="//*[contains(text(),'Your message has been sent')]")
	public WebElement WebEle_ackBanner;
}


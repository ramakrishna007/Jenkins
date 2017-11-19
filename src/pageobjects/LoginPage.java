package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	public WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="identifierId")
	public WebElement Tbx_Username;
	
	@FindBy(id="identifierNext")
	public WebElement Btn_Next;
	
	@FindBy(name="password")
	public WebElement Tbx_Password;
	
	@FindBy(id="passwordNext")
	public WebElement Btn_Signin;
	
	@FindBy(id="PersistentCookie")
	public WebElement Cbx_StaySignedin;
	
	@FindBy(xpath="//*[@aria-label='Switch account']") 
	public WebElement DD_SwitchAccount;
	
	@FindBy(xpath=".//*[@id='view_container']//*[@role='button']")
	public WebElement Btn_Done;
	
	@FindBy(id="reauthEmail")
	public WebElement Txt_Email;
	
	@FindBy(xpath="//*[contains(@id,'choose-account')]")
	public WebElement WebEle_ChooseAccount;
	
	@FindBy(id="identifierLink")
	public WebElement Lnk_UseAccount;
	
	@FindBy(id="edit-account-list")
	public WebElement Lnk_Remove;
	
	@FindBy(id="initialView")
	public WebElement WebEle_InitialView;
	
	@FindBy(id="signin_status")
	public WebElement Txt_SigninStatus;
	
	@FindBy(xpath="//*[contains(text(),'Sign In')]")
	public WebElement Lnk_Gmail;
	
	@FindBy(xpath="//*[text()='Done']")
	public WebElement Btn_DoneSignIn;
}

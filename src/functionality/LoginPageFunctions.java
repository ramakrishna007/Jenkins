package functionality;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import constants.TestDataConstants;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import reporting.Logfile;
import safeactions.SafeActionsClass;
import utilities.BasicUtilities;

public class LoginPageFunctions extends SafeActionsClass implements TestDataConstants{

	public WebDriver driver;
	LoginPage lp;
	HomePage hp;	
	Logfile logger;
	BasicUtilities utilities;
	SafeActionsClass safeactions;
	SoftAssert assertion;
	
	public LoginPageFunctions(WebDriver driver) {
		super(driver);
		this.driver = driver;
//		PageFactory.initElements(driver, this);
		lp = new LoginPage(driver);
		hp = new HomePage(driver);
		logger = new Logfile(driver);
		utilities = new BasicUtilities(driver);
		assertion = new SoftAssert();
	}
	
	public void navigateToURL(String URL){
		driver.get(URL);
	}
	
	public void setUsername(String username){
		safeType(username, lp.Tbx_Username);
	}
	
	public void setPassword(String password){
		safeType(password, lp.Tbx_Password);
	}
	
	public void clickNext(){
		safeClick(lp.Btn_Next);
	}
	
	public void clickSignin(){
		safeClick(lp.Btn_Signin);
	}
	
	public void clickSignInWithDifferentAccount(){
		safeClick(lp.DD_SwitchAccount);
	}
	
	public void clickAddAccount(){
		safeClick(lp.Lnk_UseAccount);
	}
	
	

	public void verifyMessage(String message){
		System.out.println(hp.Txt_MessageBody.getText());
		System.out.println(message);
		System.out.println(hp.Txt_MessageBody.getText().trim().equals(message.trim()));
		assertion.assertTrue(hp.Txt_MessageBody.getText().equals(message),"Message is NOT received correctly");
	}

	public void login(String Username,String Password){
		navigateToURL(BaseURL);
		if (utilities.elementExists(lp.Lnk_Gmail)){
			utilities.clickElement(lp.Lnk_Gmail);
		}
		if (utilities.elementVisible(lp.DD_SwitchAccount)){
			clickSignInWithDifferentAccount();
			clickAddAccount();
		}else if(utilities.elementVisible(lp.Lnk_UseAccount)){
			clickAddAccount();
			clickAddAccount();
		}
		setUsername(Username);
		clickNext();
		setPassword(Password);
		clickSignin();
		if (utilities.elementExists(lp.Btn_DoneSignIn)){
			utilities.clickElement(lp.Btn_DoneSignIn);
		}
		assertion.assertTrue(utilities.elementExists(hp.Btn_Compose), "Login is NOT successful");
	}
}

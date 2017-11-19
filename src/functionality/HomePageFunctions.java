package functionality;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import constants.TestDataConstants;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import reporting.Logfile;
import safeactions.SafeActionsClass;
import utilities.BasicUtilities;

public class HomePageFunctions extends SafeActionsClass implements TestDataConstants{
	public WebDriver driver;
	BasicUtilities utilities;
	Logfile logger;
	HomePage hp;
	LoginPage lp;
	
	public HomePageFunctions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		utilities = new BasicUtilities(driver);
		hp = new HomePage(driver);
		lp = new LoginPage(driver);
		logger = new Logfile(driver);
	}
	
	public void clickMoreButton(){
		safeClick(hp.Btn_More);
	}
	
	public void clickAccountOptions(){
		safeClick(hp.Btn_AccountOptions);
	}
	
	public void clickSignOut(){
		safeClick(hp.Btn_Signout);
	}
		
	public void selectLabel(){
		for(WebElement label :hp.ind_leftpane){
			try{
				safeClick(label);
			}catch(Exception e){
				e.printStackTrace();
				logger.logToFile(e.toString());
				clickMoreButton();
				safeClick(label);
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String title = driver.getTitle().toString();
			Assert.assertTrue(title.contains(label.getText().toString()),"label and browser title are NOT same");			
		}
	}
	
	public void logOut() {
		try{
			Thread.sleep(5000);
			clickAccountOptions();
			clickSignOut();
			if (utilities.isAlertPresent()){
				driver.switchTo().alert().accept();
			}
		}catch(Exception e){
			System.out.println(e);
		}
//		Assert.assertTrue(lp.Txt_SigninStatus.getText().equalsIgnoreCase("Signed out"));
//		System.out.println(utilities.elementExists(lp.WebEle_RsiCard));
//		System.out.println(utilities.elementExists(lp.Lnk_AddAccount));
		Assert.assertTrue(utilities.elementExists(lp.WebEle_InitialView) || utilities.elementExists(lp.Lnk_UseAccount),"LogOut Unsuccessful");
	}
	
	public void openEmail(String from,String subject){
		if(hp.Txt_FromEmail.size()==0){
			logger.logToFile("Inbox is Empty. No Email was received");;
		}
		for (WebElement fromemail : hp.Txt_FromEmail){
			String email = fromemail.getAttribute("email").toString();
			System.out.println(email);
			if (email.equals(from)){
				for (WebElement fromsubject : hp.Txt_FromSubject){
					String subjects = fromsubject.getText().toString();
					System.out.println(subjects);
					if (subjects.equals(subject)){
						utilities.clickElement(fromsubject);
						break;
					}
				}
				break;
			}
		}
		Assert.assertTrue(hp.Txt_MainSubject.getText().equals(subject),"Correct mail is not received");
	}
	
	public void selectMail(String from,String subject){
		if(hp.Txt_FromEmail.size()==0){
			logger.logToFile("Inbox is Empty. No Email was received");;
		}
		for (WebElement fromemail : hp.Txt_FromEmail){
			String email = fromemail.getAttribute("email").toString();
			if (email.equals(from)){
				for (WebElement fromsubject : hp.Txt_FromSubject){
					String subjects = fromsubject.getText().toString();
					if (subjects.equals(subject)){
						int index = hp.Txt_FromSubject.indexOf(fromsubject);
						utilities.clickElement(hp.Cbx_Selectmail.get(index));
						break;
					}
				}
				break;
			}
		}
	}
	
	public void deleteMail(){
		utilities.HighlightElement(hp.Btn_Delete);
		utilities.clickElement(hp.Btn_Delete);
	}
}

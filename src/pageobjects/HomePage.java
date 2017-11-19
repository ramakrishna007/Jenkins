package pageobjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	public WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}	
	
	@FindBy(xpath=".//*[contains(text(),'COMPOSE')]")
	public WebElement Btn_Compose;
	
	@FindBy(xpath=".//*[@class='TN GLujEb']//*/a")
	public List<WebElement> ind_leftpane;
		
	@FindBy(xpath="//span[contains(text(),'More')]")
	public WebElement Btn_More;
	
	@FindBy(xpath="//a[contains(@href,'SignOutOptions')]")
	public WebElement Btn_AccountOptions;
	
	@FindBy(xpath="//a[contains(text(),'Sign out')]")
	public WebElement Btn_Signout;	
	
	@FindBy(xpath="//*[(contains(text(),'Inbox'))]")
	public WebElement Lnk_Inbox;
	
	@FindBy(xpath="//*[contains(@email,'@')]")
	public List<WebElement> Txt_FromEmail;
	
	@FindBy(xpath="//div[@role='link']//span[1]")
	public List<WebElement> Txt_FromSubject;
	
	@FindBy(xpath="//div[@role='link']//span[2]")
	public List<WebElement> Txt_FromMessage;
	
	@FindBy(xpath="//*[@role='main']//h2")
	public WebElement Txt_MainSubject;
	
	@FindBy(xpath="//*[@class='adn ads']//div[@dir]")
	public WebElement Txt_MessageBody;
	
	@FindBy(xpath="//div[@role='checkbox']")
	public List<WebElement> Cbx_Selectmail;
	
	@FindBy(xpath="//div[@act='10']")
	public WebElement Btn_Delete;
	
//	@FindBy(css="div[act='10']>div")
//	public WebElement Btn_Delete;
	
}

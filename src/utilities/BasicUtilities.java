package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import reporting.Logfile;

public class BasicUtilities {
	public static ATUTestRecorder recorder;
	public static WebDriver driver;
	public static WebDriverWait wait;
	public JavascriptExecutor js;
	public static Logfile logger;
	String project_location = System.getProperty("user.dir").replace("\\", "/");
	
	public BasicUtilities(WebDriver sdriver){
		driver = sdriver;
		wait = new WebDriverWait(driver, 10);
		js = (JavascriptExecutor)driver;
		logger = new Logfile(driver);
	}
	
	public BasicUtilities(){
		
	}
	
	public void startRecording(String method) throws ATUTestRecorderException{
		recorder = new ATUTestRecorder(project_location+"/Reports/Recordings/"+getDate(),method,false);
		recorder.start();
	}
	
	public void stopRecording() throws ATUTestRecorderException{
		recorder.stop();
	}
	
	public void getScreenShot(String method) throws IOException{
		captureScreenShot(method);
	}
	
	/**
	 * Function to wait for 'time' seconds
	 * @param time : time in seconds
	 */
	public void implicitlyWaitFor(int time){
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public void captureScreenShot(String method) {
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File file = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File(project_location+"/Reports/Screenshots/"+getDate()+"/"+method+".png"));
		} catch (IOException e) {
			e.printStackTrace();
			logger.logToFile(e.toString());
		}		
	}
	
	public void getElementScreenshot(WebElement ele) throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File file = screenshot.getScreenshotAs(OutputType.FILE);
		BufferedImage image = ImageIO.read(file);
		BufferedImage elementimage = image.getSubimage(ele.getLocation().getX(), ele.getLocation().getY(),ele.getSize().width, ele.getSize().height);
		ImageIO.write(elementimage, "png", file);
//		FileUtils.copyFile(file, new File(project_location+""));
	}
		
	public boolean synchronize(WebElement element){
		try{
			do{
				
			}while(!js.executeScript("return document.readyState").equals("complete"));
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		}catch(TimeoutException e){
			System.out.println(element+ "doesn't exist on the page");
			logger.logToFile(element+ "doesn't exist on the page");
			logger.logToFile(e.toString());
			return false;
		}
	}
	
	public void clickElement(WebElement element){
		if (elementExists(element)){
			element.click();
		}else{
			System.out.println(element+ "doesn't exist on the page");
			logger.logToFile(element+ "doesn't exist on the page");
		}
	}
	
	public boolean elementExists(WebElement element){
		try{
			synchronize(element);
		}catch(Exception e){
			System.out.println(element+ "doesn't exist on the page");
			logger.logToFile(element+ "doesn't exist on the page");
			return false;
		}
		try{
			if(element.getSize() != null){
				System.out.println(element+ "do exist on the page");
				logger.logToFile(element+ "do exist on the page");
				return true;
			}
			else{
				System.out.println(element+ "doesn't exist on the page");
				logger.logToFile(element+ "doesn't exist on the page");
				return false;
			}
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}
	
	public boolean elementVisible(WebElement element){
		try{
			if(element.isDisplayed()){
				return true;
			}else return false;
		}catch(NoSuchElementException e){

			return false;
		}
	}
	
	public void HighlightElement(WebElement element){
		for(int i=0;i<5;i++){
			js.executeScript("arguments[0].setAttribute('style','border: solid 3px Red')", element);
			js.executeScript("arguments[0].setAttribute('style','border: ')", element);
			js.executeScript("arguments[0].setAttribute('style','border: solid 3px Red')", element);
			js.executeScript("arguments[0].setAttribute('style','border: ')", element);
		}
	}
	
	public void pressTab(){
		driver.findElement(By.xpath("//body")).sendKeys(Keys.TAB);
	}
	
	public boolean isAlertPresent(){
		try{
			driver.switchTo().alert();
			return true;
		}catch(NoAlertPresentException e){
			return false;
		}
	}
	
	public String getDate(){
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yy");
		Date date = new Date();
		return dateformat.format(date);
	}
}

package reporting;

import java.io.File;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Logfile {
	
	public WebDriver driver;
	public static Logger log;
	String project_location = System.getProperty("user.dir").replace("\\", "/");
	File textLogFile = new File(project_location+"/log.txt");
	
	public Logfile(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		if (textLogFile.exists())
			textLogFile.delete();
		log = Logger.getLogger("Logfile");

	}
	
	public Logfile(){
		log = Logger.getLogger("Logfile");
	}
	
	/**
	 * Function to log messages in text file
	 * @param Message : Message to log
	 */
	public void logToFile(String Message){
		Properties log4jProperties = new Properties();
		log4jProperties.setProperty("log4j.rootLogger", "INFO, FILE");
		log4jProperties.setProperty("log4j.appender.FILE", "org.apache.log4j.RollingFileAppender");
		log4jProperties.setProperty("log4j.appender.FILE.file", "log.txt");
		log4jProperties.setProperty("log4j.appender.FILE.layout", "org.apache.log4j.PatternLayout");
		log4jProperties.setProperty("log4j.appender.FILE.layout.ConversionPattern", "%-5p %c %x - %m%n");
		log4jProperties.setProperty("log4j.appender.FILE.immediateFlush", "false");
		log4jProperties.setProperty("log4j.appender.FILE.Append", "true");
		PropertyConfigurator.configure(log4jProperties);
		log.info(Message);
	}
}

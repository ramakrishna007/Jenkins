package reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import atu.testrecorder.exceptions.ATUTestRecorderException;
import utilities.BasicUtilities;

public class Results implements ITestListener,ISuiteListener,IRetryAnalyzer	{
	private static final String path = null;
	int count = 0;
	int maxcount = 3;
	FileWriter filer = null;
	BufferedWriter file;
	String suitename;
	String project_location = System.getProperty("user.dir").replace("\\", "/");
	File doc = new File(project_location+"/Reports/Results.html");
	BasicUtilities Utilities = new BasicUtilities();
	Logfile logger = new Logfile();
	
	@Override
	public boolean retry(ITestResult result) {
		if(count<maxcount){
			count++;
			return true;
		}
		return false;
	}

	@Override
	public void onStart(ISuite suite) {
		suitename = suite.getXmlSuite().getName();
		createDirectories();
		System.out.println("<<------------------------------->>");
		System.out.println(suite.getName() + " : Suite Starting...");
		System.out.println("<<------------------------------->>");
		try {
			filer= new FileWriter(doc);
		} catch (IOException e) {
			new File("../../").mkdir();
			try {
				filer= new FileWriter(doc);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 	
						
		file = new BufferedWriter(filer);
		String html="";
		html = html +  "<html><head><style media=\"screen\" type=\"text/css\">";
		html = html +  ".canv{background: #0b6690 ;padding: 8px;position: relative;margin: auto;";
		html = html +  "display: block;color: white;text-decoration:none;text-align:center;border:none;width:200;}";
		html = html +  ".canv:hover{ background: #e98828 ;padding: 8px;position: relative;margin: auto;";
		html = html +  "display: block;color: white;text-decoration:none;text-align:center;width:200;}";
		html = html +  "body{color:#8b8e94;font-family:Segoe UI;font-size:16px;vertical-align:middle;background: #F1F7F9;}";
		html = html +  "h2{color: #0b6690;Text-transform:uppercase;font-family:Segoe UI Semibold;font-size:20px;}";
		html = html +  "th{Text-transform:uppercase;background: #0b6690 ;padding: 5px;color: white;}";
		html = html +  ".clr{display: table;border-collapse: separate;border-spacing: 2px;border-color: gray;}";
		html = html +  ".fail{font-family:Segoe UI;font-size:14px;vertical-align:middle;color:white;background:red;}";
		html = html +  ".pass{font-family:Segoe UI;font-size:14px;vertical-align:middle;color:white;background:green;}";
		html = html +  ".skip{font-family:Segoe UI;font-size:14px;vertical-align:middle;color:white;background:orange;}";
		html = html +  ".clr tr:nth-child(odd) { background-color: #ccc;}";
		html = html +  "</tr>";
		html = html +  "</style></head><body>";
		html = html +  "<table Width=90% border=0 align=center><tr><td width=75%><h2>";
		String html1 = "";
		html1 = html1 + "SUITE NAME: "+ suitename;
		html1 = html1 + "</h2></td><td width=15% ><button class = canv onClick=\"history.go(-1);return true;\">RETURN TO SUMMARY</button></td>";
		html1 = html1 + "</tr></table>";
		html1 = html1 + "<Table class=clr Width=90% align=center><tr>";
		html1 = html1 + "<th BGCOLOR=lightgrey width=40%>Test Case</th>";
		html1 = html1 + "<th BGCOLOR=lightgrey width=15%>Status</th>";
		html1 = html1 + "<th BGCOLOR=lightgrey width=15%>Time (IST)</th>";
		html1 = html1 + "<th BGCOLOR=lightgrey width=25%>Screenshots</th>";
		html1 = html1 + "</tr>";
		try {
			file.write(html+html1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	 
	
	private void createDirectories() {
//		new File(project_location+"/Reports").delete();
		new File(project_location+"/Reports").mkdir();
		new File(project_location+"/Reports/Screenshots/"+Utilities.getDate()).mkdirs();
//		new File(project_location+"/Reports/Recordings").mkdir();
		new File(project_location+"/Reports/Recordings/"+Utilities.getDate()).mkdirs();
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("<<------------------------------->>");
		System.out.println(suite.getName() + " : Suite Finished");		
		System.out.println("<<------------------------------->>");
		try {
			Runtime.getRuntime().exec("C:\\Users\\RAMA KRISHNA\\Desktop\\killProcess.bat");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("<<------------------------------->>");
		System.out.println(result.getMethod().getMethodName() + " : Test Starting");
		System.out.println("<<------------------------------->>");
		logger.logToFile(result.getMethod().getMethodName().toString() + " : Test Starting");
		try {
			Utilities.startRecording(result.getMethod().getMethodName().toString());
		} catch (ATUTestRecorderException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		try {
			String Status = "PASS";
			method(Status,result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.logToFile(result.getMethod().getMethodName().toString() + " : Test Passed");
		System.out.println("<<------------------------------->>");
		System.out.println(result.getMethod().getMethodName() + " : Test Passed");	
		System.out.println("<<------------------------------->>");
		try {
			Utilities.stopRecording();
		} catch (ATUTestRecorderException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			String Status = "FAIL";
			method(Status,result);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		StackTraceElement[] path = Thread.currentThread().getStackTrace();
		for(StackTraceElement x : path) {
			System.err.println(x);
		}
		logger.logToFile(result.getMethod().getMethodName().toString() + " : Test Failed");
		System.out.println("<<------------------------------->>");
		System.out.println(result.getMethod().getMethodName() + " : Test Failed");
		System.out.println("<<------------------------------->>");
		try {
			Utilities.getScreenShot(result.getMethod().getMethodName().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Utilities.stopRecording();
		} catch (ATUTestRecorderException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		try {
			String Status = "SKIP";
			method(Status,result);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		logger.logToFile(result.getMethod().getMethodName().toString() + " : Test Skipped");
		System.out.println("<<------------------------------->>");
		System.out.println(result.getMethod().getMethodName() + " : Test Skipped");
		System.out.println("<<------------------------------->>");
		try {
			Utilities.stopRecording();
		} catch (ATUTestRecorderException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("<<------------------------------->>");
		System.out.println(context.getName() + " : Method called");
		System.out.println("<<------------------------------->>");
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.logToFile(context.getName().toString() + " :Method finished");
		System.out.println("<<------------------------------->>");
		System.out.println(context.getName() + " :Method finished");
		System.out.println("<<------------------------------->>");
	}
	public void method(String status, ITestResult result) throws IOException{
		String res = null;
		if (doc.exists()){
			filer= new FileWriter(doc,true);
		}
		file = new BufferedWriter(filer);
		if (status.equals("FAIL") || status.equals("SKIP")){
		res = "<tr><Td align=left  VALIGN=middle>" +result.getMethod().getMethodName()+ "</Td>"+
				"<Td align=center  Valign=middle class="+status+">"+status +
	      "<Td align=center >" + new Date()+ "</td>" +
	      "<Td align=center  VALIGN=middle><A HREF = \\"+project_location+"/Reports/Screenshots/"+Utilities.getDate()+result.getMethod().getMethodName()+".png"+" class = canv>VIEW SCREENSHOT</A></td></tr>";}
		else if (status.equals("PASS")){
			res = "<tr><Td align=left  VALIGN=middle>" +result.getMethod().getMethodName()+ "</Td>"+
					"<Td align=center  Valign=middle class="+status+">"+status +
		      "<Td align=center >" + new Date()+ "</td>" +
		      "<Td align=center  VALIGN=middle><A"  +" class = clr>NONE</A></td></tr>";
		}
		file.write(res);
		file.close();
	}
}

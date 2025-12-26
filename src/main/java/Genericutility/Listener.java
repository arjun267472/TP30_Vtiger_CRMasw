package Genericutility;



import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listener implements ITestListener {

	ExtentReports report;
	ExtentTest test;

	public static ThreadLocal<ExtentTest> extent = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		test = report.createTest(methodName);
		extent.set(test);
		extent.get().log(Status.INFO, methodName + " execution starts");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		extent.get().log(Status.PASS, methodName + "<b> -----> passed</b>");

	}

	@Override
	public void onTestFailure(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		String fileName = methodName + new JavaUtils().sysDate();

		try {

			//TakesScreenshot ts = (TakesScreenshot) BaseClass.getDriver();
			
			//taking screenshot in base64 format
			//String src = ts.getScreenshotAs(OutputType.BASE64);
			
			// Add screenshot to Extent report
	       
             // getThrowable ---->to print the Exception message
			extent.get().log(Status.FAIL, result.getThrowable());
			extent.get().log(Status.FAIL, methodName + "<i>---> failed</i>"); //<b> </b> -----bold text format

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		extent.get().log(Status.SKIP, methodName + "---> skipped");

	}

	@Override
	public void onStart(ITestContext context) {

		// configuration the UI part of Extent Report
		ExtentSparkReporter htmlreport = new ExtentSparkReporter(
				".\\ExtentReport\\report" + new JavaUtils().sysDate() + ".html");
		htmlreport.config().setDocumentTitle("TP-30_BATCH_ADV_SEL");
		htmlreport.config().setTheme(Theme.DARK);
		htmlreport.config().setReportName("VTiger");

		// contents in html we are customising here
		report = new ExtentReports();
		report.attachReporter(htmlreport);
		report.setSystemInfo("base_browser", "chrome");
		report.setSystemInfo("base_platform", "windows");
		report.setSystemInfo("base_url", "http://localhost:8888");
		report.setSystemInfo("ReporterName", "Aswathi");

	}

	@Override
	public void onFinish(ITestContext context) {

		report.flush();

	}

}

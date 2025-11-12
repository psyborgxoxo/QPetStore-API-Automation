//package api.utilities;
//
////Extent report 5.x
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import com.aventstack.extentreports.reporter.configuration.Theme;
//
//public class ExtentReportManager implements ITestListener
//{
//    public ExtentSparkReporter sparkReporter;
//    public ExtentReports extent;
//    public ExtentTest test;
//
//    String repName;
//
//    public void onStart(ITestContext testContext)
//    {
//        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
//        repName="Test-Report-"+timeStamp+".html";
//
//        sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);//specify location of the report
//
//        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // Title of report
//        sparkReporter.config().setReportName("Pet Store Users API"); // name of the report
//        sparkReporter.config().setTheme(Theme.DARK);
//
//        extent=new ExtentReports();
//        extent.attachReporter(sparkReporter);
//        extent.setSystemInfo("Application", "Pest Store Users API");
//        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
//        extent.setSystemInfo("User Name", System.getProperty("user.name"));
//        extent.setSystemInfo("Environemnt","QA");
//        extent.setSystemInfo("user","Sourav Shetty");
//    }
//
//
//    public void onTestSuccess(ITestResult result)
//    {
//        test=extent.createTest(result.getName());
//        test.assignCategory(result.getMethod().getGroups());
//        test.createNode(result.getName());
//        test.log(Status.PASS, "Test Passed");
//    }
//
//    public void onTestFailure(ITestResult result)
//    {
//        test=extent.createTest(result.getName());
//        test.createNode(result.getName());
//        test.assignCategory(result.getMethod().getGroups());
//        test.log(Status.FAIL, "Test Failed");
//        test.log(Status.FAIL, result.getThrowable().getMessage());
//    }
//
//    public void onTestSkipped(ITestResult result)
//    {
//        test=extent.createTest(result.getName());
//        test.createNode(result.getName());
//        test.assignCategory(result.getMethod().getGroups());
//        test.log(Status.SKIP, "Test Skipped");
//        test.log(Status.SKIP, result.getThrowable().getMessage());
//    }
//
//    public void onFinish(ITestContext testContext)
//    {
//        extent.flush();
//    }
//
//}

package api.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * ⚡ Advanced Extent Report Manager (UI Enhanced)
 * Supports: Parallel execution, UI branding, screenshots, logs, and metadata
 * Author: Sourav Shetty
 */
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    @Override
    public synchronized void onStart(ITestContext context) {
        if (extent == null) {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String reportFolder = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "Run-" + timeStamp;
            new File(reportFolder).mkdirs();

            reportPath = reportFolder + File.separator + "ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            // 🧩 Custom UI configuration
            spark.config().setDocumentTitle("🚀 Automation Test Dashboard");
            spark.config().setReportName("QTripDynamic API & UI Automation");
            spark.config().setTheme(Theme.DARK);
            spark.config().setEncoding("utf-8");
            spark.config().setCss(
                    ".badge-primary{background-color:#7a63ff}" +
                            ".category-label{color:#ffb347;font-weight:600}" +
                            ".card-title{font-size:16px;font-weight:600}" +
                            ".logo {max-height:40px;border-radius:8px;}" +
                            ".test-name {font-weight:600; color:#00ffe5;}"
            );
            spark.config().setJs(
                    "document.title='Sourav Shetty Automation Report';" +
                            "document.querySelector('.report-name').style.color='#00ffff';" +
                            "document.querySelector('.nav-wrapper').style.background='#181818';" +
                            "console.log('⚡ Advanced Report UI Loaded');"
            );

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // 🌐 Environment Info
            extent.setSystemInfo("Application", "QTripDynamic / Pet Store APIs");
            extent.setSystemInfo("Framework", "TestNG + RestAssured + Selenium");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Build", "v2.5.1");
            extent.setSystemInfo("Tester", "Sourav Shetty");
        }
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest("🧪 " + result.getMethod().getMethodName())
                .assignCategory(result.getMethod().getGroups())
                .assignAuthor("Automation Bot 🤖");
        extentTest.set(test);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED ✅", ExtentColor.GREEN));
        extentTest.get().log(Status.INFO, "Executed on Thread: " + Thread.currentThread().getId());
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " FAILED ❌", ExtentColor.RED));
        extentTest.get().log(Status.FAIL, result.getThrowable());

        // 📸 Attach screenshot automatically if exists (for UI tests)
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + result.getName() + ".png";
        File screenshotFile = new File(screenshotPath);
        if (screenshotFile.exists()) {
            try {
                extentTest.get().addScreenCaptureFromPath(screenshotPath, "🔍 Screenshot on Failure");
            } catch (Exception e) {
                extentTest.get().log(Status.WARNING, "⚠️ Unable to attach screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED ⚠️", ExtentColor.ORANGE));
        if (result.getThrowable() != null) {
            extentTest.get().log(Status.SKIP, result.getThrowable());
        }
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
            System.out.println("📄 Report generated at: " + reportPath);
        }
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static ExtentReports getExtent() {
        return extent;
    }
}

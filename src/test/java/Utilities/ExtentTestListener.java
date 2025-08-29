package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent;
    private static Map<Long, ExtentTest> testMap = new HashMap<>();
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();  // set driver externally

    // Get or create ExtentReports instance
    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("QA", "Mahessh");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
        }
        return extent;
    }

	@Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = getExtentReports().createTest(result.getMethod().getMethodName());
        testMap.put(Thread.currentThread().getId(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test().log(Status.PASS, "Test Passed");
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            String screenshotPath = takeScreenshot(currentDriver, result.getMethod().getMethodName());
            try {
                test().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                test().log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
            }
        } else {
            test().log(Status.WARNING, "WebDriver is null, screenshot not captured.");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test().log(Status.FAIL, "Test Failed");
        test().log(Status.FAIL, result.getThrowable());

        // Screenshot logic
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            String screenshotPath = takeScreenshot(currentDriver, result.getMethod().getMethodName());
            try {
                test().addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                test().log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
            }
        } else {
            test().log(Status.WARNING, "WebDriver is null, screenshot not captured.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test().log(Status.SKIP, "Test Skipped");
        test().log(Status.SKIP, result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        getExtentReports().flush();
    }

    private ExtentTest test() {
        return testMap.get(Thread.currentThread().getId());
    }

    // Capture screenshot and return file path
    public String takeScreenshot(WebDriver driver, String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        String path = screenshotDir + testName + "_" + timeStamp + ".png";

        try {
            Files.createDirectories(Paths.get(screenshotDir));
            Files.copy(src.toPath(), Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    // You must set WebDriver per test before listener can use it
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void removeDriver() {
        driver.remove();
    }
}

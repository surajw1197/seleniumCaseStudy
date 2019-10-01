package testmeapp.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testmeapp.utility.Drivers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class OnlineShoppingTest {
	WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;

	@BeforeTest
	public void startReportBeforeTest() {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/extent-reports/"+new SimpleDateFormat("hh-mm-ss-ms-dd-MM-yyyy").format(new Date())+".html");
		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("HostName", "GFT NexGen Testing Stream");
		extent.setSystemInfo("Environment", "Automation Testing - Selenium");
		extent.setSystemInfo("user Name", "Suraj");

		htmlReporter.config().setDocumentTitle("Title of the report come here");
		htmlReporter.config().setReportName("Name of the Report come here");
		htmlReporter.config().setTheme(Theme.STANDARD);

		driver = Drivers.getDriver("chrome");
		driver.manage().window().maximize();

	}


	@Test(priority=1)
	public void testRegistration() {
		logger = extent.createTest("Registration Test");
		driver.get("http://10.232.237.143:443/TestMeApp/fetchcat.htm");
		driver.findElement(By.xpath("//a[contains(.,'SignUp')]")).click();
		driver.findElement(By.id("userName")).sendKeys("testuser3");
		driver.findElement(By.id("firstName")).sendKeys("user");

		WebDriverWait wait1 = new WebDriverWait(driver,10);
		boolean flag1 = wait1.until(ExpectedConditions.textToBe(By.id("err"), "Available"));
		System.out.println(flag1);
		Assert.assertTrue(flag1);

		String flag2=driver.findElement(By.id("err")).getText();
		System.out.println(flag2);
		Assert.assertEquals("Available", flag2);

		logger.log(Status.INFO, MarkupHelper.createLabel("ID is available",ExtentColor.GREEN));
		driver.findElement(By.id("lastName")).sendKeys("test");
		driver.findElement(By.id("password")).sendKeys("pass123");
		driver.findElement(By.id("pass_confirmation")).sendKeys("pass123");
		driver.findElement(By.xpath("//input[@value='Male']")).click();
		driver.findElement(By.id("emailAddress")).sendKeys("sw@gmail.com");
		driver.findElement(By.id("mobileNumber")).sendKeys("9876543210");

		//for date
		driver.findElement(By.xpath("//img[@src='images/calendar.png']")).click();
		Select month = new Select(driver.findElement(By.className("ui-datepicker-month")));
		month.selectByVisibleText("Sep");
		Select year = new Select(driver.findElement(By.className("ui-datepicker-year")));
		year.selectByVisibleText("1997");
		driver.findElement(By.linkText("25")).click();


		driver.findElement(By.id("address")).sendKeys("sinhgad college road, pune");
		Select secquestion = new Select(driver.findElement(By.id("securityQuestion")));
		secquestion.selectByVisibleText("What is your favour color?");
		driver.findElement(By.id("answer")).sendKeys("blue");

		driver.findElement(By.name("Submit")).click();

		String flag3 = driver.findElement(By.xpath("//div[@id='errormsg']/following-sibling::div[3]")).getText();

		//Assert.assertEquals("User Registered Succesfully!!! Please login",  driver.findElement(By.id("errormsg")),"Registration Successful");
		Assert.assertEquals("User Registered Succesfully!!! Please login",flag3);

		logger.log(Status.INFO, MarkupHelper.createLabel("User Registered Succesfully!!! Please login", ExtentColor.GREEN));

	}

	@Test(priority=2)
	public void testLogin() {
		logger = extent.createTest("Login Test");
		driver.navigate().to("http://10.232.237.143:443/TestMeApp/login.htm");
		driver.findElement(By.id("userName")).sendKeys("Lalitha");
		driver.findElement(By.id("password")).sendKeys("password123");
		driver.findElement(By.name("Login")).click();
		logger.log(Status.INFO,MarkupHelper.createLabel("Login Successfully ", ExtentColor.GREEN));

	}

	@Test(priority=3)
	public void testCart() throws Exception {
		//driver.get("http://10.232.237.143:443/TestMeApp/fetchcat.htm#");
		logger = extent.createTest("Test cart");

		Actions act1 =new Actions(driver);
		act1.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'All\n" + 
				"										Categories')]"))).click().pause(1000).perform();
		act1.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Electronics')]"))).click().pause(1000).perform();
		act1.moveToElement(driver.findElement(By.xpath("//span[contains(text(),'Head Phone')]"))).click().pause(1000).perform();

		driver.findElement(By.xpath("//a[contains(.,'Add to cart')]")).click();
		driver.findElement(By.xpath("//a[@href='displayCart.htm']")).click();
		String details = driver.findElement(By.xpath("//p[contains(.,'Blue tooth head phone')]")).getText();
		Assert.assertTrue(details.contains("Blue tooth head phone"));
		logger.log(Status.INFO, MarkupHelper.createLabel("added to cart successfully", ExtentColor.GREEN));


	}

	@Test(priority=4)
	public void testPayment() {
		logger = extent.createTest("Payment Test");
		driver.findElement(By.xpath("//a[contains(.,'Checkout')]")).click();
		driver.findElement(By.xpath("//input[@value='Proceed to Pay']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//label[contains(.,'HDFC Bank')]")).click();
		driver.findElement(By.xpath("//a[contains(.,'Continue')]")).click();
		driver.findElement(By.name("username")).sendKeys("123459");
		driver.findElement(By.name("password")).sendKeys("Pass@459");
		driver.findElement(By.xpath("//input[@value='LOGIN']")).click();
		driver.findElement(By.name("transpwd")).sendKeys("Trans@459");
		driver.findElement(By.xpath("//input[@value='PayNow']")).click();
		String title = driver.getTitle();
		Assert.assertEquals("Order Details", title);
		logger.log(Status.INFO,MarkupHelper.createLabel("Ordered Successfully ", ExtentColor.GREEN));


	}

	@AfterMethod
	public void getResultAfterMethod(ITestResult result) throws IOException {

		if(result.getStatus() == ITestResult.FAILURE)
		{

			logger.log(Status.FAIL,MarkupHelper.createLabel(result.getName() + " - Test case failed ", ExtentColor.RED));
			TakesScreenshot snapshot= (TakesScreenshot)driver;
			File src = snapshot.getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir")+"/extent-reports/snapshots/"+result.getName()+".png";
			//FileHandler.copy(src,new File(path));
			FileUtils.copyFile(src,new File(path));
			logger.addScreenCaptureFromPath(path,result.getName());

			logger.log(Status.FAIL,MarkupHelper.createLabel(result.getThrowable() + " - Test case failed ", ExtentColor.RED));
		}
		else if(result.getStatus() == ITestResult.SKIP){
			logger.log(Status.SKIP,MarkupHelper.createLabel(result.getName() + " - Test case Skipeed ", ExtentColor.ORANGE));
		}
	}


	@AfterTest
	public void endReportAfterTest() {
		extent.flush();
		driver.close();
	}

}

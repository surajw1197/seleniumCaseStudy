package testmeapp.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Drivers {
	 
	public static WebDriver getDriver(String browser) {

		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");
						return new ChromeDriver();
		}
		else if(browser.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", "resources\\geckodriver.exe");
						return new FirefoxDriver();
		}
		else if(browser.equals("ie")){
			System.setProperty("webdriver.ie.driver", "resources\\IEDriverServer.exe");
						return new FirefoxDriver();
		}
		else{
			System.out.println("Not a valid driver details");
			return null;
		}
	}
}

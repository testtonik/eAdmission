package eAdmission;
	import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

	public class TBase {
		
		public static WebDriver driver = null;

		// public static WebDriver driver;
		static DesiredCapabilities capability;
		public enum Browsers {
			Firefox, Chrome, IE, Android, Ipad, Iphone, Opera, Safari
		};

		public static void initDriver (Browsers browser) throws Exception {
			// instantiate a new browser based on the choice of browsers
			String host = "localhost";

			switch (browser) {
			
			case Firefox: {
				capability = DesiredCapabilities.firefox();
				break;
			}
			case Chrome: {
				capability = DesiredCapabilities.chrome();
				break;
			}

			case IE: {
				capability = DesiredCapabilities
						.internetExplorer();
				break;
			}
			
			case Android: {
				capability = DesiredCapabilities.android();
				break;
			}
			
			case Iphone: {
				capability = DesiredCapabilities.iphone();
				break;
			}
			
			case Safari: {
				capability = DesiredCapabilities.safari();
				break;
			}
			
			default: {
				throw new Exception();
			}
		}
			// open our test site's URL
			driver = new RemoteWebDriver(new URL("http://" + host
					+ ":4444/wd/hub"), capability);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		public static void navigateToLandingPage (WebDriver driver) throws Exception {
			driver.manage().deleteAllCookies();
			driver.get("https://gotovu--tst.custhelp.com/app/landing");
			driver.manage().window().maximize();
		}
		
	    public void quitDriver(){
	        driver.quit();
	        driver=null;
	    }
	
	}

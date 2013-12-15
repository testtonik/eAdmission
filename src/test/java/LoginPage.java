package eAdmission;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends Page {


	public static final String login_error_message = "The username or password that you entered is incorrect or your account has been disabled.";

	WebDriver driver;
	
	static HashMap<String, String> AD = new HashMap<String, String>();
	public LoginPage (WebDriver driver) throws Exception{
		super (driver, "Log in", AD);
		this.driver = driver;
    }
	
	@FindBy(how = How.CSS, using = "span[class*='login-error']") WebElement errorMessage;
	@FindBy(linkText = "Logout") WebElement logout;

/*
	public void doLogin () throws Exception {	
		HashMap<String, String> AD = new HashMap<String, String>();
		this.driver.get("https://gotovu--tst.custhelp.com/app/landing");
		//Login Page Object
		Page LP = new Page(driver, "Log in", AD);	
		LP.fillInput ("correct.username", "Username");
		LP.fillInput ("correct.password", "Password");	
		LP.clickButton("Log in");
	}
*/	
}

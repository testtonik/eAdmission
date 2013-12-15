package eAdmission;

import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LoginTest extends TBase {
	
	@BeforeSuite
	public void init() throws Exception{
		initDriver(Browsers.Chrome);
		navigateToLandingPage(driver);
    }	 
	
	@Test(groups = { "regression" })
	public void tests () throws Exception {
		LoginPage LP = PageFactory.initElements(driver, LoginPage.class);
		LP.fillInput ("invalid.user", "Username");
		LP.fillInput ("password1", "Password");
		LP.clickButton("Login");
		waitForErrorMessage (LP.login_error_message);
		assertEquals(LP.errorMessage.getText(), LP.login_error_message);
		invalid_password_account_details(LP);
		blank_password_account_details(LP);
		blank_user_account_details(LP);
		loginTest(LP);
	}

	public void invalid_password_account_details(LoginPage LP) throws Exception {
		LP.fillInput ("evgeny.semiletov@gmail.com", "Username");
		LP.fillInput ("incorrect.password", "Password");		
		LP.clickButton("Login");
		waitForErrorMessage (LP.login_error_message);
		assertEquals(LP.errorMessage.getText(), LP.login_error_message);
	}

//	@Test(groups = { "regression" })
	public void blank_password_account_details(LoginPage LP) throws Exception {
		LP.fillInput ("evgeny.semiletov@gmail.com", "Username");
		LP.fillInput ("", "Password");		
		LP.clickButton("Login");
		waitForErrorMessage (LP.login_error_message);
		assertEquals(LP.errorMessage.getText(), LP.login_error_message);
	}
		
//	@Test(groups = { "regression" })
	public void blank_user_account_details(LoginPage LP) throws Exception {
		LP.fillInput ("", "Username");
		LP.fillInput ("password1", "Password");		
		LP.clickButton("Login");
		waitForErrorMessage (LP.login_error_message);
		assertEquals(LP.errorMessage.getText(), LP.login_error_message);
	}

//	@Test(groups = { "regression" })
	public void loginTest (LoginPage LP) throws Exception {
		LP.fillInput ("evgeny.semiletov@gmail.com", "Username");
		LP.fillInput ("password1", "Password");	
		LP.clickButton("Login");
		assertEquals(driver.getTitle(), "Admissions centre");
		LP.logout.click();
	}
	
	public void waitForErrorMessage (final String error_message){
		final LoginPage LP = PageFactory.initElements(driver, LoginPage.class);	
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String text = null;
		        if (LP.errorMessage.isEnabled()){         
		        	text = LP.errorMessage.getText();
		        }
		        if(text.isEmpty()) return false;
		        	else return true;
		        }
		   });
	}
	
	@AfterSuite
	public void kill() throws Exception{
		quitDriver();
    }	
}

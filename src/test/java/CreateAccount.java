package eAdmission;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

public class CreateAccount {

	public static HashMap<String, String> createAccount(WebDriver driver, int current_scenario) throws Exception {
		HashMap<String, String> AD = new HashMap<String, String>();
		DataSheet dtSht = new DataSheet("Personal Information");
		HashMap<String, String> elementRepository = dtSht
				.getXPathRepository("Create an account");
		AD.put("TEST_ID",
				dtSht.getValue("TEST_ID", current_scenario));
		AD.put("scenario", String.valueOf(current_scenario));

		Helper.buttonClick(driver, AD, "Create account");
		AD = Helper.selectRadioBtnByLabel(driver, dtSht,
				AD, "What is your residency status?",
				current_scenario);
		Helper.buttonClick(driver, AD, "Continue");
		String user_name = Helper.generateString(20);
		AD = Helper.populateTxtFieldwithValue(driver, user_name,
				elementRepository, AD, "Username");
		String first_name = Helper.generateString(20);
		AD = Helper.populateTxtFieldwithValue(driver, first_name,
				elementRepository, AD, "First name");
		AD = Helper.populateTxtFieldwithValue(driver,
				"qwertyuiopasdfghjkl", elementRepository, AD,
				"Last name");
		AD = Helper.populateTxtFieldwithValue(driver, user_name
				+ "@ya.com", elementRepository, AD, "Email address");
		AD = Helper.populateTxtFieldwithValue(driver, "Password1",
				elementRepository, AD, "Password");
		AD = Helper.populateTxtFieldwithValue(driver, "Password1",
				elementRepository, AD, "Verify password");
		Helper.buttonClick(driver, AD, "Create account");
		return AD;
	}
}
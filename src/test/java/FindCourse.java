package eAdmission;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import eAdmission.TBase.Browsers;

public class FindCourse extends Page{

	public static HashMap<String, String> FindACourse(WebDriver driver,
			Browsers browser, HashMap<String, String> appDetails,
			int current_scenario) throws Exception {
		DataSheet dtSht = new DataSheet("Personal Information");
		HashMap<String, String> elementRepository = dtSht.getXPathRepository("Find a course");
		appDetails = Helper.populateTextField(driver, dtSht, elementRepository,
				appDetails, "Search by course name or code", current_scenario);
		Helper.buttonClick(driver, appDetails, "Find");
		return appDetails;
	}
}

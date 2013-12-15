package eAdmission;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

public class PersonalInformationPage {

	public static HashMap<String, String> completePage(WebDriver driver,
			HashMap<String, String> appDetails, String mandatoryOnly)
			throws Exception {

		Page PIP = new Page(driver, "Personal Information", appDetails);
		int current_scenario = PIP.scenario;
		if (mandatoryOnly.equals("No")) {
			PIP.AppDetails = Helper.setValue(PIP, "Middle Name");
			PIP.AppDetails = Helper.setValue(PIP, "Home phone");
			PIP.AppDetails = Helper.setValue(PIP, "Mobile phone");
		}

		PIP.AppDetails = Helper.setValue(PIP, "Title");
		PIP.AppDetails = Helper.setValue(PIP, "DOB");
		PIP.AppDetails = Helper.setValue(PIP, "Gender");
		PIP.AppDetails = Helper.setValue(PIP, "Street");
		PIP.AppDetails = Helper.setValue(PIP, "Suburb");
		PIP.AppDetails = Helper.setValue(PIP, "State");
		PIP.AppDetails = Helper.setValue(PIP, "Postcode");

		// Residential Address
		if (PIP.dtSht.getValue(
				"Is your residential address the same as your postal address?",
				current_scenario).isEmpty() == false) {
			PIP.AppDetails = Helper
					.setValue(PIP,
							"Is your residential address the same as your postal address?");
			if (PIP.dtSht
					.getValue(
							"Is your residential address the same as your postal address?",
							current_scenario).equals("No")) {
				PIP.AppDetails = Helper.setValue(PIP, "resStreet");
				PIP.AppDetails = Helper.setValue(PIP, "resSuburb");
				PIP.AppDetails = Helper.setValue(PIP, "resPostcode");
				PIP.AppDetails = Helper.setValue(PIP, "resState");
				PIP.AppDetails = Helper
						.setValue(
								PIP,
								"Is this your permanent address, or your temporary semester address while studying?");
			}
		}

		Helper.Continue(driver, PIP.AppDetails);
		Helper.clickTab(driver, "Personal", PIP.AppDetails);
		Helper.getAllValuesfromPage(driver, "Personal Information Page",
				PIP.AppDetails);
		Helper.Continue(driver, PIP.AppDetails);
		return PIP.AppDetails;
	}

	public static HashMap<String, String> SaveAndExitThenDelete(
			WebDriver driver, HashMap<String, String> appDetails)
			throws Exception {

		Page PIP = new Page(driver, "Personal Information", appDetails);
		
		int current_scenario = PIP.scenario;
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Title");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Middle Name");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "DOB");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Gender");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Home phone");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Mobile phone");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Street");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Suburb");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "State");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");
		PIP.AppDetails = Helper.setValue(PIP, "Postcode");
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		Helper.buttonClick(driver, PIP.AppDetails, "Continue");

		// Residential Address
		if (PIP.dtSht.getValue(
				"Is your residential address the same as your postal address?",
				current_scenario).isEmpty() == false) {
			PIP.AppDetails = Helper
					.setValue(PIP,
							"Is your residential address the same as your postal address?");
			Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
			Helper.buttonClick(driver, PIP.AppDetails, "Continue");
			if (PIP.dtSht
					.getValue(
							"Is your residential address the same as your postal address?",
							current_scenario).equals("No")) {
				PIP.AppDetails = Helper.setValue(PIP, "resStreet");
				Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
				Helper.buttonClick(driver, PIP.AppDetails, "Continue");
				PIP.AppDetails = Helper.setValue(PIP, "resSuburb");
				Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
				Helper.buttonClick(driver, PIP.AppDetails, "Continue");
				PIP.AppDetails = Helper.setValue(PIP, "resPostcode");
				Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
				Helper.buttonClick(driver, PIP.AppDetails, "Continue");
				PIP.AppDetails = Helper.setValue(PIP, "resState");
				Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
				Helper.buttonClick(driver, PIP.AppDetails, "Continue");
				PIP.AppDetails = Helper
						.setValue(
								PIP,
								"Is this your permanent address, or your temporary semester address while studying?");
				Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
				Helper.buttonClick(driver, PIP.AppDetails, "Continue");
			}
		}
		Helper.buttonClick(driver, PIP.AppDetails, "Save & Exit");
		return PIP.AppDetails;
	}
}
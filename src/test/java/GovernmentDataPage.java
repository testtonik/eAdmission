package eAdmission;
import java.io.IOException;
import java.util.HashMap;

import jxl.read.biff.BiffException;

import org.openqa.selenium.WebDriver;

public class GovernmentDataPage extends Page {

	public static HashMap<String, String> completePage(WebDriver driver,
			HashMap<String, String> appDetail, String mandatoryOnly)
			throws BiffException, IOException, InterruptedException,
			AdmissionsExeption {

		Page GDP = new Page(driver, "Government data", appDetail);
		int current_scenario = GDP.scenario;

		if (mandatoryOnly.equals("No")) {
			if (GDP.dtSht.getValue("Parent / Guardian 2:Gender",
					current_scenario).isEmpty() == false) {
				GDP.AppDetails = Helper.setValue(GDP,
						"Parent / Guardian 2:Gender");
			}

			GDP.AppDetails = Helper.setValue(GDP,
					"In what country is your permanent home address?");
			if (GDP.dtSht.getValue(
					"In what country is your permanent home address?",
					current_scenario).equals("Overseas")) {
				GDP.AppDetails = Helper
						.setValue(GDP,
								"In what country is your permanent home address?:Name of Country");
			} else {
				GDP.AppDetails = Helper
						.setValue(GDP,
								"In what country is your permanent home address?:Postcode");
			}

			// In what country is your residence during the year?
			GDP.AppDetails = Helper.setValue(GDP,
					"In what country is your residence during the year?");
			if (GDP.dtSht.getValue(
					"In what country is your residence during the year?",
					current_scenario).equals("Overseas")) {
				GDP.AppDetails = Helper
						.setValue(GDP,
								"In what country is your residence during the year?:Name of Country");
			} else {
				GDP.AppDetails = Helper
						.setValue(GDP,
								"In what country is your residence during the year?:Postcode");
			}

			if (GDP.dtSht.getValue(
					"Do you speak a language other than English at home?",
					current_scenario).equals("Yes")) {
				GDP.AppDetails = Helper.setValue(GDP, "Language");
			}

			GDP.AppDetails = Helper.setValue(GDP,
					"Parent / Guardian 1:Highest level of education");
			if (GDP.dtSht.getValue(
					"Parent / Guardian 2:Highest level of education",
					current_scenario).isEmpty() == false) {
				GDP.AppDetails = Helper.setValue(GDP,
						"Parent / Guardian 2:Highest level of education");
			}

			GDP.AppDetails = Helper.setValue(GDP,
					"Do you speak a language other than English at home?");
			GDP.AppDetails = Helper.setValue(GDP, "Parent / Guardian 1:Gender");
			GDP.AppDetails = Helper
					.setValue(
							GDP,
							"What is the highest level of secondary education you have attempted or completed?");
			GDP.AppDetails = Helper
					.setValue(
							GDP,
							"What was your highest level of educational participation prior to this course?");
			GDP.AppDetails = Helper.setValue(GDP, "Country of birth");
			if (GDP.dtSht.getValue("Country of birth", current_scenario) == "Australia") {
				// DO NOTHING
			} else {
				GDP.AppDetails = Helper.setValue(GDP, "Year of arrival");
			}

			if (GDP.dtSht.getValue(
					"Do you speak a language other than English at home?",
					current_scenario).equals("Yes")) {
				GDP.AppDetails = Helper.setValue(GDP, "Language");
			}
			GDP.AppDetails = Helper.setValue(GDP, "Year");
			GDP.AppDetails = Helper.setValue(GDP, "Name of school");
			GDP.AppDetails = Helper.setValue(GDP, "State");
			GDP.AppDetails = Helper
					.setValue(
							GDP,
							"What is the highest level of secondary education you have attempted or completed?:Postcode");
			GDP.AppDetails = Helper.setValue(GDP, "Country");
		}

		GDP.AppDetails = Helper.setValue(GDP,
				"What is your Citizenship/Residence status?");

		if (GDP.dtSht.getValue("What is your Citizenship/Residence status?",
				current_scenario).equals(
				"Permanent Resident (Other Than Humanitarian)")) {
			GDP.AppDetails = Helper.setValue(GDP,
					"Permanent Resident Date Granted");
			GDP.AppDetails = Helper.setValue(GDP,
					"Permanent Resident Expiry Date");
			GDP.AppDetails = Helper
					.setValue(
							GDP,
							"If you have a Permanent Residence status, which statement best describes you circumstance?");
		}

		if (GDP.dtSht.getValue("What is your Citizenship/Residence status?",
				current_scenario).equals("Permanent Humanitarian Visa Holder")) {
			GDP.AppDetails = Helper.setValue(GDP,
					"Permanent Humanitarian Visa Holder Date Granted");
			GDP.AppDetails = Helper.setValue(GDP,
					"Permanent Humanitarian Visa Holder Expiry");
		}

		if (GDP.dtSht.getValue("What is your Citizenship/Residence status?",
				current_scenario).equals("Temporary Visa Holder (Tafe Only)")) {
			GDP.AppDetails = Helper.setValue(GDP,
					"Temporary Visa Holder Date Granted");
			GDP.AppDetails = Helper.setValue(GDP,
					"Temporary Visa Holder Expiry Date");
		}

		// In what country is your permanent home address?

		GDP.AppDetails = Helper.setValue(GDP,
				"Year completed (or last year attempted)");
		GDP.AppDetails = Helper.setValue(GDP,
				"Are you of Aboriginal or Torres Strait Islander origin?");
		GDP.AppDetails = Helper.setValue(GDP, "Was the final year completed?");
		if (GDP.dtSht.getValue("Was the final year completed?",
				current_scenario).equals("Yes")) {
			GDP.AppDetails = Helper.setValue(GDP, "ATAR (or equiv)");
		}

		Helper.Continue(driver, GDP.AppDetails);
		return GDP.AppDetails;
	}

}

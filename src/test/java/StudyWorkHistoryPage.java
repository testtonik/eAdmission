package eAdmission;
import java.awt.AWTException;
import java.io.IOException;

import static org.junit.Assert.*; 

import java.util.HashMap;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import eAdmission.TBase.Browsers;

import java.util.List;

public class StudyWorkHistoryPage extends Page {

	public static HashMap<String, String> completeStudyHistory(
			WebDriver driver, Browsers browser,
			HashMap<String, String> appDetails) throws BiffException,
			IOException, InterruptedException, AWTException, AdmissionsExeption {

		LayoutChecker.verifyCommonElements(driver);
		DataSheet dtSht = new DataSheet("Study & work history");
		HashMap<String, String> elementRepository = dtSht
				.getXPathRepository("Study & work history");

		for (int i = 1; i < 4; i++) {
			driver.findElement(By.id("sh-new-button")).click();
			appDetails = Helper.populateTextField(driver, dtSht,
					elementRepository, appDetails,
					"Course or Qualification title", i);
			appDetails = Helper.populateTextField(driver, dtSht,
					elementRepository, appDetails, "Major area of study", i);
			appDetails = Helper.selectRadioButton(driver, dtSht,
					elementRepository, appDetails,
					"Did you complete the qualification?", i);
			appDetails = Helper.selectDropDown(driver, dtSht,
					elementRepository, appDetails, "Last year of study", i);
			appDetails = Helper.selectRadioButton(driver, dtSht,
					elementRepository, appDetails,
					"Did you undertake this area of study in Australia?", i);
			if (dtSht.getValue(
					"Did you undertake this area of study in Australia?", i)
					.equals("Yes")) {
				appDetails = Helper.populatesSelectTextField(driver, dtSht,
						elementRepository, appDetails,
						"Australian Institution", i);
				appDetails.put("State or Country", "Australia");
				appDetails.put("Institution name",
						dtSht.getValue("Australian Institution", i));
				appDetails = Helper.selectDropDown(driver, dtSht,
						elementRepository, appDetails,
						"In which state did you study?", i);
			} else {
				appDetails = Helper.populateTextField(driver, dtSht,
						elementRepository, appDetails, "Institution name", i);
				appDetails.put("Institution name",
						dtSht.getValue("Institution name", i));
				appDetails = Helper.populateTextField(driver, dtSht,
						elementRepository, appDetails, "State or Country", i);
				appDetails.put("State or Country",
						dtSht.getValue("State or Country", i));
			}
			driver.findElement(By.xpath("//span[text()='Save']")).click();
			
			appDetails.put("Completed",
					dtSht.getValue("Did you complete the qualification?", i));
			Helper.checkHistoryRecord(driver, appDetails, "mng_study_history",
					i);
		}
		return appDetails;
	}

	public static HashMap<String, String> completeWorkHistory(WebDriver driver,
			Browsers browser, HashMap<String, String> appDetails)
			throws BiffException, IOException, InterruptedException,
			AdmissionsExeption {
		DataSheet dtSht = new DataSheet("Study & work history");
		HashMap<String, String> elementRepository = dtSht
				.getXPathRepository("Study & work history");
		
		for (int i = 1; i < 2; i++) {
			driver.findElement(By.id("wh-new-button")).click();
			appDetails = Helper.populateTextField(driver, dtSht,
					elementRepository, appDetails, "Position", i);
			appDetails = Helper.populateTextField(driver, dtSht,
					elementRepository, appDetails, "Employer", i);
			appDetails = Helper.populateTextField(driver, dtSht,
					elementRepository, appDetails, "Industry", i);
			appDetails = Helper.populateTextField(driver, dtSht,
					elementRepository, appDetails, "Start date", i);
			appDetails = Helper.populateTextField(driver, dtSht,
					elementRepository, appDetails, "End date", i);
			appDetails = Helper.selectRadioButton(driver, dtSht,
					elementRepository, appDetails, "Employment type", i);
			appDetails = Helper.selectRadioButton(driver, dtSht,
					elementRepository, appDetails, "Remuneration status", i);
			driver.findElement(
					By.xpath("//div[@aria-describedby='dialog-new-wh-form']//span[text()='Save']/.."))
					.click();
		}

		return appDetails;
	}

	public static HashMap<String, String> completePage(WebDriver driver,
			Browsers browser, HashMap<String, String> appDetails,
			int current_scenario, String mandatoryOnly) throws BiffException,
			IOException, InterruptedException, AWTException, AdmissionsExeption {

		DataSheet dtSht = new DataSheet("Study & work history");
		HashMap<String, String> elementRepository = dtSht
				.getXPathRepository("Study & work history");

		// Shortcut? Only mandatory fields?
		if (mandatoryOnly.equals("No")) {
			appDetails = StudyWorkHistoryPage.completeStudyHistory(driver,
					browser, appDetails);
			Helper.Continue(driver, appDetails);
			Helper.clickTab(driver, "Study &", appDetails);
			assertEquals(String.valueOf(3), String.valueOf(Helper
					.countRecordsTable(driver, "mng_study_history") - 1));
			// Delete Study record
			driver.findElement(By.id("delete-sh-button")).click();
			Thread.sleep(1000);
			Helper.highlightElement(
					driver,
					driver.findElement(By
							.xpath("//div[@aria-describedby='dialog-delete-sh-form']//span[text()='Delete']/..")));
			driver.findElement(
					By.xpath("//div[@aria-describedby='dialog-delete-sh-form']//span[text()='Delete']/.."))
					.click();
			// One less record
			assertEquals(String.valueOf(2), String.valueOf(Helper
					.countRecordsTable(driver, "mng_study_history") - 1));

			appDetails = StudyWorkHistoryPage.completeWorkHistory(driver,
					browser, appDetails);
			// Upload Support documents
			List<WebElement> uploadFileButton = driver.findElements(By
					.id("seh_supporting_docs"));
			Helper.uploadFile(driver, uploadFileButton.get(0));
			// driver.findElement(By.xpath("//input[@value='Upload']")).click();
			Helper.uploadFile(driver, uploadFileButton.get(0));
			Helper.buttonClick(driver, appDetails, "Upload");
		}
		appDetails = Helper.selectRadioButton(driver, dtSht, elementRepository,
				appDetails, "Advanced standing", current_scenario);

		if (dtSht.getValue("Advanced standing", current_scenario).equals("Yes")) {
			WebElement uploadAdvanceStandingFile = driver.findElement(By
					.id("seh_advance_standing"));
			Helper.uploadFile(driver, uploadAdvanceStandingFile);
			Helper.buttonClick(driver, appDetails, "Upload");
		}
		Helper.Continue(driver, appDetails);
		return appDetails;
	}

}

package eAdmission;
import java.util.HashMap;
import java.util.Random;

import org.openqa.selenium.WebDriver;

import eAdmission.TBase.Browsers;
public class CourseDetailsPage extends Page {

	public static HashMap<String, String> completePage(WebDriver driver,
			Browsers browser, HashMap<String, String> appDetails)
			throws Exception {

		Page CDP = new Page(driver, "Course Details", appDetails);
		int current_scenario = CDP.scenario;

		HashMap<String, String> CourseInfo = null;

		CDP.AppDetails = Helper.setValue(CDP, "isVTAC");

		if (CDP.dtSht.getValue("isVTAC", current_scenario).equals("Yes")) {
			CDP.AppDetails = Helper.setValue(CDP, "VTAC ID");
		}

		CDP.AppDetails = Helper.setValue(CDP,
				"Do you have a Victoria University student ID number?");

		String delims = ",";
		String AttendanceModes = CourseInfo.get("AttendanceModes");
		String[] AttendanceModestokens = AttendanceModes.split(delims);
		
		if (AttendanceModestokens.length > 0) {
			String value = AttendanceModestokens[new Random()
					.nextInt(AttendanceModestokens.length)];
			CDP.AppDetails = Helper.setValue(CDP, "Attendance mode", value);
		}

		String AttendanceTypes = CourseInfo.get("AttendanceTypes");
		String[] AttendanceTypestokens = AttendanceTypes.split(delims);
		
		if (AttendanceTypestokens.length > 0) {
			String value = AttendanceTypestokens[new Random()
					.nextInt(AttendanceTypestokens.length)];
			CDP.AppDetails = Helper
					.setValue(CDP, "Preferred study mode", value);
		}

		String Locations = CourseInfo.get("Locations");
		String[] Locationstokens = Locations.split(delims);
		if (Locationstokens.length > 0) {
			String value = Locationstokens[new Random()
					.nextInt(Locationstokens.length)];
			CDP.AppDetails = Helper.setValue(CDP, "Location", value);
		}

		if (CourseInfo.get("FEE").equals("Yes")) {
			CDP.AppDetails = Helper
					.setValue(
							CDP,
							"Do you wish to be considered for a full-fee place should a Commonwealth Supported Place not be available?",
							"Yes");
		}

		if (CDP.dtSht.getValue(
				"Do you have a Victoria University student ID number?",
				current_scenario).equals("Yes")) {
			CDP.AppDetails = Helper
					.setValue(
							CDP,
							"Are you applying to transfer from a current Victoria University course into this course?");
		}

		Helper.Continue(driver, CDP.AppDetails);
		Helper.clickTab(driver, "Course", CDP.AppDetails);
		Helper.getAllValuesfromPage(driver, "Course Details", CDP.AppDetails);
		Helper.Continue(driver, CDP.AppDetails);
		return CDP.AppDetails;
	}
}

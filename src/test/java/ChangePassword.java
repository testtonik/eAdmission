package eAdmission;
import java.io.IOException;
import java.util.HashMap;

import jxl.read.biff.BiffException;

import org.openqa.selenium.WebDriver;

public class ChangePassword extends Page {

	public ChangePassword() {
		// TODO Auto-generated constructor stub
	}

	public static void changePassword(WebDriver driver,
			HashMap<String, String> appDetails) throws BiffException,
			IOException, InterruptedException, AdmissionsExeption {
		Page GPP = new Page(driver, "Change password", appDetails);
		GPP.AppDetails = Helper.setValue(GPP, "Old password");
		GPP.AppDetails = Helper.setValue(GPP, "Enter new password");
		GPP.AppDetails = Helper.setValue(GPP, "Confirm new password");
		Helper.buttonClick(driver, appDetails, "Submit");
	}
}

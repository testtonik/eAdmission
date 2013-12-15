package eAdmission;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReviewPage {

	public static HashMap<String, String> completePage(WebDriver driver,
			HashMap<String, String> appDetails) throws NumberFormatException,
			IOException {
		String file_name = appDetails.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		System.out
				.println("**********************************************************");
		System.out.println("Given I am on 'Review page'");
		System.out
				.println("**********************************************************");
		writer.println("**********************************************************");
		writer.println("Given I am on 'Review page'");
		writer.println("**********************************************************");
		writer.close();
		driver.findElement(By.id("review_declaration")).click();
		Helper.buttonClick(driver, appDetails, "Submit my application");
		// Get reference id from the Manage Application page
		String referenceId = driver.findElement(By.id("ref-num")).getText();
		writer.println("REFERENCE ID: " + referenceId);
		appDetails.put("Reference Id", referenceId);
		return appDetails;
	}

}

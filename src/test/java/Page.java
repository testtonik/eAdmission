package eAdmission;

import java.io.IOException;
import java.util.HashMap;

import jxl.read.biff.BiffException;

import org.openqa.selenium.WebDriver;

public class Page {
	public HashMap<String, String> ElementRepository;
	public HashMap<String, String> AppDetails;
	public WebDriver Driver;
	public DataSheet dtSht;
	public HashMap<String, String> ElementTypes;
	int scenario;

	public Page(WebDriver ext_driver, String page,
			HashMap<String, String> appDetails) throws BiffException,
			IOException {
		dtSht = new DataSheet(page);
		ElementRepository = dtSht.getXPathRepository(page);
		ElementTypes = dtSht.getElementType(page);
		AppDetails = appDetails;
		Driver = ext_driver;
	//	scenario = Integer.valueOf(appDetails.get("scenario"));
	}

	public void fillInput (String value, String inputName) throws BiffException, IOException, InterruptedException{
		Helper.populateTxtFieldwithValue (Driver, value, ElementRepository, null,inputName);
	}
	
	public void clickButton (String value) throws IOException{
		Helper.buttonClick(Driver, AppDetails, value);
	}
	
	
	public WebDriver getDriver() {
		return this.Driver;
	}

	public DataSheet getSheet() {
		return this.dtSht;
	}

	public HashMap<String, String> getAppDetails() {
		return this.AppDetails;
	}

	public HashMap<String, String> getElementTypes() {
		return this.ElementTypes;
	}

	public HashMap<String, String> getRepository() {
		return this.ElementRepository;
	}

	public Page() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package eAdmission;
import static org.junit.Assert.*; 

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import jxl.read.biff.BiffException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Helper {

	public static String getRandomState() {
		String[] stateArray = { "VIC", "NSW", "QLD", "TAS", "WA", "NT", "SA",
				"ACT" };
		int randomState = (int) (Math.random() * 6);
		return stateArray[randomState];
	}

	public static String generateString(int len) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	public static WebElement getAssociatedInputfield(WebDriver driver,
			String label) {
		int r = 0;
		int key_current = -10000;

		List<WebElement> allInputElements = driver.findElements(By
				.xpath("//input"));
		List<WebElement> allLabelElements = driver.findElements(By
				.xpath("//label[contains(text(),'" + label
						+ "')] | //span[contains(text(),'" + label + "')]"));

		HashMap<Integer, WebElement> allElementsHM = new HashMap<Integer, WebElement>();
		Iterator<WebElement> itr_lbl = allLabelElements.iterator();

		// Clean the list from fields above label
		while (itr_lbl.hasNext()) {
			WebElement element = itr_lbl.next();
			if (element.getLocation().x == 0 || element.getLocation().y == 0) {
				allLabelElements.remove(element);
			}
		}
		Iterator<WebElement> itr_inp = allInputElements.iterator();
		while (itr_inp.hasNext()) {
			WebElement element = itr_inp.next();
			if (element.getLocation().x == 0 || element.getLocation().y == 0) {
			} else {
				int y_label = allLabelElements.get(0).getLocation().y;
				int y_input = element.getLocation().y;
				r = y_label - y_input;
				if (r > 0) {
					// do nothing
				} else {
					allElementsHM.put(r, element);
				}
			}
		}
		// iterating over keys only
		for (Integer key : allElementsHM.keySet()) {

			if (key > key_current) {
				key_current = key;
			}
		}
		return allElementsHM.get(key_current);
	}

	public static void highlightElement(WebDriver driver, WebElement element) {
		for (int i = 0; i < 12; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: yellow; border: 2px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "");
		}
	}

	public static HashMap<String, String> setValue(Page pageObj,
			String fieldName) throws BiffException, IOException,
			InterruptedException, AdmissionsExeption {
		WebDriver driver = pageObj.getDriver();
		HashMap<String, String> elementTypes = pageObj.getElementTypes();
		HashMap<String, String> elementRepository = pageObj.getRepository();
		HashMap<String, String> gdAppDetails = pageObj.getAppDetails();
		DataSheet dtSht = pageObj.getSheet();
		int scenario = pageObj.scenario;
		String el_type = elementTypes.get(fieldName.trim());
		if (el_type.equals("RADIO")) {
			gdAppDetails = Helper.selectRadioButtonNew(driver, dtSht,
					elementRepository, gdAppDetails, fieldName, scenario);
		} else {
			if (el_type.equals("TEXT")) {
				gdAppDetails = Helper.populateTextField(driver, dtSht,
						elementRepository, gdAppDetails, fieldName, scenario);
			} else {
				if (el_type.equals("TEXT")) {
					gdAppDetails = Helper.populateTextField(driver, dtSht,
							elementRepository, gdAppDetails, fieldName,
							scenario);
				} else {
					if (el_type.equals("SELECT")) {
						gdAppDetails = Helper.selectDropDown(driver, dtSht,
								elementRepository, gdAppDetails, fieldName,
								scenario);
					} else {
						if (el_type.equals("TEXT_SELECT")) {
							gdAppDetails = Helper.populatesSelectTextField(
									driver, dtSht, elementRepository,
									gdAppDetails, fieldName, scenario);
						} else {
							System.out.println("ELEMENT TYPE UNKNOWN");
						}
					}
				}
			}
		}
		return gdAppDetails;
	}

	public static HashMap<String, String> setValue(Page pageObj,
			String fieldName, String value) throws BiffException, IOException,
			InterruptedException, AdmissionsExeption {
		WebDriver driver = pageObj.getDriver();
		HashMap<String, String> elementTypes = pageObj.getElementTypes();
		HashMap<String, String> elementRepository = pageObj.getRepository();
		HashMap<String, String> AD = pageObj.getAppDetails();
		int scenario = pageObj.scenario;
		String el_type = elementTypes.get(fieldName);
		if (el_type.equals("RADIO")) {
			AD = Helper.selectRadioButton2(driver, value,
					elementRepository, AD, fieldName, scenario);
			/*
			 * } else { if (el_type.equals("TEXT")){ gdAppDetails =
			 * Helper.populateTextField(driver, value, elementRepository,
			 * gdAppDetails, fieldName, scenario); } else { if
			 * (el_type.equals("TEXT")){ gdAppDetails =
			 * Helper.populateTextField(driver, value, elementRepository,
			 * gdAppDetails, fieldName, scenario); } else { if
			 * (el_type.equals("SELECT")){ gdAppDetails = Helper.selectDropDown
			 * (driver, value, elementRepository, gdAppDetails, fieldName,
			 * scenario); } else { if (el_type.equals("TEXT_SELECT")){
			 * gdAppDetails = Helper.populatesSelectTextField (driver, value,
			 * elementRepository, gdAppDetails, fieldName, scenario); } else {
			 * System.out.println("ELEMENT TYPE UNKNOWN"); } } } }
			 */
		}
		return AD;
	}

	// TEXT FIELD CONTROL
	public static HashMap<String, String> populateTextField(WebDriver driver,
			DataSheet dtSht, HashMap<String, String> elementsHM,
			HashMap<String, String> appDetails, String Name, int scenario)
			throws BiffException, IOException, InterruptedException,
			AdmissionsExeption {
		String file_name = appDetails.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		String value = dtSht.getValue(Name, scenario);
		if (driver.findElement(By.xpath(elementsHM.get(Name))).isDisplayed()) {
			driver.findElement(By.xpath(elementsHM.get(Name))).clear();
			// Helper.highlightElement(driver,
			// driver.findElement(By.xpath(elementsHM.get(Name))));
			driver.findElement(By.xpath(elementsHM.get(Name))).sendKeys(value);
			driver.findElement(By.xpath(elementsHM.get(Name))).sendKeys(
					Keys.TAB);
			Thread.sleep(400);
			System.out.println("And I set '" + Name + "' to: '" + value + "'");
			System.out.println("-----------------------------");
			writer.println("And I set '" + Name + "' to: '" + value + "'");
			writer.println("-----------------------------");
		} else {
			System.out.println(Name + " doesn't set to: " + value
					+ " as it is not displayed!");
			writer.println(Name + " doesn't set to: " + value
					+ " as it is not displayed!");
			appDetails.put("TEST_STATUS", "FAILED");
		}
		appDetails.put(Name, value);
		writer.close();
		return appDetails;
	}

	// TEXT FIELD CONTROL
	public static HashMap<String, String> populateTxtFieldwithValue(
			WebDriver driver, String text, HashMap<String, String> elementsHM,
			HashMap<String, String> pageHM, String Name) throws BiffException,
			IOException, InterruptedException {

		if (driver.findElement(By.xpath(elementsHM.get(Name))).isEnabled()) {
			driver.findElement(By.xpath(elementsHM.get(Name))).clear();
			driver.findElement(By.xpath(elementsHM.get(Name))).sendKeys(text);
			driver.findElement(By.xpath(elementsHM.get(Name))).sendKeys(
					Keys.TAB);
			System.out.println("And I set '" + Name + "' to: '" + text + "'");
			System.out.println("-----------------------------");
		} else {
			if (pageHM != null) {
				System.out.println(Name + " doesn't set to: " + text
						+ " as it is not displayed!");
				pageHM.put("TEST_STATUS", "FAILED");
			}
		}
		if (pageHM != null) {
			pageHM.put(Name, text);
		}
		return pageHM;
	}

	// TEXT SELECT FIELD CONTROL (AS PER AUSTRALIAN UNIVERSITIES)
	public static HashMap<String, String> populatesSelectTextField(
			WebDriver driver, DataSheet dtSht,
			HashMap<String, String> elementsHM, HashMap<String, String> pageHM,
			String Name, int scenario) throws BiffException, IOException,
			InterruptedException {
		String value = dtSht.getValue(Name, scenario);
		String file_name = pageHM.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		if (driver.findElement(By.xpath(elementsHM.get(Name)))
				.getAttribute("value").equals(value)) {
			// DO NOTHING
		} else {
			if (driver.findElement(By.xpath(elementsHM.get(Name)))
					.isDisplayed()) {
				driver.findElement(By.xpath(elementsHM.get(Name))).clear();
				driver.findElement(By.xpath(elementsHM.get(Name))).sendKeys(
						value);
				Thread.sleep(2500);
				driver.findElement(By.linkText(value)).click();
				driver.findElement(By.xpath(elementsHM.get(Name))).sendKeys(
						Keys.TAB);
				System.out.println("And I set '" + Name + " to: " + value);
				System.out.println("-----------------------------");
				writer.println("And I set '" + Name + " to: " + value);
				writer.println("-----------------------------");
			} else {
				System.out.println(Name + " doesn't set to: " + value
						+ " as it is not displayed!");
				writer.println(Name + " doesn't set to: " + value
						+ " as it is not displayed!");
			}
		}

		pageHM.put(Name, value);
		writer.close();
		return pageHM;
	}

	// DROP DOWN CONTROL
	public static HashMap<String, String> selectDropDown(WebDriver driver,
			DataSheet dtSht, HashMap<String, String> elementsHM,
			HashMap<String, String> pageHM, String Name, int scenario)
			throws BiffException, IOException {
		String id = elementsHM.get(Name);
		boolean found = false;
		String file_name = pageHM.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		String element_value = dtSht.getValue(Name, scenario);
		List<WebElement> select_elements = driver.findElements(By
				.tagName("select"));
		for (int i = 0; i < select_elements.size(); i++) {
			// System.out.println(select_elements.get(i).getAttribute("id"));
			if (select_elements.get(i).getAttribute("id").equals(id)) {
				found = true;
				Select select_element = new Select(select_elements.get(i));
				if (element_value.isEmpty() == false) {
					select_element.selectByVisibleText(element_value);
					pageHM.put(Name, element_value);
					System.out.println("And I set '" + Name + "' to: "
							+ element_value);
					System.out.println("-----------------------------");
					writer.println("And I set '" + Name + "' to: "
							+ element_value);
					writer.println("-----------------------------");
				} else {
					System.out.println("VALUE IS BLANK!");
				}
			}
		}
		if (found != true) {
			System.out.println("Select element '" + Name + "' with '" + id
					+ "' is not found");
		}
		writer.close();
		return pageHM;
	}

	// RADIO BUTTON CONTROL
	public static HashMap<String, String> selectRadioButton(WebDriver driver,
			DataSheet dtSht, HashMap<String, String> elementsHM,
			HashMap<String, String> pageHM, String Name, int scenario)
			throws BiffException, IOException {
		boolean found = false;
		String xPath = elementsHM.get(Name);
		List<WebElement> radio_buttons = driver.findElements(By.xpath(xPath));
		String button_value = dtSht.getValue(Name, scenario);
		String file_name = pageHM.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		if (button_value.isEmpty() == false && button_value != "") {
			for (int i = 0; i < radio_buttons.size(); i++) {
				if (radio_buttons.get(i).getAttribute("Value")
						.equals(button_value)) {
					// Helper.highlightElement(driver, radio_buttons.get(i));
					radio_buttons.get(i).click();
					pageHM.put(Name, button_value);
					System.out.println("And I set '" + Name + "' to: "
							+ button_value);
					System.out.println("-----------------------------");
					writer.println("And I set '" + Name + "' to: "
							+ button_value);
					writer.println("-----------------------------");
					found = true;
				}
			}
		}
		if (found != true) {
			System.out.println("RADIO BUTTON " + Name + ", WITH VALUE="
					+ button_value + " NOT FOUND");
		}
		writer.close();
		return pageHM;
	}

	// Select radio button by label
	public static HashMap<String, String> selectRadioBtnByLabel(
			WebDriver driver, DataSheet dtSht,
			HashMap<String, String> pageHM, String Name, int scenario)
			throws BiffException, IOException {
		String button_value = dtSht.getValue(Name, scenario);
		// WebElement button_group =
		// driver.findElement(By.xpath("//span[contains(text(), '"+Name+"')]/.."));
		// Helper.highlightElement(driver, button_group);
		List<WebElement> radio_button = driver.findElements(By
				.xpath("//span[contains(text(), '" + Name
						+ "')]/..//following::label[text()='" + button_value
						+ "']"));
		// System.out.println(radio_button.size());
		// Helper.highlightElement(driver, radio_button.get(0));
		String file_name = pageHM.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		if (button_value.isEmpty() == false && button_value != "") {
			// Helper.highlightElement(driver, radio_button.get(0));
			radio_button.get(0).click();
			pageHM.put(Name, button_value);
			System.out.println("'" + Name + "' set to: '" + button_value + "'");
			System.out.println("-----------------------------");
			writer.println("'" + Name + "' set to: '" + button_value + "'");
			writer.println("-----------------------------");
		}
		writer.close();
		return pageHM;
	}

	public static HashMap<String, String> selectRadioButtonNew(
			WebDriver driver, DataSheet dtSht,
			HashMap<String, String> elementRepository,
			HashMap<String, String> pageHM, String Name, int scenario)
			throws BiffException, IOException {
		String button_value = dtSht.getValue(Name, scenario);
		// WebElement button_group =
		// driver.findElement(By.xpath("//span[contains(text(), '"+Name+"')]/.."));
		// Helper.highlightElement(driver, button_group);
		List<WebElement> radio_button = driver.findElements(By
				.xpath("//span[contains(text(), '" + Name
						+ "')]/..//following::label[text()='" + button_value
						+ "']"));
		// System.out.println(radio_button.size());
		String file_name = pageHM.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		// Helper.highlightElement(driver, radio_button.get(0));
		if (radio_button.size() == 0) {
			pageHM = Helper.selectRadioButton(driver, dtSht, elementRepository,
					pageHM, Name, scenario);
		} else {
			if (button_value.isEmpty() == false && button_value != "") {
				// Helper.highlightElement(driver, radio_button.get(0));
				radio_button.get(0).click();
				pageHM.put(Name, button_value);
				System.out.println("'" + Name + "' set to: '" + button_value
						+ "'");
				System.out.println("-----------------------------");
				writer.println("'" + Name + "' set to: '" + button_value + "'");
				writer.println("-----------------------------");

			}
		}
		writer.close();
		return pageHM;
	}

	public static HashMap<String, String> selectRadioButton2(WebDriver driver,
			String button_value, HashMap<String, String> elementsHM,
			HashMap<String, String> pageHM, String Name, int scenario)
			throws BiffException, IOException {
		boolean found = false;
		String file_name = pageHM.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		List<WebElement> radio_buttons = driver.findElements(By
				.xpath(elementsHM.get(Name)));
		if (button_value.isEmpty() == false && button_value != "") {
			for (int i = 0; i < radio_buttons.size(); i++) {
				if (radio_buttons.get(i).getAttribute("Value")
						.equals(button_value)) {
					Helper.highlightElement(driver, radio_buttons.get(i));
					radio_buttons.get(i).click();
					String id = radio_buttons.get(i).getAttribute("id");
					String value = driver.findElement(
							By.xpath("//label[@for='" + id + "']")).getText();
					pageHM.put(Name, value);
					System.out
							.println("'" + Name + "' set to: " + button_value);
					System.out.println("-----------------------------");
					writer.println("'" + Name + "' set to: " + button_value);
					writer.println("-----------------------------");
					found = true;
					break;
				}
			}
		}
		if (found != true) {
			System.out.println("RADIO BUTTON " + Name + ", WITH VALUE="
					+ button_value + " NOT FOUND");
			writer.println("RADIO BUTTON " + Name + ", WITH VALUE="
					+ button_value + " NOT FOUND");
		}
		writer.close();
		return pageHM;
	}

	public static void getAllValuesfromPage(WebDriver driver, String page,
			HashMap<String, String> beforeHM) throws BiffException, IOException {
		boolean result = false;
		DataSheet dtSht = new DataSheet(page);
		HashMap<String, String> elementRepository = dtSht
				.getXPathRepository(page);
		HashMap<String, String> elementTypes = dtSht.getElementType(page);
		for (String key : elementRepository.keySet()) {
			if (beforeHM.get(key) != null) {
				// System.out.println(elementTypes.get(key));
				String value = Helper.getElementValue(key,
						elementTypes.get(key), driver, elementRepository);
				result = Helper.verifyValue(key, value, beforeHM.get(key));
				if (result != true) {
					beforeHM.put("TEST_STATUS", "FAILED");
				}
			}
		}

	}

	// Get value of an element
	public static String getElementValue(String name, String type,
			WebDriver driver, HashMap<String, String> elementRepository) {
		String value = "";
		String xPath = elementRepository.get(name);
		// System.out.println(name+" , "+xPath);
		try {
			if (type.equals("TEXT") || type.equals("TEXT_SELECT")) {
				WebElement we = driver.findElement(By.xpath(xPath));
				value = we.getAttribute("Value");
			}
			if (type.equals("RADIO")) {
				List<WebElement> radio_buttons = driver.findElements(By
						.xpath(xPath));
				if (radio_buttons.size() > 0) {
					for (int i = 0; i < radio_buttons.size(); i++) {
						if (radio_buttons.get(i).isSelected()) {
							String id = radio_buttons.get(i).getAttribute("id");
							// System.out.println("+++++++++++"+id);
							value = driver.findElement(
									By.xpath("//label[@for='" + id + "']"))
									.getText();
							break;
						}
					}
				} else {
					System.out
							.println("I couldn't found RADIO BUTTON " + xPath);
				}
			}
			if (type.equals("SELECT")) {
				WebElement we = driver.findElement(By.id(xPath));
				Select comboBox = new Select(we);
				value = comboBox.getFirstSelectedOption().getText();
			}
		} catch (NoSuchElementException e) {
			System.out.println("I couldn't found " + name + " field " + xPath
					+ " value=" + value);
			e.printStackTrace();
			return null;
		}
		return value;
	}

	public static boolean verifyValue(String param, String value1, String value2) {
		try {
			assertEquals(value1, value2);
			// System.out.println("PASSED!!! Expexted value of "+param+" is: "+value2+" , actual value is: "+value1);
			return true;
		} catch (Throwable ex) {
			System.out.println("FAIL!!! Expexted value of " + param + " is: "
					+ value2 + " , actual value is: " + value1);
			return false;
		}
	}

	@SuppressWarnings("resource")
	public static void Continue(WebDriver driver,
			HashMap<String, String> appDetails) throws AdmissionsExeption,
			InterruptedException, IOException {
		// if no errors presents
		String file_name = appDetails.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		String error_message = Helper.isErrorExist(driver);
		if (error_message == "") {
			String pageBefore = driver.findElement(
					By.xpath("//a[@class='selected']")).getText();
			driver.findElement(By.xpath("//input[@value='Continue']")).click();
			System.out.println("When I click 'Continue'");
			writer.println("When I click 'Continue'");
			Thread.sleep(1000);
			String pageAfter = driver.findElement(
					By.xpath("//a[@class='selected']")).getText();
			if (pageBefore.equals(pageAfter)) {
				appDetails.put("TEST_STATUS", "FAILED");
				throw new AdmissionsExeption("Unable to navigate from '"
						+ pageBefore + "' page", writer);
			} else {
				System.out.println("Then I moved from '" + pageBefore
						+ "' to page '" + pageAfter);
				writer.println("Then I moved from '" + pageBefore
						+ "' to page '" + pageAfter);
			}
		} else {
			appDetails.put("TEST_STATUS", "FAILED");
			writer.close();
			throw new AdmissionsExeption(error_message, writer);
		}
		writer.close();
	}

	public static String isErrorExist(WebDriver driver) {
		String found = "";
		List<WebElement> we = driver.findElements(By.tagName("div"));
		for (int i = 0; i < we.size(); i++) {
			if (we.get(i).getAttribute("class").equals("relative error")
					|| we.get(i).getAttribute("class")
							.equals("validation-error")) {
				found = we.get(i).getText();
			}
		}
		return found;
	}

	public static boolean isElementExist(WebDriver driver, String xPath) {
		boolean found = true;
		try {
			driver.findElement(By.xpath(xPath));
		} catch (NoSuchElementException e) {
			found = false;
		}
		return found;
	}

	public static void checkHistoryRecord(WebDriver driver,
			HashMap<String, String> PersInfPgAllElementsHM, String table_id,
			int record) {
		WebElement table_element = driver.findElement(By.id(table_id));
		List<WebElement> tr_collection = table_element.findElements(By
				.xpath("id('" + table_id + "')/tbody/tr"));
		List<WebElement> th_collection = tr_collection.get(0).findElements(
				By.xpath("th"));
		// System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
		List<WebElement> td_collection = tr_collection.get(record)
				.findElements(By.xpath("td"));
		// System.out.println("NUMBER OF COLUMNS="+td_collection.size());
		for (int j = 0; j < td_collection.size() - 1; j++) {
			String headerValue = th_collection.get(
					td_collection.indexOf(td_collection.get(j))).getText();
			// System.out.println(headerValue);
			assertEquals(PersInfPgAllElementsHM.get(headerValue).toUpperCase(),
					td_collection.get(j).getText().toUpperCase());
			// System.out.println(td_collection.indexOf(td_collection.get(j)));
			// System.out.println("row # "+record+", col # "+j+
			// "text="+td_collection.get(j).getText());
		}

	}

	// Count how many records has been created for Work or Study history
	public static int countRecordsTable(WebDriver driver, String table) {
		WebElement table_element = driver.findElement(By.id(table));
		List<WebElement> tr_collection = table_element.findElements(By
				.xpath("id('mng_study_history')/tbody/tr"));
		// System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());
		return tr_collection.size();
	}

	public static void uploadFile(WebDriver driver,	WebElement elem) throws InterruptedException, AWTException {
			elem.sendKeys("C:\\xAjx2omKVKU08121195403.pdf");
	}

	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
	}

	public static void buttonClick(WebDriver driver,
			HashMap<String, String> appDetails, String buttonText)
			throws IOException {
		driver.findElement(By.xpath("//input[@value='" + buttonText + "']"))
				.click();
		System.out.println("And I click '" + buttonText + "' button");
		System.out
				.println("----------------------------------------------------------------------------");
	}

	public static void testFooter(HashMap<String, String> appDetails)
			throws IOException {
		String file_name = appDetails.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		System.out
				.println("----------------------------------------------------------------------------");
		System.out.println(appDetails.get("TEST_ID") + " ENDED WITH STATUS: "
				+ appDetails.get("TEST_STATUS"));
		System.out
				.println("----------------------------------------------------------------------------");
		writer.println("----------------------------------------------------------------------------");
		writer.println(appDetails.get("TEST_ID") + " ENDED WITH STATUS: "
				+ appDetails.get("TEST_STATUS"));
		writer.println("----------------------------------------------------------------------------");
		writer.close();
	}

	public static void testHeader(HashMap<String, String> appDetails)
			throws IOException {
		String file_name = appDetails.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		System.out
				.println("----------------------------------------------------------------------------");
		System.out.println(appDetails.get("TEST_ID") + " STARTED");
		System.out
				.println("----------------------------------------------------------------------------");
		writer.println("----------------------------------------------------------------------------");
		writer.println(appDetails.get("TEST_ID") + " STARTED");
		writer.println("----------------------------------------------------------------------------");
		writer.close();
		appDetails.put("TEST_STATUS", "PASSED");
	}

	public static void clickTab(WebDriver driver, String linkText,
			HashMap<String, String> appDetails) throws IOException {
		// ul[@class='apply-navigation']//span[contains(text(), "Personal")]/..
		String file_name = appDetails.get("file_name");
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				file_name, true)));
		WebElement tab = driver
				.findElement(By
						.xpath("//ul[@class='apply-navigation']//span[contains(text(), '"
								+ linkText + "')]/.."));
		System.out.println("And I click on '" + tab.getText() + "' tab");
		System.out
				.println("----------------------------------------------------------------------------");
		writer.println("And I click on '" + tab.getText() + "' tab");
		writer.println("----------------------------------------------------------------------------");
		writer.close();
		tab.click();
	}

}

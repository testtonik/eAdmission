package eAdmission;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import eAdmission.TBase.Browsers;

public class LayoutChecker extends Page {

	public static void verifyCommonElements(WebDriver driver)
			throws BiffException, IOException {
		DataSheet dtSht = new DataSheet("Checkpoints");

		LayoutChecker.checkWidth(driver, dtSht, "app-title",
				driver.findElement(By.id("app-title")));
		LayoutChecker.checkHeight(driver, dtSht, "app-title",
				driver.findElement(By.id("app-title")));
		LayoutChecker.checkY(driver, dtSht, "app-title",
				driver.findElement(By.id("app-title")));
		LayoutChecker.checkWidth(driver, dtSht, "help-area-content",
				driver.findElement(By.id("help-area-content")));
		LayoutChecker.checkHeight(driver, dtSht, "help-area-content",
				driver.findElement(By.id("help-area-content")));
		LayoutChecker.checkY(driver, dtSht, "help-area-content",
				driver.findElement(By.id("help-area-content")));
		// TODO Auto-generated constructor stub
	}

	public static void checkWidth(WebDriver driver, DataSheet dtSht,
			String element_name, WebElement we) throws BiffException,
			IOException {
		Helper.verifyValue(
				dtSht.getDimensions(element_name, "Name") + " width",
				Integer.toString(we.getSize().width),
				dtSht.getDimensions(element_name, "width"));
	}

	public static void checkHeight(WebDriver driver, DataSheet dtSht,
			String element_name, WebElement we) throws BiffException,
			IOException {
		Helper.verifyValue(dtSht.getDimensions(element_name, "Name")
				+ " height", Integer.toString(we.getSize().height),
				dtSht.getDimensions(element_name, "height"));
	}

	public static void checkY(WebDriver driver, DataSheet dtSht,
			String element_name, WebElement we) throws BiffException,
			IOException {
		Helper.verifyValue(dtSht.getDimensions(element_name, "Name") + " y",
				Integer.toString(we.getLocation().y),
				dtSht.getDimensions(element_name, "y"));
	}

	public static void layoutNazy(WebDriver driver, Browsers browser)
			throws FileNotFoundException, UnsupportedEncodingException,
			InterruptedException {
		Thread.sleep(1000);
		List<WebElement> h1 = driver.findElements(By.tagName("h1"));
		List<WebElement> h2 = driver.findElements(By.tagName("h2"));
		List<WebElement> p = driver.findElements(By.tagName("p"));
		List<WebElement> span = driver.findElements(By.tagName("span"));
		List<WebElement> div = driver.findElements(By.tagName("div"));
		LayoutChecker.spitDataToFile(driver, span, browser);
		LayoutChecker.spitDataToFile(driver, p, browser);
		LayoutChecker.spitDataToFile(driver, h1, browser);
		LayoutChecker.spitDataToFile(driver, h2, browser);
		LayoutChecker.spitDataToFile(driver, div, browser);
	}

	public static void spitDataToFile(WebDriver driver, List<WebElement> we,
			Browsers browser) throws FileNotFoundException,
			UnsupportedEncodingException {
		WebElement we_first = we.get(0);
		File file = new File("C:\\Layout-" + browser + "-"
				+ we_first.getTagName() + ".txt");
		WebElement we_relative = driver.findElement(By.id("app-title"));
		PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		for (int i = 0; i < we.size(); i++) {
			WebElement we_current = we.get(i);
			if (we_current.isDisplayed()
					&& we_current.getText().equals("*") == false) {
				writer.println("----------------------------------------");
				writer.println("Text=" + we_current.getText());
				writer.println("----------------------------------------");
				writer.println("x="
						+ (we_current.getLocation().x - we_relative
								.getLocation().x));
				writer.println("y="
						+ (we_current.getLocation().y - we_relative
								.getLocation().y));
				writer.println("height=" + we_current.getSize().height);
				writer.println("width=" + we_current.getSize().width);
			}
		}
		writer.close();
	}

}

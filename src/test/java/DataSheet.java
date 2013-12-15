package eAdmission;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import jxl.*;
import jxl.read.biff.BiffException;

class DataSheet {

	private Workbook workbook;
	private Sheet dataSheet;

	private String path2 = System.getProperty("user.dir")+"/src/eAdmission/testscenarious.xlsx";

	public DataSheet(String spdSheet) throws BiffException, IOException {
		workbook = Workbook.getWorkbook(new File(path2));
		dataSheet = workbook.getSheet(spdSheet);
	}

	public String getValue(String columnName, int row) {

		LabelCell lbCell = dataSheet.findLabelCell(columnName);
		if (lbCell != null) {
			Cell a1 = dataSheet.getCell(lbCell.getColumn(), row);
			String stringa1 = a1.getContents();
			return stringa1;
		} else {
			return null;
		}
	}

	public int getRows() {

		return dataSheet.getRows();

	}

	public HashMap<String, String> getXPathRepository(String page) {

		Sheet sheet = workbook.getSheet("Repository");
		HashMap<String, String> allElements = new HashMap<String, String>();
		int nmrRows = sheet.getRows();
		for (int i = 1; i < nmrRows; i++) {
			if (sheet.getCell(0, i).getContents().equals(page)) {
				if (sheet.getCell(1, i).getContents().trim() == "") {
					allElements.put(sheet.getCell(2, i).getContents(), sheet
							.getCell(3, i).getContents());
				} else {

					allElements.put(sheet.getCell(1, i).getContents().trim()
							+ ":" + sheet.getCell(2, i).getContents().trim(),
							sheet.getCell(3, i).getContents());
				}
			}
		}
		return allElements;
	}

	public HashMap<String, String> getElementType(String page) {

		Sheet sheet = workbook.getSheet("Repository");
		HashMap<String, String> allElementsTypes = new HashMap<String, String>();
		int nmrRows = sheet.getRows();
		for (int i = 1; i < nmrRows; i++) {
			if (sheet.getCell(0, i).getContents().equals(page)) {

				if (sheet.getCell(1, i).getContents().trim() == "") {

					allElementsTypes.put(sheet.getCell(2, i).getContents(),
							sheet.getCell(5, i).getContents());

				} else {
					allElementsTypes.put(sheet.getCell(1, i).getContents()
							.trim()
							+ ":" + sheet.getCell(2, i).getContents().trim(),
							sheet.getCell(5, i).getContents());
				}
			}
		}
		return allElementsTypes;
	}

	public HashMap<String, String> getElementsLocation(String page) {

		Sheet sheet = workbook.getSheet("Repository");
		HashMap<String, String> allElementsLocation = new HashMap<String, String>();
		int nmrRows = sheet.getRows();
		for (int i = 1; i < nmrRows; i++) {
			if (sheet.getCell(0, i).getContents().equals(page)) {
				allElementsLocation.put(sheet.getCell(2, i).getContents(),
						sheet.getCell(6, i).getContents());
			}
		}
		return allElementsLocation;
	}

	public String getDimensions(String name, String dimension)
			throws BiffException, IOException {
		// TODO Auto-generated method stub
		String dim_value = "";
		LabelCell lbCell = dataSheet.findLabelCell("ID");
		LabelCell dmnCell = dataSheet.findLabelCell(dimension);
		int nmrRows = dataSheet.getRows();
		for (int i = 1; i < nmrRows; i++) {
			Cell a1 = dataSheet.getCell(lbCell.getColumn(), i);
			if (a1.getContents().equals(name)) {
				Cell a2 = dataSheet.getCell(dmnCell.getColumn(), i);
				dim_value = a2.getContents();
			}
		}
		return dim_value;
	}

	public static void checkForDefaultValues(String string) {
		// TODO Auto-generated method stub

	}

}
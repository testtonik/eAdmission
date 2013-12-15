package eAdmission;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import static org.junit.Assert.*; 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Callista {

	public static void getCallista(
			HashMap<String, String> PersInfPgAllElementsHM)
			throws SAXException, IOException, ParserConfigurationException {
		
		File fXmlFile = new File("C:\\test.xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		NodeList nodeList = doc.getElementsByTagName("textField");
		System.out.println(nodeList.getLength());

		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			if (nodeList.item(x).getAttributes()
					.getNamedItem("FORMS_LOGICAL_NAME").getNodeValue()
					.equals("APAV_APPLICANT_NAME_0")) {
				System.out.println(nodeList.item(x).getAttributes()
						.getNamedItem("VALUE").getNodeValue());
				assertEquals(
						PersInfPgAllElementsHM.get("Last name").toUpperCase()
								+ ", "
								+ PersInfPgAllElementsHM.get("Title")
										.toUpperCase()
								+ " "
								+ PersInfPgAllElementsHM.get("First name")
										.toUpperCase(), nodeList.item(x)
								.getAttributes().getNamedItem("VALUE")
								.getNodeValue());
			}
		}
	}
}

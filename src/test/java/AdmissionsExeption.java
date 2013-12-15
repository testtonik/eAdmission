package eAdmission;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class AdmissionsExeption extends Exception {
	public AdmissionsExeption(String msg, PrintWriter writer) {
		super(msg);
		writer.println(msg);
		writer.close();
	}
}

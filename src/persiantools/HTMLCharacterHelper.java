package persiantools;
import java.io.*;
import org.apache.commons.io.*;

public class HTMLCharacterHelper {
	public static void removeAll_NBSP_ZWNJ_QUOT(File file) throws IOException{
		String content;
		FileInputStream fis = new FileInputStream(file);
		content = IOUtils.toString(fis, "UTF8");
		fis.close();
		content = content.replaceAll("&nbsp;", " ");
		content = content.replaceAll("&rlm;", "‌");
		content = content.replaceAll("&shy;", "‌");
		content = content.replaceAll("&zwnj;", "‌");
		
		content = content.replaceAll("&ndash;", "-");
		
		content = content.replaceAll("&quot;", "\"");
		content = content.replaceAll("&rdquo;", "«");
		content = content.replaceAll("&ldquo;", "»");
		content = content.replaceAll("&laquo;", "«");
		content = content.replaceAll("&raquo;", "»");
		FileOutputStream fos = new FileOutputStream(file);
		IOUtils.write(content, fos, "UTF8");
		fos.close();
	}
}
 /*
&rdquo; --> «
&ldquo; --> »
&ndash; --> –
*/
package probono.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WordApi {
	static String apikey = "A9CAF26B128DB44DA671FA8334A73CE9";
	static String url = "https://stdict.korean.go.kr/api/view.do?key=" + apikey + "&advanced=y&method=TARGET_CODE&q=";

	
	public static void main(String[] args) {
		System.out.println(crawler("4"));
	}
	
	public static ArrayList<HashMap<String, String>> crawler(String inputnumber) {
		ArrayList<HashMap<String, String>> resultlist = new ArrayList<HashMap<String, String>>();
		Document doc = null;
		try {
			while (resultlist.size() < Integer.valueOf(inputnumber)) {
				HashMap<String, String> finder = new HashMap<String, String>();
				String number = String.valueOf((int) (Math.random() * 550000));
				doc = Jsoup.connect(url + number).timeout(10000).get();

				Elements scripts2 = doc.getElementsByTag("word");
				for (Element e : scripts2) {
					finder.put("word", StringUtils.substringBetween(e.toString(), "CDATA[", "]").replace("-", "").replace("^", "").replace(":", ""));
					break;
				}
				Elements scripts3 = doc.getElementsByTag("definition");
				for (Element e : scripts3) {
					finder.put("definition", StringUtils.substringBetween(e.toString(), "CDATA[", "]").replace("-", "").replace("^", "").replace(":", ""));
					break;
				}
				if(finder.get("word")!=null) {
					resultlist.add(finder);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultlist;
	}
}
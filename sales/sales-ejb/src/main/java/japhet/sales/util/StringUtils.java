package japhet.sales.util;

import java.util.List;

public class StringUtils {

	/**
	 * Adds the 'https' prefix in case that the specified URL
	 * doesn't contain 'https' or 'http' either.
	 * @param url
	 * @return
	 */
	public static final String urlCompletion(String url) {
		final String httpsChain = "https://";
		final String httpChain = "http://";
		if(!(url.toLowerCase()).contains(httpsChain) && 
				!(url.toLowerCase()).contains(httpChain)) {
			url = httpsChain + url;
		}
		return url;
	}
	
	/**
	 * Adds wildcards to a simple String.
	 * @param param
	 * @return
	 */
	public static final String wildcardParameter(String param) {
		//Clean String
		param.toUpperCase();
		param.trim();
		//Generate string with wildcard
		StringBuilder strBldr = new StringBuilder("%");
		strBldr.append(param);
		strBldr.append("%");
		return strBldr.toString();
	}
	
	/**
	 * Generates a comma separated list in String format
	 * from a List<Short> object.
	 * @param list
	 * @return
	 */
	public static String listToCSVString(List<Short> list) {
		StringBuilder csv = new StringBuilder("");
		if(list != null) {
			short index = 0;
			for(Short element : list) {
				csv.append(element);
				if(list.size() > ++index) {
					csv.append(",");
				}
			}
		}
		return csv.toString();
	}
}

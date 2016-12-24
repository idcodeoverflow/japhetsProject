package japhet.sales.util;

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
	
}

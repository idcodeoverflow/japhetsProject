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
	
}

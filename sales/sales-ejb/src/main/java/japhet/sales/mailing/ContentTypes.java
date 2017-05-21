/**
 * 
 */
package japhet.sales.mailing;

/**
 * @author David Israel Garcia Alcazar
 *
 */
public enum ContentTypes {
	TEXT_HTML ("text/html");
	
	private String mimeType;
	
	ContentTypes(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getMimeType() {
		return mimeType;
	}
}

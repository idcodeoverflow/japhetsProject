package japhet.sales.rest.util;

public class PathTools {
	
	/**
	 * Obtains a substring obtained from removing the 
	 * last N occurrences of the token specified.
	 * @param path
	 * @param token
	 * @param occurrence
	 * @return
	 */
	public static String substrPerNTokenReverse(String path, char token, int occurrence) {
		int boundaryIndex = 0;
		if(path != null) {
			short count = 0;
			for(int index = path.length() - 1; index >= 0; index--) {
				if(path.charAt(index) == token) {
					count++;
					if(occurrence == count) {
						boundaryIndex = index;
						break;
					}
				}
			}
			if(boundaryIndex != 0) {
				path = path.substring(0, boundaryIndex);
			}
		}
		return path;
	}

}

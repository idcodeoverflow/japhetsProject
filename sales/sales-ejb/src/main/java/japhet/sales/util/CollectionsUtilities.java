package japhet.sales.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionsUtilities {

	public static void stringListToUppercase(List<String> strings) {
		List<String> newStrings = new ArrayList<>();
		if(strings == null) {
			return;
		}
		for (String string : strings) {
			newStrings.add(string.toUpperCase());
		}
		strings = newStrings;
	}
	
	public static void stringSetToUppercase(Set<String> strings) {
		Set<String> newStrings = new HashSet<>();
		if(strings == null) {
			return;
		}
		for (String string : strings) {
			newStrings.add(string.toUpperCase());
		}
		strings = newStrings;
	}
	
}

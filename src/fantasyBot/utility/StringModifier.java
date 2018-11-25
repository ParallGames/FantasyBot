package fantasyBot.utility;

public class StringModifier {
	
	private static final String VALUE_OF_REGENERATION_TOTAL = "**VALUE_OF_REGENERATION_TOTAL**";
	
	public static String consumableStringSetValue(String str, int value) {
		return str.replace(VALUE_OF_REGENERATION_TOTAL, Integer.toString(value));
	}
	
}

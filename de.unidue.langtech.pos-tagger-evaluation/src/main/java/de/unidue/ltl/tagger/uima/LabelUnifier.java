package de.unidue.ltl.tagger.uima;

public class LabelUnifier {

	public static String mapLabels(String input, String goldLabel) {
		switch (goldLabel) {
		case "HT":
			return "HT";
		case "URL":
			return "URL";
		case "USR":
			return "USR";
		case "RT":
			return "RT";
		case "XXCol":
			return ":";
		}
		return input;
	}

	public static String mapCoarse(String coarsePred, String coarseGold,
			String token) {
		if (token.startsWith("www") || token.startsWith("http")
				|| token.matches("^#[a-zA-Z0-9_-]+") || token.equals("RT")
				|| token.matches("^@[a-zA-Z0-9_-]+")) {
			return coarseGold;
		}

		return coarsePred;
	}

}

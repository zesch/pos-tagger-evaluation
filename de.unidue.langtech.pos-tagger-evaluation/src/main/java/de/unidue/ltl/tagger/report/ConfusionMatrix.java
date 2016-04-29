package de.unidue.ltl.tagger.report;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import de.tudarmstadt.ukp.dkpro.core.api.frequency.util.ConditionalFrequencyDistribution;

public class ConfusionMatrix {
	private List<String> goldValues;
	private List<String> predictedValues;

	private ConditionalFrequencyDistribution<String, String> cfd;

	public ConfusionMatrix() {
		this.goldValues = new ArrayList<String>();
		this.predictedValues = new ArrayList<String>();
		this.cfd = new ConditionalFrequencyDistribution<String, String>();

	}

	public ConfusionMatrix(List<String> goldValues, List<String> predictedValues) {
		if (goldValues.size() != predictedValues.size()) {
			throw new IllegalArgumentException(
					"Gold and predicted need to be of equal size.");
		}

		this.goldValues = new ArrayList<String>(goldValues);
		this.predictedValues = new ArrayList<String>(predictedValues);
		this.cfd = new ConditionalFrequencyDistribution<String, String>();

	}

	public void registerOutcome(String goldValue, String predictedValue) {
		cfd.inc(goldValue, predictedValue);

		goldValues.add(goldValue);
		predictedValues.add(predictedValue);
	}

	public List<String> getTopConfusions(int n) {
		Map<String, Long> confusions = new HashMap<String, Long>();

		for (String outerKey : cfd.getConditions()) {
			for (String innerKey : cfd.getFrequencyDistribution(outerKey)
					.getKeys()) {
				if (!outerKey.equals(innerKey)) {
					confusions.put(
							outerKey + "-" + innerKey,
							cfd.getFrequencyDistribution(outerKey).getCount(
									innerKey));
				}
			}
		}

		Map<String, Long> sortedConfusions = new TreeMap<String, Long>(
				new ValueComparator(confusions));
		sortedConfusions.putAll(confusions);

		int i = 0;
		List<String> topConfusions = new ArrayList<String>();
		for (String key : sortedConfusions.keySet()) {
			topConfusions.add(key + " - " + confusions.get(key));
			i++;
			if (i > n) {
				break;
			}

		}
		return topConfusions;
	}

	public double getWordClassAccuracy() {
		int correct = 0;

		for (int i = 0; i < goldValues.size(); i++) {
			if (goldValues.get(i).equals(predictedValues.get(i))) {
				correct++;
			}
		}

		return (double) correct / goldValues.size();
	}

	public String getWordClassAccuracyOverview() {
		Map<String, WordClass> accWc = new HashMap<String, WordClass>();
		for (int i = 0; i < predictedValues.size(); i++) {
			String pred = predictedValues.get(i);
			String gold = goldValues.get(i);

			WordClass wordClass = accWc.get(gold);
			if (wordClass == null) {
				wordClass = new WordClass(gold);
			}
			wordClass.update(pred);
			accWc.put(gold, wordClass);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%10s %10s %10s %10s", "WordClass", "Correct",
				"Incorrect", "Accuracy"));
		sb.append("\n");
		List<String> keys = new ArrayList<String>(accWc.keySet());
		Collections.sort(keys);
		for (String wc : keys) {
			WordClass wordClass = accWc.get(wc);
			sb.append(String.format("%10s %10s %10s %10.1f",
					wordClass.wordClass, wordClass.getCorrect(),
					wordClass.getIncorrect(), wordClass.getAccuracy()));
			sb.append("\n");
		}

		return sb.toString();
	}

	public String getFormattedMatrix() {

		Set<String> catSet = new HashSet<String>();
		catSet.addAll(goldValues);
		catSet.addAll(predictedValues);

		List<String> categories = new ArrayList<String>(catSet);
		Collections.sort(categories);

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%10s", ""));
		for (String category : categories) {
			sb.append(String.format("%10s", category));
		}
		sb.append("\n");

		for (String outerCategory : categories) {
			sb.append(String.format("%10s", outerCategory));
			for (String innerCategory : categories) {
				String val = formatValue(cfd.getCount(innerCategory,
						outerCategory));
				//suppress zeros in matrix
				val = val.equals("0") ? "" : val;
				sb.append(String.format("%10s", val));
			}
			sb.append("\n");
		}
		sb.append("\n");

		sb.append("NOTE:\nHORIZONTAL axis shows the GOLD label\nVERTICAL axis shows the PREDICTED label");

		return sb.toString();
	}

	public static String formatValue(long value) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat
				.getInstance(Locale.US);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

		symbols.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(symbols);
		String format = formatter.format((long) value);
		return format;
	}

	class ValueComparator implements Comparator<String> {

		Map<String, Long> base;

		public ValueComparator(Map<String, Long> base) {
			this.base = base;
		}

		public int compare(String a, String b) {

			if (base.get(a) < base.get(b)) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	class WordClass {
		private String wordClass;
		private double correct;
		private double incorrect;

		public WordClass(String wordClass) {
			this.wordClass = wordClass;
			this.correct = 0.0;
			this.incorrect = 0.0;
		}

		public void update(String pred) {
			if (wordClass.equals(pred)) {
				correct++;
			} else {
				incorrect++;
			}
		}

		public double getAccuracy() {
			return correct / (incorrect + correct) * 100;
		}

		public String getCorrect() {
			return ConfusionMatrix.formatValue((long) correct);
		}

		public String getIncorrect() {
			return formatValue((long) incorrect);
		}
	}
}
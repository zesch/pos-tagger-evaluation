package de.unidue.ltl.tagger.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.resource.ResourceInitializationException;


public class SequenceReportHTML extends SequenceReportAbstract {

	@Override
	public void initialize(final UimaContext context)
			throws ResourceInitializationException {
		SUFFIX = ".html";
		super.initialize(context);

	}

	@Override
	protected void writeHeader(BufferedWriter bw) throws Exception {
		bw.write(getDocumentHeader());
	}

	@Override
	protected void process(BufferedWriter bw, List<String> tokens,
			List<String> gold, List<String> pred) throws IOException {

		double accuracy = calculateAccuracy(gold, pred);

		List<Integer> mismatches = getIdxOfMismatches(gold, pred);

		bw.write(String.format("ID: %5d, Accuracy %7.3f", sentId, accuracy));
		bw.write("<table>");
		bw.write("\n");
		bw.write(writeTokens(tokens, mismatches));
		bw.write(writeTags(gold, "Gold:"));
		bw.write(writeTags(pred, "Pred:"));
		bw.write("\n");
		bw.write("<table>");
		bw.write("<br>");
	}

	private List<Integer> getIdxOfMismatches(List<String> gold,
			List<String> pred) {
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < gold.size(); i++) {
			if (!gold.get(i).equals(pred.get(i))) {
				ids.add(i);
			}
		}
		return ids;
	}

	private String getDocumentFooter() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	private String writeTags(List<String> tags, String rowName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr>");
		sb.append("\n");

		sb.append("<td>");
		sb.append(rowName);
		sb.append("</td>");

		for (String t : tags) {
			sb.append("<td>");
			sb.append(t);
			sb.append("</td>");
		}

		sb.append("</tr>");
		return sb.toString();
	}

	private String writeTokens(List<String> tokens, List<Integer> mismatches) {
		StringBuilder sb = new StringBuilder();

		sb.append(insertColumHighlighting(tokens.size(), mismatches));

		sb.append("<tr>");
		sb.append("\n");

		// empty cell
		sb.append("<th>");
		sb.append("</th>");

		for (String t : tokens) {
			sb.append("<th>");
			sb.append(StringEscapeUtils.escapeHtml(t));
			sb.append("</th>");
			sb.append("\n");
		}
		sb.append("</tr>");
		return sb.toString();
	}

	private String insertColumHighlighting(int numToks, List<Integer> mismatches) {
		StringBuilder sb = new StringBuilder();
		sb.append("<colgroup>");
		sb.append("\n");
		sb.append("<col>");
		sb.append("\n");
		for (int i = 0; i < numToks; i++) {
			if (mismatches.contains(i)) {
				sb.append("<col style=\"background-color:rgb(255,190,190);\">");
			} else {
				sb.append("<col>");
			}
			sb.append("\n");
		}
		sb.append("\n");
		sb.append("</colgroup>");
		return sb.toString();
	}

	private double calculateAccuracy(List<String> gold, List<String> pred) {
		double match = 0.0;
		for (int i = 0; i < pred.size(); i++) {
			if (gold.get(i).equals(pred.get(i))) {
				match++;
			}
		}

		return match / gold.size();
	}

	private String getDocumentHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>");
		sb.append("\n");
		sb.append("<html>");
		sb.append(getHeaderWithCss());
		sb.append("<body>");
		sb.append("\n");
		return sb.toString();
	}

	private String getHeaderWithCss() {
		StringBuilder sb = new StringBuilder();
		sb.append("<style>");
		sb.append("\n");
		sb.append("table, th, td {");
		sb.append("\n");
		sb.append("border:1px solid rgba(0, 0, 0, .1);");
		sb.append("\n");
		sb.append("border-collapse: collapse;");
		sb.append("}");
		sb.append("\n");
		sb.append("th, td {");
		sb.append("\n");
		sb.append("padding: 5px;");
		sb.append("}");
		sb.append("\n");
		sb.append("</style>");
		sb.append("\n");
		return sb.toString();
	}

	@Override
	protected void writeFooter(BufferedWriter bw) throws Exception {
		bw.write(getDocumentFooter());
	}

}

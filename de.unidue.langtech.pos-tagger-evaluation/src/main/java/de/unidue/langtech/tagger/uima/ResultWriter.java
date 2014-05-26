package de.unidue.langtech.tagger.uima;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.performance.Stopwatch;
import de.unidue.langtech.tagger.evaluation.ConfusionMatrix;
import de.unidue.langtech.tagger.type.GoldPOS;

public class ResultWriter extends JCasAnnotator_ImplBase {

	public static final String ACC_FINE = "accFine";
	public static final String ACC_COARSE = "accCoarse";
	public static final String TIME = "time";
	public static final String CORPUS = "corpus";
	public static final String TOOL = "tool";

	public static final String PARAM_OUTPUT_FILE_NAME = "outputFile";
	@ConfigurationParameter(name = PARAM_OUTPUT_FILE_NAME, mandatory = true)
	private String outputFileName;

	public static final String PARAM_PROP_FILE = "propFile";
	@ConfigurationParameter(name = PARAM_PROP_FILE, mandatory = true)
	private File propFile;

	public static final String PARAM_TIMER_FILE = "timerFile";
	@ConfigurationParameter(name = PARAM_TIMER_FILE, mandatory = true)
	private File timerFile;

	public static final String PARAM_CORPUS_NAME = "corpusname";
	@ConfigurationParameter(name = PARAM_CORPUS_NAME, mandatory = true)
	private String corpusname;

	public static final String PARAM_TOOL_NAME = "toolname";
	@ConfigurationParameter(name = PARAM_TOOL_NAME, mandatory = true)
	private String toolname;

	private ConfusionMatrix fine;
	private ConfusionMatrix coarse;

	StringBuilder fineBuilder = new StringBuilder();
	StringBuilder coarseBuilder = new StringBuilder();

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		fine = new ConfusionMatrix();
		coarse = new ConfusionMatrix();
		
		coarseBuilder.append("Tok" + ";"
				+ "Gold" + ";"
				+ "Pred"+";\n");
		fineBuilder.append("Tok" + ";"
				+ "Gold" + ";"
				+ "Pred"+";\n");

	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
//		List<String> tokens = new ArrayList<String>();
//		List<String> coarseTagGold = new ArrayList<String>();
//		List<String> coarseTagPred = new ArrayList<String>();
//		List<String> fineTagGold = new ArrayList<String>();
//		List<String> fineTagPred = new ArrayList<String>();
		for (Token tokenAnno : JCasUtil.select(aJCas, Token.class)) {
//			tokens.add(tokenAnno.getCoveredText());
			for (GoldPOS goldPos : JCasUtil.selectCovered(GoldPOS.class,
					tokenAnno)) {
				// System.out.println(
				// tokenAnno.getCoveredText() + " " +
				// goldPos.getPosTag().getPosValue() + "/" +
				// tokenAnno.getPos().getPosValue() + " -" +
				// goldPos.getCPosTag() + "/" +
				// tokenAnno.getPos().getClass().getSimpleName()
				// );

				String fineGold = goldPos.getPosTag().getPosValue();
				String finePred = tokenAnno.getPos().getPosValue();
				fine.registerOutcome(fineGold,
						LabelUnifier.mapLabels(finePred, fineGold));

				String coarseGold = goldPos.getCPosTag();
				String coarsePred = tokenAnno.getPos().getClass()
						.getSimpleName();
				// coarse.registerOutcome(coarseGold, coarsePred);
				coarse.registerOutcome(coarseGold, LabelUnifier.mapCoarse(
						coarsePred, coarseGold, tokenAnno.getCoveredText()));

//				coarseTagGold.add(coarseGold);
//				coarseTagPred.add(coarsePred);
//				fineTagPred.add(finePred);
//				fineTagGold.add(fineGold);
			}
		}
//		print(coarseBuilder, tokens, coarseTagGold, coarseTagPred);
//		print(fineBuilder, tokens, fineTagGold, fineTagPred);
	}

	private static void print(StringBuilder p, List<String> in,
			List<String> gold, List<String> prediction) {
		
		for (int i = 0; i < in.size(); i++) {
			String tok = in.get(i);
			String g = gold.get(i);
			String pred = prediction.get(i);
			p.append(tok + ";"
					+ g + ";"
					+ pred+";");
			p.append("\n");
		}
		p.append("\n");
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		try {
			Properties props = new Properties() {
				/**
                 * 
                 */
				private static final long serialVersionUID = 1508382095114386402L;

				@Override
				public synchronized Enumeration<Object> keys() {
					return Collections.enumeration(new TreeSet<Object>(super
							.keySet()));
				}
			};
			props.setProperty(ACC_FINE, "" + fine.getAccuracy());
			props.setProperty(ACC_COARSE, "" + coarse.getAccuracy());
			props.setProperty(TIME, getTime(timerFile));
			props.setProperty(CORPUS, "" + corpusname);
			props.setProperty(TOOL, "" + toolname);
			OutputStream out = new FileOutputStream(propFile);
			props.store(out, "result file");
		} catch (FileNotFoundException e) {
			throw new AnalysisEngineProcessException(e);
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		}

		StringBuilder sb = new StringBuilder();
		sb.append(outputFileName);
		sb.append("\n");
		sb.append("Acc (fine): " + fine.getAccuracy());
		sb.append("\n");
		sb.append("Acc (coarse): " + coarse.getAccuracy());
		sb.append("\n");
		sb.append("Acc (diff): " + (coarse.getAccuracy() - fine.getAccuracy()));
		sb.append("\n");
		sb.append("\n");

		// sb.append(fine.getFormattedMatrix());
		//
		// for (String topConfusion : fine.getTopConfusions(10)) {
		// sb.append(topConfusion);
		// sb.append("\n");
		// }
		//
		// sb.append("\n");
		// sb.append(coarse.getFormattedMatrix());
		// sb.append("\n");
		// for (String topConfusion : coarse.getTopConfusions(10)) {
		// sb.append(topConfusion);
		// sb.append("\n");
		// }

		System.out.println(sb.toString());

		try {
			FileUtils.write(new File(outputFileName), sb.toString(), "UTF-8");
			FileUtils.write(
					new File(System.getProperty("user.home") + "/Desktop/coarse_"
							+ toolname + "-on-" + corpusname + "-tok.csv"),
					coarseBuilder.toString(), "UTF-8");
			FileUtils.write(
					new File(System.getProperty("user.home") + "/Desktop/fine_"
							+ toolname + "-on-" + corpusname + "-tok.csv"),
					fineBuilder.toString(), "UTF-8");
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

	private String getTime(File timerFile) throws IOException {
		FileInputStream fileInput = new FileInputStream(timerFile);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();

		return properties.getProperty(Stopwatch.KEY_SUM);
	}
}

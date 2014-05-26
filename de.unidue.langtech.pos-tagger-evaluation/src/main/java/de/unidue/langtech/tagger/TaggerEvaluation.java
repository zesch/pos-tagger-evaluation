package de.unidue.langtech.tagger;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.performance.Stopwatch;
import de.unidue.langtech.tagger.evaluation.CSVPrinter;
import de.unidue.langtech.tagger.uima.GoldPOSAnnotator;
import de.unidue.langtech.tagger.uima.ResultWriter;

public class TaggerEvaluation {
	public static String CORPORA_FOLDER_PREFIX = "";
	public static String MAPPING_FOLDER_PREFIX = "src/main/resources/";
	public static String OUTPUT_FOLDER = "target/";

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			CORPORA_FOLDER_PREFIX = args[0].endsWith("/") ? args[0] : args[0]
					+ "/";

			MAPPING_FOLDER_PREFIX = args[1].endsWith("/") ? args[1] : args[1]
					+ "/";

			OUTPUT_FOLDER = args[2].endsWith("/") ? args[2] : args[2] + "/";
		}

		EnglishTaggers.MAPPING_FOLDER_PREFIX = MAPPING_FOLDER_PREFIX;
		EnglishTaggers.posMappingFile = MAPPING_FOLDER_PREFIX
				+ EnglishTaggers.posMappingFile;
		GermanTaggers.MAPPING_FOLDER_PREFIX = MAPPING_FOLDER_PREFIX;
		GermanTaggers.posMappingFile = MAPPING_FOLDER_PREFIX
				+ GermanTaggers.posMappingFile;

		EnglishReader.CORPORA_FOLDER_PREFIX = CORPORA_FOLDER_PREFIX;
		EnglishReader.MAPPING_FOLDER_PREFIX = EnglishTaggers.MAPPING_FOLDER_PREFIX;
		EnglishReader.posMappingDefault = EnglishTaggers.posMappingFile;

		GermanReader.CORPORA_FOLDER_PREFIX = CORPORA_FOLDER_PREFIX;

		List<ReaderConfiguration> corporaEn = new ArrayList<ReaderConfiguration>();

		//
		// // written
		corporaEn.add(EnglishReader.getBrownReader());		
		corporaEn.add(EnglishReader.getBNCFormalNewspaper100kToken());
		corporaEn.add(EnglishReader.getMASCFormalNonFiction());
		corporaEn.add(EnglishReader.getMASCFormalFiction());
		corporaEn.add(EnglishReader.getMASCFormalEssay());
		corporaEn.add(EnglishReader.getMASCFormalGovDocs());
		corporaEn.add(EnglishReader.getMASCFormalTechnical());
		corporaEn.add(EnglishReader.getMASCFormalTravelGuide());
		corporaEn.add(EnglishReader.getMASCFormalJournal());
		// spoken
		corporaEn.add(EnglishReader.getSwitchboardReader());
		corporaEn.add(EnglishReader
				.getBNCSpokenConversations100kToken());		
		corporaEn.add(EnglishReader.getMASCSpokenTelephone());
		corporaEn.add(EnglishReader.getMASCSpokenDebate());
		corporaEn.add(EnglishReader.getMASCSpokenCourt());
		corporaEn.add(EnglishReader.getMASCSpokenFace2Face());

		

		// social
		corporaEn.add(EnglishReader.getGimpel());
		corporaEn.add(EnglishReader.getMASCSocialTwitter());
		corporaEn.add(EnglishReader.getMASCSocialEmail());
		corporaEn.add(EnglishReader.getMASCSocialBlog());
		
		
		//
		List<TaggerConfiguration> taggersEn = new ArrayList<TaggerConfiguration>();
		taggersEn.addAll(EnglishTaggers.getStandfordTagger());
		taggersEn.addAll(EnglishTaggers.getMateTagger());
		taggersEn.addAll(EnglishTaggers.getHepplePosTagger());
		taggersEn.addAll(EnglishTaggers.getHunPosTagger());
		taggersEn.addAll(EnglishTaggers.getLbjPosTagger());
		taggersEn.addAll(EnglishTaggers.ArktweetPosTagger());
		taggersEn.addAll(EnglishTaggers.getTreeTagger());
		taggersEn.addAll(EnglishTaggers.getOpenNlpTagger());
		taggersEn.addAll(EnglishTaggers.getClearNlpTagger());
		//
		List<ReaderConfiguration> corporaDe = new ArrayList<ReaderConfiguration>();
		corporaDe.add(GermanReader.getTuebadzReader());
		corporaDe.add(GermanReader.getRehbeinReader());
		

		List<TaggerConfiguration> taggersDe = new ArrayList<TaggerConfiguration>();
		taggersDe.addAll(GermanTaggers.getOpenNlpTagger());
		taggersDe.addAll(GermanTaggers.getStanfordTagger());
		taggersDe.addAll(GermanTaggers.getMatePosTagger());
		taggersDe.addAll(GermanTaggers.getHunPosTagger());
		taggersDe.addAll(GermanTaggers.getTreeTagger());

		evaluate(corporaEn, taggersEn, "enAll");
		evaluate(corporaDe, taggersDe, "deAll");
	}

	private static void evaluate(List<ReaderConfiguration> readers,
			List<TaggerConfiguration> taggers, String experimentName)
			throws Exception {
		HashMap<String, List<String[]>> map = new HashMap<String, List<String[]>>();
		for (ReaderConfiguration corpus : readers) {

			String uuid = new Timestamp(new Date().getTime()).toString();

			CollectionReaderDescription reader = corpus.makeReader();
			for (TaggerConfiguration tagger : taggers) {
				AnalysisEngineDescription taggerEngineDescription = tagger
						.makeEngine();

				String humanReadable = tagger.humanRedableName + "-on-"
						+ corpus.humanReadableName;
				System.out.println("################################");
				System.out.println("START: " + humanReadable);
				String detailFile = OUTPUT_FOLDER + uuid + "_" + humanReadable
						+ "_zDetail.txt";
				String timerFile = OUTPUT_FOLDER + uuid + "_" + humanReadable
						+ "_timer.txt";
				String accFile = OUTPUT_FOLDER + uuid + "_" + humanReadable
						+ "_acc.txt";

				SimplePipeline.runPipeline(
						reader,
						createEngineDescription(GoldPOSAnnotator.class),
						createEngineDescription(Stopwatch.class,
								Stopwatch.PARAM_TIMER_NAME, "t1"),
						createEngineDescription(taggerEngineDescription),
						createEngineDescription(Stopwatch.class,
								Stopwatch.PARAM_TIMER_NAME, "t1",
								Stopwatch.PARAM_OUTPUT_FILE, timerFile),
						createEngineDescription(ResultWriter.class,
								ResultWriter.PARAM_OUTPUT_FILE_NAME,
								detailFile, ResultWriter.PARAM_PROP_FILE,
								accFile, ResultWriter.PARAM_TIMER_FILE,
								timerFile, ResultWriter.PARAM_CORPUS_NAME,
								corpus.humanReadableName,
								ResultWriter.PARAM_TOOL_NAME,
								tagger.humanRedableName));

				outputTimes(timerFile);
			}

			List<String[]> resultOverview = getResultOverview(uuid);
			Collections.sort(resultOverview, new Comparator<String[]>() {

				public int compare(String[] o1, String[] o2) {
					return o1[0].compareTo(o2[0]);
				}
			});
			map.put(corpus.humanReadableName, resultOverview);
		}

		CSVPrinter.createCSV(OUTPUT_FOLDER, experimentName, map);
	}

	private static void outputTimes(String timerFile) throws IOException {
		FileInputStream fileInput = new FileInputStream(new File(timerFile));
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();

		Map<String, Double> results = new HashMap<String, Double>();
		for (String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);
			results.put(key, Double.valueOf(value));
		}

		DecimalFormat df = new DecimalFormat("0.00");

		System.out.println();
		System.out.println("time:    "
				+ df.format(results.get(Stopwatch.KEY_SUM)) + " s");
		System.out.println("avg:     "
				+ df.format(results.get(Stopwatch.KEY_MEAN)) + " s");
		System.out.println("std-dev: "
				+ df.format(results.get(Stopwatch.KEY_STDDEV)) + " s");
	}

	private static List<String[]> getResultOverview(final String uuid)
			throws IOException {
		File target = new File(OUTPUT_FOLDER + "/");
		File[] files = target.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(uuid) && name.endsWith("acc.txt");
			}
		});

		List<String[]> perTaggerResults = new ArrayList<String[]>();
		for (File resultFile : files) {

			FileInputStream fileInput = new FileInputStream(resultFile);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			String tool = properties.getProperty(ResultWriter.TOOL);
			String accCoarse = properties.getProperty(ResultWriter.ACC_COARSE);
			String time = properties.getProperty(ResultWriter.TIME);

			perTaggerResults.add(new String[] { tool,
					accCoarse.replace(".", ","), time.replace(".", ",") });

		}
		return perTaggerResults;

	}
}

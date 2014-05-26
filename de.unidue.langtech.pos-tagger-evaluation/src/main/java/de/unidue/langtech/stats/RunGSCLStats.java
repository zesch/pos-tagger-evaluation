package de.unidue.langtech.stats;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.bnc.BncReader;
import de.unidue.langtech.tagger.reader.MASCPennReader;

public class RunGSCLStats {

	public static void main(String[] args) throws Exception

	{

		CollectionReaderDescription reader = CollectionReaderFactory
				.createReaderDescription(MASCPennReader.class,
						BncReader.PARAM_LANGUAGE, "en",
						BncReader.PARAM_SOURCE_LOCATION,
						args[0],
						BncReader.PARAM_PATTERNS,
						new String [] {"*.xml"}
						);

		AnalysisEngineDescription writer = AnalysisEngineFactory
				.createEngineDescription(CorpusStatistics.class);

		SimplePipeline.runPipeline(reader, writer);
	}
}
package de.unidue.langtech.stats;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.penntree.PennTreebankChunkedReader;
import de.unidue.langtech.tagger.reader.MASCPennReader;

public class RunSentFilter
{

    public static void main(String[] args)
        throws Exception

    {

        {

            CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                    MASCPennReader.class, MASCPennReader.PARAM_LANGUAGE, "en",
                    MASCPennReader.PARAM_SOURCE_LOCATION,
                    "/Users/Tobias/Documents/gscl/corpora/english/MASC/spoken/**",
                    MASCPennReader.PARAM_PATTERNS, "*.xml");

            AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(
                    SentenceLenFilter.class, SentenceLenFilter.PARAM_PREFIX, "masc_spoken_");

            SimplePipeline.runPipeline(reader, writer);
        }
        {

            CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                    MASCPennReader.class, MASCPennReader.PARAM_LANGUAGE, "en",
                    MASCPennReader.PARAM_SOURCE_LOCATION,
                    "/Users/Tobias/Documents/gscl/corpora/english/MASC/written/**",
                    MASCPennReader.PARAM_PATTERNS, "*.xml");

            AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(
                    SentenceLenFilter.class, SentenceLenFilter.PARAM_PREFIX, "masc_written_");

            SimplePipeline.runPipeline(reader, writer);
        }
       
        
        {

            CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                    PennTreebankChunkedReader.class, PennTreebankChunkedReader.PARAM_LANGUAGE, "en",
                    PennTreebankChunkedReader.PARAM_SOURCE_LOCATION,
                    "/Users/Tobias/Documents/gscl/corpora/english/swbd/**/",
                    PennTreebankChunkedReader.PARAM_PATTERNS, "*.pos");

            AnalysisEngineDescription writer = AnalysisEngineFactory.createEngineDescription(
                    SentenceLenFilter.class, SentenceLenFilter.PARAM_PREFIX, "swbd_");

            SimplePipeline.runPipeline(reader, writer);
        }
    }
}
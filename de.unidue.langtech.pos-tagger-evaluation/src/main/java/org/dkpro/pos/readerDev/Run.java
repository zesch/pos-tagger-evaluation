package org.dkpro.pos.readerDev;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.unidue.langtech.tagger.reader.MASCPennReader;

public class Run
{

    public static void main(String[] args)
        throws Exception
    {
        String inputFolder = "/Users/Tobias/Documents/gscl/corpora/english/MASC/**/";

        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
        		MASCPennReader.class, MASCPennReader.PARAM_LANGUAGE, "en",MASCPennReader.PARAM_SOURCE_LOCATION, inputFolder,
        		MASCPennReader.PARAM_PATTERNS, new String[] { "*.xml" });
        
//        AnalysisEngineDescription tagger = AnalysisEngineFactory
//                .createEngineDescription(TreeTaggerPosTagger.class, TreeTaggerPosTagger.PARAM_LANGUAGE,"en");

        AnalysisEngineDescription printer = AnalysisEngineFactory
                .createEngineDescription(Printer.class);
        

        SimplePipeline.runPipeline(reader,  printer);
    }

}

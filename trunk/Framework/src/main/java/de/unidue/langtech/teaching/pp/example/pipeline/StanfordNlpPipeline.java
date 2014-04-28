package de.unidue.langtech.teaching.pp.example.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;

/** 
 * 
 * @author Onur
 *
 */
public class StanfordNlpPipeline
{

    public static void main(String[] args)
        throws Exception
    {
    	
    	JCas jcas = JCasFactory.createJCas();
    	jcas.setDocumentText("I was the man in the boat");
    	jcas.setDocumentLanguage("en");
    	
    	//TODO: read text from file
     	CollectionReaderDescription reader = createReaderDescription(
                TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, "src/test/resources/test/Wikipedia_English.txt",
                TextReader.PARAM_LANGUAGE, "en"
                );


        SimplePipeline.runPipeline(
        		reader,
        		AnalysisEngineFactory.createEngineDescription(StanfordSegmenter.class),
        		AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Writer.class));
        
        
}
}

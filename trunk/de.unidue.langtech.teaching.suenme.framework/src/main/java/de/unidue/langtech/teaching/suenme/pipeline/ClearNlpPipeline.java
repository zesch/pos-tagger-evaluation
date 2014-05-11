package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.io.tei.TeiReader;


/**
 * 
 * @author suenme
 *
 */
public class ClearNlpPipeline
{
    public static void main(String[] args)
        throws Exception
    {
    	//TODO: dkProHome is null?
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	String resources = dkproHome + "\\en\\brown_tei";
    	String extension = "a01.xml";

    	@SuppressWarnings("deprecation")
		CollectionReaderDescription reader = createReaderDescription(
				TeiReader.class, 
				TeiReader.PARAM_SOURCE_LOCATION, resources,
				TeiReader.PARAM_PATTERNS, extension,
				TeiReader.PARAM_LANGUAGE, "en");
    	

        //just start the reader and print text + tags
        SimplePipeline.runPipeline(
        		reader,
        		AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
        		AnalysisEngineFactory.createEngineDescription(ClearNlpPosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Writer.class,
        				Writer.PARAM_OUTPUT_FILE, dkproHome + "\\ClearNlp.txt"));
}
}

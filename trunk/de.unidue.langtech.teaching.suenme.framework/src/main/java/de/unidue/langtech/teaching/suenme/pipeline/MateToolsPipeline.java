package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.unidue.langtech.teaching.suenme.components.GoldPOSAnnotator;
import de.unidue.langtech.teaching.suenme.components.Writer;
import de.unidue.langtech.teaching.suenme.reader.Conll2009Reader;

/** 
 * 
 * @author Onur
 *
 */
public class MateToolsPipeline
{
    public static void main(String[] args)
        throws Exception
    {
    	//set enviroment variable, change to en for english data and change to correct extension
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	String resources = dkproHome + "\\en";
    	String extension = "*.txt";

    	@SuppressWarnings("deprecation")
		CollectionReaderDescription reader = createReaderDescription(
				Conll2009Reader.class, 
				Conll2009Reader.PARAM_SOURCE_LOCATION, resources,
				Conll2009Reader.PARAM_PATTERNS, extension,
				Conll2009Reader.PARAM_LANGUAGE, "en");
    	

        //just start the reader and print text + tags
        SimplePipeline.runPipeline(
        		reader,
        		AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
        		AnalysisEngineFactory.createEngineDescription(MatePosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Writer.class,
        				Writer.PARAM_OUTPUT_FILE, dkproHome + "\\Mate.txt"));
        
        
}
}

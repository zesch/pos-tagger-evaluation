package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.conll.Conll2006Reader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;


/**
 * 
 * @author suenme
 *
 */
public class OpenNlpPipeline
{
    public static void main(String[] args)
        throws Exception
    {
    	//set enviroment variable, change to en for english data and change to correct extension
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	String resources = dkproHome + "\\de\\test";
    	String extension = "*.conll";

    	@SuppressWarnings("deprecation")
		CollectionReaderDescription reader = createReaderDescription(
				Conll2006Reader.class, 
				Conll2006Reader.PARAM_SOURCE_LOCATION, resources,
				Conll2006Reader.PARAM_PATTERNS, extension,
				Conll2006Reader.PARAM_LANGUAGE, "de");
    	

        //just start the reader and print text + tags
        SimplePipeline.runPipeline(
        		reader,
        		AnalysisEngineFactory.createEngineDescription(GenericComponent.class),
        		AnalysisEngineFactory.createEngineDescription(OpenNlpPosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Writer.class),
        		AnalysisEngineFactory.createEngineDescription(CasDumpWriter.class,
        				CasDumpWriter.PARAM_OUTPUT_FILE, resources + " output.txt"));

}
}

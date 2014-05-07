package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.io.tei.TeiReader;
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
        		AnalysisEngineFactory.createEngineDescription(GenericComponent.class),
        		AnalysisEngineFactory.createEngineDescription(OpenNlpSegmenter.class),
        		AnalysisEngineFactory.createEngineDescription(OpenNlpPosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Writer.class));

}
}

package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.io.conll.Conll2006Reader;


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
        		AnalysisEngineFactory.createEngineDescription(ClearNlpPosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Evaluator.class));
}
}

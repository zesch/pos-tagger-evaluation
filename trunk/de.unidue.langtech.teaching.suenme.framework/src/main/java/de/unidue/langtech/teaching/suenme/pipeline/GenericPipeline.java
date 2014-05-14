package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import java.io.File;
import java.util.List;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import de.unidue.langtech.teaching.suenme.components.Evaluator;
import de.unidue.langtech.teaching.suenme.components.Evaluator;
import de.unidue.langtech.teaching.suenme.components.GoldPOSAnnotator;
import de.unidue.langtech.teaching.suenme.components.Writer;
import de.unidue.langtech.teaching.suenme.reader.Conll2009Reader;
import dnl.utils.text.table.TextTable;


/**
 * 
 * @author suenme
 *
 */
public class GenericPipeline
{
    public static void main(String[] args)
        throws Exception
    {
    	//set enviroment variable, change to en for english data and change to correct extension
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	String resources = dkproHome + "\\en";
    	String extension = "*.txt";

		CollectionReaderDescription reader = createReaderDescription(
				Conll2009Reader.class, 
				Conll2009Reader.PARAM_SOURCE_LOCATION, resources,
				Conll2009Reader.PARAM_PATTERNS, extension,
				Conll2009Reader.PARAM_LANGUAGE, "en");
		
		//array of all used POS taggers
    	Class[] tagger = new Class[] {OpenNlpPosTagger.class, MatePosTagger.class, StanfordPosTagger.class, 
    			TreeTaggerPosLemmaTT4J.class, ClearNlpPosTagger.class};
    	
    	for (int i=0; i<tagger.length; i++) {
    		
            SimplePipeline.runPipeline(
            		reader,
            		AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
            		AnalysisEngineFactory.createEngineDescription(tagger[i]),
            		AnalysisEngineFactory.createEngineDescription(Writer.class,
            				Writer.PARAM_OUTPUT_FILE, dkproHome + "\\" + tagger[i].getSimpleName() + ".txt"));
    		
    	}
    	  
    	Evaluator.evaluateAll(tagger);


}
}
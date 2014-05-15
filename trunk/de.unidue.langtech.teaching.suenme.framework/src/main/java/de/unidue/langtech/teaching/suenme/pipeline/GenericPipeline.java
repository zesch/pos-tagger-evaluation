package de.unidue.langtech.teaching.suenme.pipeline;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import de.unidue.langtech.teaching.suenme.components.Evaluator;
import de.unidue.langtech.teaching.suenme.components.GoldPOSAnnotator;
import de.unidue.langtech.teaching.suenme.components.Writer;
import de.unidue.langtech.teaching.suenme.corpus.BrownTeiCorpus;
import de.unidue.langtech.teaching.suenme.corpus.Conll2009Corpus;
import de.unidue.langtech.teaching.suenme.corpus.TigerCorpus;


/**
 * 
 * @author suenme
 *
 */
public class GenericPipeline
{
    public static void main(String[] args) throws Exception
        
    {
    	//set enviroment variable, change to en for english data and change to correct extension
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
		
		//array of all used POS taggers
    	Class[] tagger = new Class[] {OpenNlpPosTagger.class, MatePosTagger.class, StanfordPosTagger.class, 
    			TreeTaggerPosLemmaTT4J.class, ClearNlpPosTagger.class};
    	
    	//array of all used Corpora
    	CollectionReaderDescription[] corpus = new CollectionReaderDescription[] {new Conll2009Corpus().getReader(),
    			new BrownTeiCorpus().getReader(), new TigerCorpus().getReader()};

    	for (int i=0; i<corpus.length; i++) {
    	for (int j=0; j<tagger.length; j++) {
    		
            SimplePipeline.runPipeline(
            		corpus[i],
            		AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
            		AnalysisEngineFactory.createEngineDescription(tagger[j]),
            		AnalysisEngineFactory.createEngineDescription(Writer.class,
            				Writer.PARAM_OUTPUT_FILE, dkproHome + "\\" + tagger[j].getSimpleName() + ".txt"));
            }
    		
    	}
    	  
    	Evaluator.evaluateAll(tagger);


}
}
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
 * Pipeline needs at least 2GB of space
 * Go to Run-> Run Configurations-> Arguments-> VM Arguments-> "-Xmx2048m
 * @author suenme
 *
 */
public class GenericPipeline
{
    public static void main(String[] args) throws Exception
        
    {
    	
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");

    	Class[] taggers = new Class[] {OpenNlpPosTagger.class, MatePosTagger.class, StanfordPosTagger.class, 
    			TreeTaggerPosLemmaTT4J.class, ClearNlpPosTagger.class};
    	
    	CollectionReaderDescription[] corpora = new CollectionReaderDescription[] {new Conll2009Corpus().getReader(),
    			new BrownTeiCorpus().getReader(), new TigerCorpus().getReader()};

    	for (CollectionReaderDescription corpus : corpora) {
 
    	for (Class tagger : taggers) {
    		
            SimplePipeline.runPipeline(
            		corpus,
            		AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
            		AnalysisEngineFactory.createEngineDescription(tagger),
            		AnalysisEngineFactory.createEngineDescription(Writer.class,
            				Writer.PARAM_OUTPUT_FILE, dkproHome + "\\" + tagger.getSimpleName() + ".txt"));
            }//end tagging
    	
    	Evaluator.evaluateAll(taggers, corpus);
    	Evaluator.deleteAll(taggers);
    		
    	}//end corpora
    	 


}
}
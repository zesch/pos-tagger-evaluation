package de.unidue.langtech.teaching.suenme.pipeline;

import static de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase.INCLUDE_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.io.tei.TeiReader;
import de.tudarmstadt.ukp.dkpro.core.io.tiger.TigerXmlReader;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import de.unidue.langtech.teaching.suenme.components.Evaluator;
import de.unidue.langtech.teaching.suenme.components.GoldPOSAnnotator;
import de.unidue.langtech.teaching.suenme.components.Writer;
import de.unidue.langtech.teaching.suenme.reader.Conll2009Reader;


/**
 * Pipeline needs at least 2GB of space
 * Go to Run-> Run Configurations-> Arguments-> VM Arguments-> "-Xmx2048m
 * @author suenme
 *
 */
public class GenericPipeline
{
    public static void main(String[] args) throws ResourceInitializationException, IOException
        
    {
    	
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	
    	//Brown Corpus
    	CollectionReaderDescription brownCorpus = CollectionReaderFactory.createReaderDescription(
                TeiReader.class,
                TeiReader.PARAM_LANGUAGE, "en",
                TeiReader.PARAM_SOURCE_LOCATION, dkproHome + "\\en\\brown_tei",
                TeiReader.PARAM_PATTERNS, new String[] {INCLUDE_PREFIX + "*.xml", INCLUDE_PREFIX + "*.xml.gz"}
        );
    	
    	//Conll2009 Corpus
    	CollectionReaderDescription conllCorpus = CollectionReaderFactory.createReaderDescription(
                Conll2009Reader.class,
                Conll2009Reader.PARAM_SOURCE_LOCATION, dkproHome + "\\de",
                Conll2009Reader.PARAM_LANGUAGE, "de",
                Conll2009Reader.PARAM_PATTERNS, new String[] {INCLUDE_PREFIX + "*.txt"}
        );
    	
    	//Tiger Corpus
    	CollectionReaderDescription tigerCorpus = CollectionReaderFactory.createReaderDescription(
                TigerXmlReader.class,
                TigerXmlReader.PARAM_SOURCE_LOCATION, dkproHome + "\\de",
                TigerXmlReader.PARAM_LANGUAGE, "de",
                TigerXmlReader.PARAM_PATTERNS, new String[] {INCLUDE_PREFIX + "*.xml", INCLUDE_PREFIX + "*.xml.gz"}
        );
    	
    	
    	
    	
    	
    	//AnalysisEngineDescriptions for given POS tagger
    	AnalysisEngineDescription openNlp = AnalysisEngineFactory.createEngineDescription(OpenNlpPosTagger.class);
    	AnalysisEngineDescription matePos = AnalysisEngineFactory.createEngineDescription(MatePosTagger.class);
    	AnalysisEngineDescription stanfordPos = AnalysisEngineFactory.createEngineDescription(StanfordPosTagger.class);
    	AnalysisEngineDescription treeTaggerPos = AnalysisEngineFactory.createEngineDescription(TreeTaggerPosLemmaTT4J.class);
    	AnalysisEngineDescription clearNlpPos = AnalysisEngineFactory.createEngineDescription(ClearNlpPosTagger.class);
    	

    	AnalysisEngineDescription[] taggers = new AnalysisEngineDescription[] {openNlp, clearNlpPos, matePos, stanfordPos,
    			treeTaggerPos};
    	
    	CollectionReaderDescription[] corpora = new CollectionReaderDescription[] {conllCorpus, brownCorpus};
    	
    	List<AnalysisEngineDescription> removedTaggers = new ArrayList<AnalysisEngineDescription>();
    	
    	
    	

    	for (CollectionReaderDescription corpus : corpora) {
 
    	for (AnalysisEngineDescription tagger : taggers) {
    		
            try {
				SimplePipeline.runPipeline(
						corpus,
						AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
						AnalysisEngineFactory.createEngineDescription(tagger),
						AnalysisEngineFactory.createEngineDescription(Writer.class,
								Writer.PARAM_OUTPUT_FILE, dkproHome + "\\" + tagger.getImplementationName() + ".txt"));
			} catch (UIMAException e) {			
				//remove tagger from array, but add it to a list so we can put it back in the array for later corpora
				taggers = (AnalysisEngineDescription[]) ArrayUtils.removeElement(taggers, tagger);
				removedTaggers.add(tagger);
				continue;
			}
              }//end tagging
    	
    	Evaluator.evaluateAll(taggers, corpus);
    	Evaluator.deleteAllTagger(taggers);
    	
    	//after evaluating one corpus, put all taggers which caused problems back in the taggers array
    	for (AnalysisEngineDescription removedTagger : removedTaggers) {
    		taggers = (AnalysisEngineDescription[]) ArrayUtils.add(taggers, removedTagger);
    	}   		
    	  }//end corpora
    	
    	Evaluator.combineResults(corpora);
    	Evaluator.deleteAllCorpora(corpora);
    	 
}
  }
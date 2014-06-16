package de.unidue.langtech.teaching.suenme.pipeline;

import static de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase.INCLUDE_PREFIX;
import static java.util.Arrays.asList;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.TagDescription;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.TagsetDescription;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.io.tei.TeiReader;
import de.tudarmstadt.ukp.dkpro.core.io.tiger.TigerXmlReader;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import de.unidue.langtech.teaching.suenme.components.EvaluatorModels;
import de.unidue.langtech.teaching.suenme.components.EvaluatorModels;
import de.unidue.langtech.teaching.suenme.components.Evaluator;
import de.unidue.langtech.teaching.suenme.components.GoldPOSAnnotator;
import de.unidue.langtech.teaching.suenme.components.Writer;
import de.unidue.langtech.teaching.suenme.reader.Conll2009Reader;

/**
 * Pipeline needs at least 2GB of space
 * Go to Run-> Run Configurations-> Arguments-> VM Arguments-> -Xmx3048m
 * @author suenme
 *
 */
public class Pipeline
{
    public static void main(String[] args)
        throws Exception
    {
    	String[] variants = new String[] { "maxent", "perceptron", 
                "fast", "dewac", "hgc", "bidirectional-distsim", "bidirectional-distsim-wsj",
                "left3words-distsim", "wsj-0-18-left3words-distsim", "ontonotes", "mayo", 
                "conll2009", "tiger"};
        
        
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
        


        Configuration[] configurations = new Configuration[] {

              new Configuration(OpenNlpPosTagger.class, asList(variants)),
              new Configuration(StanfordPosTagger.class, asList(variants)),
              new Configuration(ClearNlpPosTagger.class, asList(variants)),
              new Configuration(MatePosTagger.class, asList(variants)),
              new Configuration(TreeTaggerPosLemmaTT4J.class, asList("-none-")), 
                };
        
        CollectionReaderDescription[] corpora = new CollectionReaderDescription[] {conllCorpus, brownCorpus};
        
        for (CollectionReaderDescription corpus : corpora) {

            for (Configuration cfg : configurations) {
                for (String variant : cfg.variants) {
                    
                        Object[] parameters = ArrayUtils.addAll(cfg.parameters, new Object[] {
                                ComponentParameters.PARAM_VARIANT, variant });
                        AnalysisEngineDescription tagger = AnalysisEngineFactory.createEngineDescription(cfg.component, parameters);

                        try {
                    SimplePipeline.runPipeline(
    						corpus,
    						AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
    						AnalysisEngineFactory.createEngineDescription(tagger),
    						AnalysisEngineFactory.createEngineDescription(Writer.class,
    								Writer.PARAM_OUTPUT_FILE, dkproHome + "\\" + cfg.component.getSimpleName() + "-" + variant + ".txt"));
                        } catch (Exception e) {
            				continue;
            			}
                          }//end variant
            }//end configuration
            
                	EvaluatorModels.saveResults(configurations, corpus, variants);
                	EvaluatorModels.deleteAllTagger(configurations);
                	
                	  }//end corpora
                	
        EvaluatorModels.combineResults(corpora);
        EvaluatorModels.deleteAllCorpora(corpora);
                	 
            }
                 
    public static class Configuration
    {
        Class<? extends AnalysisComponent> component;
        public List<String> variants;
        Object[] parameters;

        public Configuration(Class<? extends AnalysisComponent> aComponent, List<String> aVariants,
                Object... aParameters)
        {
            component = aComponent;
            variants = aVariants;
            parameters = aParameters;
        }
        
        public String getConfigurationName(){
        	return component.getSimpleName();
        }
    }
}



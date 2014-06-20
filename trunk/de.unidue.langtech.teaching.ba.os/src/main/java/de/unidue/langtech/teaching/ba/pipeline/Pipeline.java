package de.unidue.langtech.teaching.ba.pipeline;

import static de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase.INCLUDE_PREFIX;
import static java.util.Arrays.asList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetTagger;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.io.tei.TeiReader;
import de.tudarmstadt.ukp.dkpro.core.io.tiger.TigerXmlReader;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import de.unidue.langtech.teaching.ba.components.EvaluatorModels;
import de.unidue.langtech.teaching.ba.components.GoldPOSAnnotator;
import de.unidue.langtech.teaching.ba.components.Writer;
import de.unidue.langtech.teaching.ba.reader.Conll2009Reader;


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
              
              new Configuration(OpenNlpPosTagger.class, asList("maxent", "perceptron")),
              new Configuration(StanfordPosTagger.class, asList("fast", "dewac", "hgc", 
              		"fast.41", "twitter","twitter-fast", "bidirectional-distsim", 
              		"bidirectional-distsim-wsj","left3words-distsim", 
            		"wsj-0-18-left3words-distsim")),
              new Configuration(ClearNlpPosTagger.class, asList("ontonotes", "mayo")),
              new Configuration(MatePosTagger.class, asList("conll2009", "tiger")),
              new Configuration(TreeTaggerPosLemmaTT4J.class, asList("-none-")),
              new Configuration(ArktweetTagger.class, asList("default")) 
                };
                         
        CollectionReaderDescription[] corpora = new CollectionReaderDescription[] {conllCorpus, brownCorpus};
        
        for (CollectionReaderDescription corpus : corpora) {
        	
            List<File> posFiles = new ArrayList<File>();

            for (Configuration cfg : configurations) {
                for (String variant : cfg.variants) {
                    
                        Object[] parameters = ArrayUtils.addAll(cfg.parameters, new Object[] {
                                ComponentParameters.PARAM_VARIANT, variant });
                        AnalysisEngineDescription tagger = AnalysisEngineFactory.createEngineDescription(cfg.component, parameters);
                        File posFile = new File(dkproHome + "\\" + cfg.component.getSimpleName() + "-" + variant + ".txt");

                        try {
                    SimplePipeline.runPipeline(
    						corpus,
    						AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
    						AnalysisEngineFactory.createEngineDescription(tagger),
    						AnalysisEngineFactory.createEngineDescription(Writer.class,
    								Writer.PARAM_OUTPUT_FILE, posFile));
                    
                       posFiles.add(posFile);
                       
                        } catch (Exception e) {
                        	 posFiles.remove(posFile);
            				continue;
            			}
                          }//end variant
            }//end configuration

            
        EvaluatorModels.saveResults(posFiles, corpus);
        EvaluatorModels.deleteAllTagger(posFiles);
        
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



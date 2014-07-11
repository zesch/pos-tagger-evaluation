package de.unidue.langtech.teaching.ba.pipeline;

import static de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase.INCLUDE_PREFIX;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.resource.ResourceInitializationException;

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
import de.unidue.langtech.teaching.ba.reader.ArkCorpusReader;
import de.unidue.langtech.teaching.ba.reader.Conll2009Reader;
import de.unidue.langtech.teaching.ba.results.ResultStore;


/**
 * Pipeline needs at least 2GB of space
 * Go to Run-> Run Configurations-> Arguments-> VM Arguments-> -Xmx3048m
 * @author suenme
 *
 */
public class Pipeline
{
    public static void main(String[] args) throws ResourceInitializationException, IOException
    {
        
    	System.setProperty("PROJECT_HOME", "src/main/resources/corpora");
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	
    	//Brown Corpus
    	CollectionReaderDescription brownCorpus = CollectionReaderFactory.createReaderDescription(
                TeiReader.class,
                TeiReader.PARAM_LANGUAGE, "en",
                TeiReader.PARAM_SOURCE_LOCATION, dkproHome + "/en/brown_tei",
                TeiReader.PARAM_POS_MAPPING_LOCATION, dkproHome + "/en/brown_tei/en-brown-pos.map",
                TeiReader.PARAM_PATTERNS, new String[] {INCLUDE_PREFIX + "*.xml", INCLUDE_PREFIX + "*.xml.gz"}
        );
    	
    	//Conll2009 Corpus
    	CollectionReaderDescription conllCorpusGerman = CollectionReaderFactory.createReaderDescription(
                Conll2009Reader.class,
                Conll2009Reader.PARAM_SOURCE_LOCATION, dkproHome + "/de",
                Conll2009Reader.PARAM_LANGUAGE, "de",
                Conll2009Reader.PARAM_PATTERNS, new String[] {INCLUDE_PREFIX + "*.txt"}
        );
    	
    	//Tiger Corpus
    	CollectionReaderDescription tigerCorpus = CollectionReaderFactory.createReaderDescription(
                TigerXmlReader.class,
                TigerXmlReader.PARAM_SOURCE_LOCATION, dkproHome + "/de",
                TigerXmlReader.PARAM_LANGUAGE, "de",
                TigerXmlReader.PARAM_PATTERNS, new String[] {INCLUDE_PREFIX + "*.xml", INCLUDE_PREFIX + "*.xml.gz"}
        );
    	
    	//TweetNLP Corpus
    	CollectionReaderDescription arkCorpus = CollectionReaderFactory.createReaderDescription(
                ArkCorpusReader.class,
                ArkCorpusReader.PARAM_SOURCE_LOCATION, dkproHome + "/en/oct27.conll",
                ArkCorpusReader.PARAM_LANGUAGE, "en",
                ArkCorpusReader.PARAM_POS_MAPPING_LOCATION, "classpath:/de/tudarmstadt/ukp/dkpro/"
                        + "core/api/lexmorph/tagset/en-arktweet.map"
        );
        


        TaggerConfiguration[] configurations = new TaggerConfiguration[] {
              
              new TaggerConfiguration(OpenNlpPosTagger.class, asList("maxent", "perceptron")),
              new TaggerConfiguration(StanfordPosTagger.class, asList("fast", "dewac", "hgc", 
              		"fast.41", "twitter","twitter-fast", "bidirectional-distsim", 
              		"bidirectional-distsim-wsj","left3words-distsim", 
            		"wsj-0-18-left3words-distsim")),
              new TaggerConfiguration(ClearNlpPosTagger.class, asList("ontonotes", "mayo")),
              new TaggerConfiguration(MatePosTagger.class, asList("conll2009", "tiger")),
              new TaggerConfiguration(TreeTaggerPosLemmaTT4J.class, asList("-none-")),
              new TaggerConfiguration(ArktweetTagger.class, asList("default")) 
                };
                         
        CorpusConfiguration[] corpusConfigurations = new CorpusConfiguration[] {
                
                new CorpusConfiguration(brownCorpus, "Brown Corpus"),
                new CorpusConfiguration(conllCorpusGerman, "CoNLL 2009 - German") 
                  };
        
        ResultStore rs = new ResultStore();
        
        for (CorpusConfiguration corpus : corpusConfigurations) {
        	
            List<File> posFiles = new ArrayList<File>();

            for (TaggerConfiguration tcfg : configurations) {
            	
                for (String variant : tcfg.variants) {
                    
                        Object[] parameters = ArrayUtils.addAll(tcfg.parameters, new Object[] {
                                ComponentParameters.PARAM_VARIANT, variant });
                        
                        if (corpus.readerDescription.equals(brownCorpus)) {
                        	
                        	parameters = ArrayUtils.addAll(tcfg.parameters, new Object[] {
                                    ComponentParameters.PARAM_VARIANT, variant,
                                    ComponentParameters.PARAM_POS_MAPPING_LOCATION, dkproHome + "/en/brown_tei/en-brown-pos.map"});
                        }
                        
                        AnalysisEngineDescription tagger = AnalysisEngineFactory.createEngineDescription(tcfg.taggerComponent, parameters);
                        File posFile = new File(dkproHome + "/" + tcfg.taggerComponent.getSimpleName() + "-" + variant + ".txt");

                        try {
                    SimplePipeline.runPipeline(
                    		corpus.readerDescription,
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

            
        rs.saveResults(posFiles, corpus.corpusName);
        rs.deleteAllTagger(posFiles);
        
                	  }//end corpora
                	
        rs.combineResults(corpusConfigurations);
        rs.deleteAllCorpora(corpusConfigurations);
                	 
            }
                 
    public static class TaggerConfiguration
    {
        Class<? extends AnalysisComponent> taggerComponent;
        public List<String> variants;
        Object[] parameters;

        public TaggerConfiguration(Class<? extends AnalysisComponent> aComponent, List<String> aVariants,
                Object... aParameters)
        {
            taggerComponent = aComponent;
            variants = aVariants;
            parameters = aParameters;
        }
        
        public String getTaggerName(){
        	return taggerComponent.getSimpleName();
        }
    }
    
    public static class CorpusConfiguration
    {
        CollectionReaderDescription readerDescription;
        String corpusName;


        public CorpusConfiguration(CollectionReaderDescription aReaderDescription, String aReaderName)
        {
            readerDescription = aReaderDescription;
            corpusName = aReaderName;
        }
        
        public String getCorpusName(){
        	return corpusName;
        }
    }
}


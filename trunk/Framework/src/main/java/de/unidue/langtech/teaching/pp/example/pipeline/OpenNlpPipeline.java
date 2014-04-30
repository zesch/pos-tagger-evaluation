package de.unidue.langtech.teaching.pp.example.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
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
    	
    	//used for single string
    	JCas jcas = JCasFactory.createJCas();
    	jcas.setDocumentText("I was the man in the boat");
    	jcas.setDocumentLanguage("en");
    	
    	//TODO: read text from file
    	@SuppressWarnings("deprecation")
		CollectionReaderDescription reader = createReaderDescription(
                Conll2006Reader.class, 
                Conll2006Reader.PARAM_SOURCE_LOCATION, "src/test/resources/test/german_tiger_train.conll",
                Conll2006Reader.PARAM_LANGUAGE, "de");


        SimplePipeline.runPipeline(
        		reader,
        		AnalysisEngineFactory.createEngineDescription(OpenNlpSegmenter.class),
        		AnalysisEngineFactory.createEngineDescription(OpenNlpPosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Writer.class));
        
        //used for single string
        for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
    		  System.out.println(tokenAnno.getCoveredText() + " " + tokenAnno.getPos().getPosValue());
      }
       	

}
}

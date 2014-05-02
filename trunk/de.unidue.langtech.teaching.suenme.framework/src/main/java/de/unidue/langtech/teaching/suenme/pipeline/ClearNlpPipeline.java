package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;


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
    	
    	//used for single string
    	JCas jcas = JCasFactory.createJCas();
    	jcas.setDocumentText("I was the man in the boat");
    	jcas.setDocumentLanguage("en");
    	
    	//TODO: read text from file
     	CollectionReaderDescription reader = createReaderDescription(
                TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, "src/test/resources/test/Wikipedia_English.txt",
                TextReader.PARAM_LANGUAGE, "en"
                );


        SimplePipeline.runPipeline(
        		reader,
        		AnalysisEngineFactory.createEngineDescription(ClearNlpSegmenter.class),
        		AnalysisEngineFactory.createEngineDescription(ClearNlpPosTagger.class),
        		AnalysisEngineFactory.createEngineDescription(Writer.class));
        
        //used for single string
        for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
    		  System.out.println(tokenAnno.getCoveredText() + " " + tokenAnno.getPos().getPosValue());
      }
       	

}
}

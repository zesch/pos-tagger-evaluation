package de.unidue.langtech.teaching.ba;

import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.ba.reader.ArkCorpusReader;



public class ArkCorpusReaderTest
{
	//change input path to "src/test/resources/test/taska-training.txt" for reading training data
    @Test
    public void testReaderExample()throws Exception
    {
    	CollectionReaderDescription arkCorpusReader = CollectionReaderFactory.createReaderDescription(
    			ArkCorpusReader.class,
    			ArkCorpusReader.PARAM_SOURCE_LOCATION, "src/test/resources/arkcorpus-test.conll");

        int i = 0;
        for (JCas jcas : new JCasIterable(arkCorpusReader)) {
        	for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
            // we know that the first line should be the first token
            if (i==0) {
                assertEquals("I", tokenAnno.getCoveredText());
                
                assertEquals("O", tokenAnno.getPos().getPosValue());
                
            }
        }
            
            System.out.println(jcas.getDocumentText());
            i++;
        }
        
          // there are 7 lines in the file
          assertEquals(8, i);
    }
}
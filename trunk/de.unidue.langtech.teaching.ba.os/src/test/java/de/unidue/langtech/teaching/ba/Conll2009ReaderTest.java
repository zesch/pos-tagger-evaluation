package de.unidue.langtech.teaching.ba;

import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.ba.reader.Conll2009Reader;



public class Conll2009ReaderTest
{
	//change input path to "src/test/resources/test/taska-training.txt" for reading training data
    @Test
    public void testReaderExample()throws Exception
    {
    	CollectionReaderDescription conll2009 = CollectionReaderFactory.createReaderDescription(
                Conll2009Reader.class,
                Conll2009Reader.PARAM_SOURCE_LOCATION, "src/test/resources/conll2009-test.txt");

        int i = 0;
        for (JCas jcas : new JCasIterable(conll2009)) {
        	for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
            // we know that the first line should be the first token
            if (i==0) {
                assertEquals("Ross", tokenAnno.getCoveredText());
                
                POS pos = tokenAnno.getPos();
                
                assertEquals("NE", pos.getPosValue());
                
            }
        }
            
            System.out.println(jcas.getDocumentText());
            i++;
        }
        
          // there are 7 lines in the file
          assertEquals(7, i);
    }
}
package de.unidue.langtech.teaching.ba;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import com.google.common.collect.Iterables;

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

        for (JCas jcas : new JCasIterable(conll2009)) {
        	
        Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
        	
        	assertEquals(7, tokens.size());

            assertEquals("Ross", Iterables.get(tokens, 0).getCoveredText());
                
            assertEquals("NE", Iterables.get(tokens, 0).getPos().getPosValue());

            System.out.println(jcas.getDocumentText());
        }  
    }
}
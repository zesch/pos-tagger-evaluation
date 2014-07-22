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
import de.unidue.langtech.teaching.ba.reader.ArkCorpusReader;


/**
 * Tests if tokens size, tokens and POS are correctly read.
 * @author Onur
 *
 */
public class ArkCorpusReaderTest
{
    @Test
    public void testReaderExample()throws Exception
    {
    	CollectionReaderDescription arkCorpus = CollectionReaderFactory.createReaderDescription(
                ArkCorpusReader.class,
                ArkCorpusReader.PARAM_SOURCE_LOCATION, "src/test/resources/arkcorpus-test.conll");

        for (JCas jcas : new JCasIterable(arkCorpus)) {
        	
        Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
        	
        	assertEquals(8, tokens.size());

            assertEquals("I", Iterables.get(tokens, 0).getCoveredText());
                
            assertEquals("O", Iterables.get(tokens, 0).getPos().getPosValue());

            System.out.println(jcas.getDocumentText());
        }  
    }
}
package de.unidue.langtech.teaching.pp.example;

import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.unidue.langtech.teaching.pp.type.OriginalSentiment;
import de.unidue.langtech.teaching.pp.type.SentimentIndex;

public class ReaderTestA
{
	//change input path to "src/test/resources/test/taska-training.txt" for reading training data
    @Test
    public void testReaderExample()throws Exception
    {
        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                ReaderTaskA.class,
                ReaderTaskA.PARAM_INPUT_FILE, "src/test/resources/test/taska-dev.txt"
        );

        int i = 0;
        for (JCas jcas : new JCasIterable(reader)) {
            // we know that the first line should be the first tweet
            if (i==0) {
                assertEquals("Won the match #getin . Plus, tomorrow is a very busy day, with Awareness Day's and debates. Gulp. Debates...", jcas.getDocumentText());
                
                OriginalSentiment origSent = JCasUtil.selectSingle(jcas, OriginalSentiment.class);
                
                assertEquals("objective", origSent.getSentiment());
                
                //check if the indexes are read correctly
                
                SentimentIndex index = JCasUtil.selectSingle(jcas, SentimentIndex.class);
                
                assertEquals(Integer.parseInt("0"), index.getBeginIndex());
                
                assertEquals(Integer.parseInt("9"), index.getEndIndex());
            }
            
            System.out.println(jcas.getDocumentText());
            i++;
        }
        
          // there are 1942 lines in the file (devdata), so we should get 1942 documents here
          // change to 17234 when using trainingdata
          assertEquals(1942, i);
    }
}
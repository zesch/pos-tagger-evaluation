package de.unidue.langtech.teaching.pp.example;

import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.unidue.langtech.teaching.pp.type.OriginalSentiment;

public class ReaderTestB
{
    //change input path to "src/test/resources/test/taskb-training.txt" for reading training data
    @Test
    public void testReaderExample()throws Exception
    {
        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                ReaderTaskB.class,
                ReaderTaskB.PARAM_INPUT_FILE, "src/test/resources/test/taskb-dev.txt"
        );

        int i = 0;
        for (JCas jcas : new JCasIterable(reader)) {
            // we know that the first line should be the first tweet
            if (i==0) {
                assertEquals("Won the match #getin . Plus, tomorrow is a very busy day, with Awareness Day's and debates. Gulp. Debates...", jcas.getDocumentText());
                
                OriginalSentiment origSent = JCasUtil.selectSingle(jcas, OriginalSentiment.class);
                
                assertEquals("\"neutral\"", origSent.getSentiment());
            }
            
            System.out.println(jcas.getDocumentText());
            i++;
        }
        
        // there are 1007 lines in the file (devdata), so we should get 1007 documents here
        // change to 6414 when using trainingdata
        assertEquals(1007, i);
    }
}
package de.unidue.langtech.teaching.pp.example;

import java.util.StringTokenizer;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * This class is for tokenizing each tweet into tokens, e.g. words. 
 */
public class TweetTokenizer
    extends JCasAnnotator_ImplBase
{
    /* found at
     * http://alvinalexander.com/blog/post/java/java-faq-stringtokenizer-example
     */
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
        
        StringTokenizer st = new StringTokenizer(jcas.getDocumentText(), " ");

        int offset = 0;
        while ( st.hasMoreTokens() )
        {
          String token = st.nextToken();
          Token tokenAnno = new Token(jcas, offset, offset + token.length());
          tokenAnno.addToIndexes();
          offset = offset + token.length() + 1;
        }
    }

}

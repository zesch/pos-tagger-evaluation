package de.unidue.langtech.teaching.pp.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.DetectedSentiment;

/**
 * The baseline determines the sentiment of a complete tweet. This is Subtask B.
 * It makes use of a method which returns the sentiment for a given string.
 * Exceptions are passed to AnalysisEngineProcessException
 * @author suenme
 *
 */
public class BaselineTaskB
    extends JCasAnnotator_ImplBase
{

    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
        System.out.println("Tweet is: " + jcas.getDocumentText());
        
		try {
			String detectedSentiment = getSentiment(jcas);
			DetectedSentiment decSent = new DetectedSentiment(jcas);
			decSent.setSentiment(detectedSentiment);
			decSent.addToIndexes();
		} catch (FileNotFoundException e) {
			throw new AnalysisEngineProcessException(e);
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		}
		}  
    
  /* 
   * Our classify method. All steps (if necessary) are explained in comments.
   */
  public String getSentiment(JCas jcas) throws FileNotFoundException, IOException{
        
		//read our word lists and store it in a JavaList, also contains emoticons
	    //source for word lists https://github.com/hinstitute/hiR/tree/master/data
		List<String> positiveWords = FileUtils.readLines(new File("src/test/resources/test/positive-words.txt"));
		List<String> negativeWords = FileUtils.readLines(new File("src/test/resources/test/negative-words.txt"));
		
		int negCounter = 0;
		int posCounter = 0;
		
		for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
		  String token = tokenAnno.getCoveredText();
		  if (positiveWords.contains(token)) {
				posCounter++;
				System.out.println("Found positive word/emoticon: " + token);
			} 
			if (negativeWords.contains(token)) {
				negCounter++;
				System.out.println("Found negative word/emoticon: " + token);
			}
		}

		int result = (posCounter - negCounter);
		
        //stores our calculated sentiment
		String output;

		if (result < 0) {
			output = "negative";
		} else if (result > 0) {
			output = "positive";
		} else {
		output = "objective-OR-neutral";
		}
		System.out.println("Tweet has " + posCounter + " positive word(s) and " + negCounter + " negative word(s).");
		return output;

	}
}

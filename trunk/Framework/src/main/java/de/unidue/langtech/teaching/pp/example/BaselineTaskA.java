package de.unidue.langtech.teaching.pp.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.unidue.langtech.teaching.pp.type.DetectedSentiment;
import de.unidue.langtech.teaching.pp.type.SentimentIndex;

/**
 * The baseline determines the sentiment of an instance of a tweet. This is Subtask A.
 * It makes use of a method which returns the sentiment for a given string.
 * Exceptions are passed to AnalysisEngineProcessException
 * There are problems with using the correct regular expression, " " and "\\s+" do not work, 
 * so we ignore exceptions
 * @author suenme
 *
 */
public class BaselineTaskA
    extends JCasAnnotator_ImplBase
{

    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
    	String tweet = jcas.getDocumentText();
        System.out.println("Tweet is: " + tweet);
        String[] tweetWords = tweet.split("\\s+");  //whitespace regex works better than " "
        String extractedTweet = new String();
        
        try {
        	
        	SentimentIndex index = JCasUtil.selectSingle(jcas, SentimentIndex.class);
        	
        	int begin = index.getBeginIndex();
        	int end = index.getEndIndex();
        	
        	//only consider tweet instances without offset conflicts
        	if (end < tweetWords.length) {
			   for (int i = begin; i<=end; i++){
				   extractedTweet = extractedTweet.concat(tweetWords[i]).concat(" "); //extract the tweet instance to be analysed from given tweet
			   }
        	}
			    
			System.out.println("Tweet instance is: " + extractedTweet);
						
			String detectedSentiment = getSentiment(extractedTweet);
			DetectedSentiment detectSent = new DetectedSentiment(jcas);
			detectSent.setSentiment(detectedSentiment);
			detectSent.addToIndexes();
			
		} catch (FileNotFoundException e) {
			throw new AnalysisEngineProcessException(e);
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		}
    }
    
    
    
  /* 
   * Our classify method. All steps (if necessary) are explained in comments.
   */
  public String getSentiment(String input) throws FileNotFoundException, IOException  {
		
		//read our word lists and store it in a JavaList, also contains emoticons
	    //source for word lists https://github.com/hinstitute/hiR/tree/master/data
		List<String> positiveWords = FileUtils.readLines(new File("src/test/resources/test/positive-words.txt"));
		List<String> negativeWords = FileUtils.readLines(new File("src/test/resources/test/negative-words.txt"));
		
		// normalize tweet (word lists only contain small letters)
		input = input.toLowerCase();
		
		//count how many positive/negative words we got
		int negCounter = 0;
		int posCounter = 0;
		
		/* StringTokenizer is a nice method for tokenizing Strings, we can determine our own split regex's, found at
		 * http://alvinalexander.com/blog/post/java/java-faq-stringtokenizer-example
		 * in case for Task A, we have to use a simple blankspace regex
		 * http://alvinalexander.com/java/java-stringtokenizer-strings-words-punctuation-marks
		 */
		
		StringTokenizer st = new StringTokenizer(input, " ");

		while ( st.hasMoreTokens() )
		{
		  String token = (String)st.nextToken();
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
		System.out.println("Tweet instance has " + posCounter + " positive word(s) and " + negCounter + " negative word(s).");
		return output;

	}
}

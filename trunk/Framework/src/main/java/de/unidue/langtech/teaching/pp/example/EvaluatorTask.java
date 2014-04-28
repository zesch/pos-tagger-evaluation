package de.unidue.langtech.teaching.pp.example;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.unidue.langtech.teaching.pp.type.DetectedSentiment;
import de.unidue.langtech.teaching.pp.type.OriginalSentiment;

/**
 * The evaluator tells us if a tweet instance was correctly classified.
 * It also gives us some details about the classification, for example how many were correctly classified.
 * 
 * @author suenme
 *
 */
public class EvaluatorTask
    extends JCasAnnotator_ImplBase
{

    private int correct;
    private int nrOfDocuments;
    
    /* 
     * This is called BEFORE any documents are processed.
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        correct = 0;
        nrOfDocuments = 0;
    }
    
    
    /* 
     * This is called ONCE for each document
     */
    @Override
    public void process(JCas jcas)
        throws AnalysisEngineProcessException
    {
        nrOfDocuments++; 
        
        DetectedSentiment detected = JCasUtil.selectSingle(jcas, DetectedSentiment.class);
        OriginalSentiment actual = JCasUtil.selectSingle(jcas, OriginalSentiment.class);

        System.out.println(actual.getSentiment() + " detected as " + detected.getSentiment());
        
        //the other two expressions are needed because a "neutral" output should be equal to "objective" and "objective-OR-neutral"
        if (detected.getSentiment().equals(actual.getSentiment()) || 
       		 (detected.getSentiment().equals("objective-OR-neutral")) && actual.getSentiment().equals("neutral") || 
       		    (detected.getSentiment().equals("objective-OR-neutral")) && actual.getSentiment().equals("objective")) {
        	correct++;
        	System.out.println("Correctly classified!");
        } else {
        	System.out.println("Wrongly classified!");
        }
		System.out.println("---------------------------------------------------");

    }


    /* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        System.out.println(correct + " tweets out of " + nrOfDocuments + " tweets were correctly classified. Scored an accuracy of " + ((double)correct/(double)nrOfDocuments)*100 + "%.");
    }
}
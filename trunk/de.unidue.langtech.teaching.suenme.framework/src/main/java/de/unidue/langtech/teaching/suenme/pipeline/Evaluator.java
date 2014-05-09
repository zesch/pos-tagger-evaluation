package de.unidue.langtech.teaching.suenme.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.suenme.type.GoldPOS;


/**
 * 
 * @author suenme
 *
 */
public class Evaluator
    extends JCasAnnotator_ImplBase
{

    private int correct;
    private int nrOfDocuments;
    private List<String> detectedPosAnnos = new ArrayList<String>();
	private List<String> goldPosAnnos = new ArrayList<String>();
	private List<String> posTexts = new ArrayList<String>();
    static double accuracy;
    
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
        
        for (GoldPOS goldPos : JCasUtil.select(jcas, GoldPOS.class)) {
    		
			goldPosAnnos.add(goldPos.getPosTag().getPosValue());
		}
  

  for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
	
	detectedPosAnnos.add(tokenAnno.getPos().getPosValue());
	posTexts.add(tokenAnno.getCoveredText());
  }
 
      nrOfDocuments = detectedPosAnnos.size();

  for (int i = 0; i<detectedPosAnnos.size(); i++) {
	  String gold = goldPosAnnos.get(i);
	  String detected = detectedPosAnnos.get(i);
		  
	      System.out.println("Token: " + posTexts.get(i));
		  System.out.println(gold + " detected as " + detected);
		  
		  if (gold.equals(detected)) {
			  System.out.println("Correctly tagged!");
			  correct++;
		  } else {
			  System.out.println("Wrongly tagged!");
		  }
		  
		  accuracy = ((double)correct/(double)nrOfDocuments)*100;
	  }

    }


    /* 
     * This is called AFTER all documents have been processed.
     */
    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();
        
        System.out.println(correct + " out of " + nrOfDocuments + " POS tags correctly tagged. Scored an accuracy of " + accuracy + "%.");
    }
}
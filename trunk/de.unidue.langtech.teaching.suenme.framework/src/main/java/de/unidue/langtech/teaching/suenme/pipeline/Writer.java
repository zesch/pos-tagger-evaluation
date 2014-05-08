package de.unidue.langtech.teaching.suenme.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.suenme.type.GoldPOS;

/**
 * Custom extern writer class to write results to screen
 * @author Onur
 *
 */
public class Writer extends JCasAnnotator_ImplBase {
	
	
	public Writer(){
		
	}
	
	public void process(JCas jcas){
		
		List<String> detectedPosAnnos = new ArrayList<String>();
		List<String> goldPosAnnos = new ArrayList<String>();
		List<String> detectedPosTexts = new ArrayList<String>();
		int correct = 0;
		
  for (GoldPOS goldPos : JCasUtil.select(jcas, GoldPOS.class)) {
		
			goldPosAnnos.add(goldPos.getPosTag().getPosValue());
		}
  

  for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
	
	detectedPosAnnos.add(tokenAnno.getPos().getPosValue());
	detectedPosTexts.add(tokenAnno.getCoveredText());
}

      int nrOfDocuments = detectedPosAnnos.size();

  for (int i = 0; i<detectedPosAnnos.size(); i++) {
	  String first = goldPosAnnos.get(i);
	  String second = detectedPosAnnos.get(i);
		  
	      System.out.println("Token: " + detectedPosTexts.get(i));
		  System.out.println(first + " detected as " + second);
		  
		  if (first.equals(second)) {
			  System.out.println("Correctly tagged!");
			  correct++;
		  } else {
			  System.out.println("Wrongly tagged!");
		  }
	  }
	  
  
  
      System.out.println(correct + " out of " + nrOfDocuments + " POS tags correctly tagged. Scored an accuracy of " + ((double)correct/(double)nrOfDocuments)*100 + "%.");


	}
	
	
}

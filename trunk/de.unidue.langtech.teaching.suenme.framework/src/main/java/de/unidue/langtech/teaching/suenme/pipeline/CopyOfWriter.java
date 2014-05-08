package de.unidue.langtech.teaching.suenme.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.suenme.type.GoldPOS;

/**
 * Custom extern writer class to write results to screen
 * @author Onur
 *
 */
public class CopyOfWriter extends JCasAnnotator_ImplBase {
	
	
	public CopyOfWriter(){
		
	}
	
	public void process(JCas jcas){
		//TODO: List for saving GoldPOS tags
		
		List<String> detectedPosAnnos = new ArrayList<String>();
		List<String> goldPosAnnos = new ArrayList<String>();
		List<String> posTexts = new ArrayList<String>();
		GoldPOS goldPOS = new GoldPOS(jcas);
		int correct = 0;

  for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
	detectedPosAnnos.add(tokenAnno.getPos().getPosValue());
	posTexts.add(tokenAnno.getCoveredText());
 }
  for (int i = 0; i<detectedPosAnnos.size(); i++) {
	  System.out.println(detectedPosAnnos.get(i));

  }


      int nrOfDocuments = detectedPosAnnos.size();

  for (int i = 0; i<detectedPosAnnos.size(); i+=2) {
	  String first = detectedPosAnnos.get(i);
	  String second = detectedPosAnnos.get(i + 1);
	  
      System.out.println("Token: " + posTexts.get(i));
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

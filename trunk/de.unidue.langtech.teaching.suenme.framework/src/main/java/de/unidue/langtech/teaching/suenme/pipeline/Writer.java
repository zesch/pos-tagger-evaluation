package de.unidue.langtech.teaching.suenme.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Custom extern writer class to write results to screen
 * @author Onur
 *
 */
public class Writer extends JCasAnnotator_ImplBase {
	
	
	public Writer(){
		
	}
	
	public void process(JCas jcas){
		
		List<String> posAnnos = new ArrayList<String>();
		List<String> posTexts = new ArrayList<String>();
		int correct = 0;

for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
	posAnnos.add(tokenAnno.getPos().getPosValue());
	posTexts.add(tokenAnno.getCoveredText());
}

      int nrOfDocuments = posAnnos.size();

  for (int i = 0; i<posAnnos.size(); i+=2) {
	  String first = posAnnos.get(i);
	  String second = posAnnos.get(i + 1);
	  
      System.out.println("Token: " + posTexts.get(i));
	  System.out.println(first + " detected as " + second);
	  
	  if (first.equals(second)) {
		  System.out.println("Correctly tagged!");
		  correct++;
	  } else {
		  System.out.println("Wrongly tagged!");
	  }
  }
  
      System.out.println(correct + " out of " + posAnnos.size() + " POS tags correctly tagged. Scored an accuracy of " + ((double)correct/(double)nrOfDocuments)*100 + "%.");


	}
	
	
}

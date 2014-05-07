package de.unidue.langtech.teaching.suenme.pipeline;

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

for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
	  System.out.println(tokenAnno.getCoveredText() + " " + tokenAnno.getPos().getPosValue());
}
	}
}

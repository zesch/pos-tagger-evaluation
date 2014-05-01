package de.unidue.langtech.teaching.pp.example.pipeline;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.GoldPOS;

public class Generic_Component
        extends JCasAnnotator_ImplBase
{

     
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		

		
		for (Token tokenAnno : JCasUtil.select(aJCas, Token.class)) {
			
			GoldPOS goldpos = new GoldPOS(aJCas);
			goldpos.setPosTag("NN");
			goldpos.addToIndexes();
		  
		  //show text & POS-Tag
  		  System.out.println(tokenAnno.getCoveredText() + " " + tokenAnno.getPos().getPosValue());
    }
		
	}
	

}


package de.unidue.langtech.teaching.suenme.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.suenme.type.GoldPOS;


public class GenericComponent
        extends JCasAnnotator_ImplBase
{

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		//add POS to delete
		List<POS> toDelete = new ArrayList<POS>();
		
		for (Token tokenAnno : JCasUtil.select(aJCas, Token.class)) {
			
			POS posValue = tokenAnno.getPos();
				
				GoldPOS goldPOS = new GoldPOS(aJCas);
				goldPOS.setPosTag(posValue);
	            goldPOS.addToIndexes();
	            toDelete.add(posValue);
	           
			}
	
    for (POS pos : toDelete) {
    	//delete all POS annotations
        pos.removeFromIndexes();
    }
    
}
}


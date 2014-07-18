package de.unidue.langtech.teaching.ba.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.ba.type.GoldPOS;

/**
 * puts POS tag into new annotation named GoldPOS and deletes old POS tag
 * @author Onur
 *
 */
public class GoldPOSAnnotator
        extends JCasAnnotator_ImplBase
{

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		//add POS to delete
		List<POS> toDelete = new ArrayList<POS>();
		
		for (Token tokenAnno : JCasUtil.select(aJCas, Token.class)) {
			
			POS posValue = tokenAnno.getPos();
			String cPosValue = tokenAnno.getPos().getClass().getSimpleName();
				
				GoldPOS goldPOS = new GoldPOS(aJCas);
				goldPOS.setPosTag(posValue);
				goldPOS.setCPosTag(cPosValue);
				
				//set begin and end, otherwise GoldPOS annotations are not created in correct order
				goldPOS.setBegin(posValue.getBegin());
				goldPOS.setEnd(posValue.getEnd());
	            goldPOS.addToIndexes();
	            toDelete.add(posValue);
	           
			}
	
    for (POS pos : toDelete) {
    	//delete all POS annotations
        pos.removeFromIndexes();
    }
    
}
}


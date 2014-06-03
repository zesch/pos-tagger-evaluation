package de.unidue.langtech.teaching.suenme.pipeline;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;

public class TreeTaggerPipeline {
	
	

	public static void main(String[] args) throws UIMAException, IOException {
    	
    	JCas jcas = JCasFactory.createJCas();
    	jcas.setDocumentText("This is a test");
    	jcas.setDocumentLanguage("de");
		    	
    	SimplePipeline.runPipeline(
				jcas,
				AnalysisEngineFactory.createEngineDescription(BreakIteratorSegmenter.class),
				AnalysisEngineFactory.createEngineDescription(TreeTaggerPosLemmaTT4J.class));

	
	
	 for (Token tokenAnno : JCasUtil.select(jcas, Token.class)) {
		   System.out.println(tokenAnno.getCoveredText() + "\t" + tokenAnno.getPos().getClass().getSimpleName());
		  
	 }
	}

}

package org.dkpro.pos.readerDev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class Printer extends JCasAnnotator_ImplBase {
	private static final String TEXT_BEGIN = "\"text\":\"";
	private static final String TEXT_END = "\",";
	
	List<String> data = new ArrayList<String>();

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
//		// We get all data types in this JCas (aka a single tweet) and then
//		// print each word or token
//	    System.out.println(aJCas.getDocumentText());
//		Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
//		for (Token token : tokens) {
//			String tp = token.getCoveredText() + "_"
//					+ token.getPos().getPosValue();
//			data.add(tp);
//		}
//		System.out.println();
	}
	
	@Override
	public void collectionProcessComplete() {
	    try {
            FileUtils.writeLines(new File("target/masc"), data);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}

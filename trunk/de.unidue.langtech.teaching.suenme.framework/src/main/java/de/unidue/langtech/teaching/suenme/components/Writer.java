package de.unidue.langtech.teaching.suenme.components;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.suenme.type.GoldPOS;

/**
 * Writes information to a text file in this order
 * Token<TAB>GoldPos<TAB>DetectedPos
 * @author Onur
 *
 */
public class Writer   extends JCasAnnotator_ImplBase {
	
	public static final String PARAM_OUTPUT_FILE = "OutputFile";
    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    private File outFile;

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		  for (Token tokenAnno : JCasUtil.select(aJCas, Token.class)) {
			  for (GoldPOS goldPos : JCasUtil.selectCovered(GoldPOS.class, tokenAnno)) {		
			  try {
				FileUtils.writeStringToFile(outFile, tokenAnno.getCoveredText() + "\t" + goldPos.getPosTag().getPosValue() + "\t" + tokenAnno.getPos().getPosValue() + "\n", true);
			} catch (IOException e) {
				throw new AnalysisEngineProcessException(e);
			      }
				                         }
		  }
	}
	     }



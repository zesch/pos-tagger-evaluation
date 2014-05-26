package de.unidue.langtech.stats;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class SentenceLenFilter extends JCasAnnotator_ImplBase {
	
	 public static final String PARAM_PREFIX = "PARAM_PREFIX";
	    @ConfigurationParameter(name = PARAM_PREFIX, mandatory = true)
	    private String prefix;


	static int sentenceCount = 0;
	static int tokenCount = 0;

	BufferedWriter brShort;
	BufferedWriter brLong;
	
	int shortSen=0;
	int longSen=0;

	int LIMIT = 400;
	
	@Override
	public void initialize(final UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		try {
			brShort = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(
							"target/"+prefix+"short.txt")), "utf-8"));
			brLong= new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(
							"target/"+prefix+"long.txt")), "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
		for (Sentence s : sentences) {
			List<Token> tokens = JCasUtil.selectCovered(aJCas, Token.class,
					s.getBegin(), s.getEnd());
			
			if (tokens.size() >= 7 && tokens.size() <= 10 && shortSen < LIMIT){
			    shortSen++;
				write(brShort, tokens);
			}
			if (tokens.size() >= 40 && longSen < LIMIT) {
			    longSen++;
				write(brLong, tokens);
			}
			
			tokenCount += tokens.size();
			sentenceCount++;
		}

	}

	private void write(BufferedWriter writer, List<Token> tokens) {
		try {
		for(Token t : tokens){
		
				writer.write(t.getCoveredText() + " "+t.getPos().getPosValue());
				writer.write("\n");
			
		}
		writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		System.out.println("Short: " + shortSen+ " Long: "
				+ longSen);
		try {
			brShort.close();
			brLong.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

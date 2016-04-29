package de.unidue.ltl.tagger.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.ltl.tagger.type.GoldPOS;

public abstract class SequenceReportAbstract
    extends JCasAnnotator_ImplBase
{

    public static final String PARAM_OUTPUT_FOLDER = "outputFolder";
    @ConfigurationParameter(name = PARAM_OUTPUT_FOLDER, mandatory = true)
    private String outputFolder;

    public static final String PARAM_AUTO_CORRECT_TAGS = "autoCorrectTags";
    @ConfigurationParameter(name = PARAM_AUTO_CORRECT_TAGS, mandatory = false)
    private String[] autoCorrect;
    private Set<String> autoCorrectionSet = new HashSet<String>();

    public static final String FINE = "fine";
    public static final String COARSE = "coarse";

    public static String SUFFIX = null;

    BufferedWriter bwFine, bwCoarse;
    protected int sentId = 0;

    @Override
    public void initialize(final UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);
        String file = outputFolder+"/";
        try {
            bwFine = makeBufferedWriter(file, FINE + SUFFIX);
            bwCoarse = makeBufferedWriter(file, COARSE + SUFFIX);

            writeHeader(bwFine);
            writeHeader(bwCoarse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        buildAutoCorrectionSet();

    }

    private void buildAutoCorrectionSet()
    {
        if (autoCorrect == null){
            return;
        }
        for(String s : autoCorrect){
            autoCorrectionSet.add(s);
        }
        
    }

    protected void writeHeader(BufferedWriter bw)
        throws Exception
    {
    }

    private BufferedWriter makeBufferedWriter(String file, String suffix)
        throws Exception
    {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(file
                + "seqEval_" + suffix))));
    }

    @Override
    public void process(JCas aJCas)
        throws AnalysisEngineProcessException
    {

        for (Sentence s : JCasUtil.select(aJCas, Sentence.class)) {
            sentId++;
            List<String> tokens = new ArrayList<String>();
            List<String> fineGold = new ArrayList<String>();
            List<String> coarseGold = new ArrayList<String>();
            List<String> finePred = new ArrayList<String>();
            List<String> coarsePred = new ArrayList<String>();

            List<Token> tt = JCasUtil.selectCovered(aJCas, Token.class, s.getBegin(), s.getEnd());
            for (Token t : tt) {

                tokens.add(t.getCoveredText());

                //Gold
                GoldPOS goldPos = JCasUtil.selectCovered(aJCas, GoldPOS.class, t.getBegin(),
                        t.getEnd()).get(0);
                String fineGoldValue = goldPos.getPosTag().getPosValue();
                fineGold.add(fineGoldValue);
                coarseGold.add(goldPos.getCPosTag());
                
                //Prediction
                POS pos = t.getPos();
                finePred.add(autoCorrectFineTag(pos.getPosValue(), fineGoldValue));
                coarsePred.add(pos.getClass().getSimpleName());
            }

            try {
                process(bwFine, tokens, fineGold, finePred);
                process(bwCoarse, tokens, coarseGold, coarsePred);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String autoCorrectFineTag(String predictedPosValue, String fineGoldValue)
    {
        if (autoCorrectionSet.contains(fineGoldValue)){
            return fineGoldValue;
        }
        
        return predictedPosValue;
    }

    protected abstract void process(BufferedWriter bw, List<String> tokens, List<String> gold,
            List<String> pred)
        throws IOException;

    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        try {
            writeFooter(bwFine);
            writeFooter(bwCoarse);

            bwFine.close();
            bwCoarse.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void writeFooter(BufferedWriter bw)
        throws Exception
    {

    }

}

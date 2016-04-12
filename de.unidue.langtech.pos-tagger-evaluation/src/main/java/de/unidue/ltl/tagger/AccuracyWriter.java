package de.unidue.ltl.tagger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.ltl.tagger.uima.LabelUnifier;
import de.unidue.ltl.toobee.poseval.ConfusionMatrix;
import de.unidue.ltl.toobee.poseval.type.GoldPOS;

public class AccuracyWriter
    extends JCasAnnotator_ImplBase
    implements Keys
{

    public static final String PARAM_FINE_RESULT = "accFine";
    @ConfigurationParameter(name = PARAM_FINE_RESULT, mandatory = true)
    private File accFine;

    public static final String PARAM_COARSE_RESULT = "accCoarse";
    @ConfigurationParameter(name = PARAM_COARSE_RESULT, mandatory = true)
    private File accCoarse;

    private ConfusionMatrix fine;
    private ConfusionMatrix coarse;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

        fine = new ConfusionMatrix();
        coarse = new ConfusionMatrix();
    }

    @Override
    public void process(JCas jCas)
        throws AnalysisEngineProcessException
    {
        for (Token tokenAnno : JCasUtil.select(jCas, Token.class)) {
            for (GoldPOS goldPos : JCasUtil.selectCovered(GoldPOS.class, tokenAnno)) {

                String fineGold = goldPos.getPosTag().getPosValue();
                String finePred = tokenAnno.getPos().getPosValue();
                fine.registerOutcome(fineGold, LabelUnifier.mapLabels(finePred, fineGold));

                String coarseGold = goldPos.getCPosTag();
                String coarsePred = tokenAnno.getPos().getClass().getSimpleName();
                coarse.registerOutcome(coarseGold,
                        LabelUnifier.mapCoarse(coarsePred, coarseGold, tokenAnno.getCoveredText()));
            }
        }
    }

    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();

        try {

            Properties accFineProp = new Properties();
            accFineProp.setProperty(PROP_ACC_FINE, "" + fine.getWordClassAccuracy());
            accFineProp.store(new FileOutputStream(accFine), "fine accuracy");

            Properties accCoarseProp = new Properties();
            accCoarseProp.setProperty(PROP_ACC_COARSE, "" + coarse.getWordClassAccuracy());
            accCoarseProp.store(new FileOutputStream(accCoarse), "coarse accuracy");
        }
        catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }

    }

}

package de.unidue.ltl.tagger.report;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.unidue.ltl.tagger.type.GoldPOS;

public class ConfusionMatrixReport
    extends JCasAnnotator_ImplBase
{

    public static final String PARAM_OUTPUT_FOLDER = "outputFolder";
    @ConfigurationParameter(name = PARAM_OUTPUT_FOLDER, mandatory = true)
    private String outputFolder;

    ConfusionMatrix fine = new ConfusionMatrix();
    ConfusionMatrix coarse = new ConfusionMatrix();

    @Override
    public void initialize(final UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

    }

    @Override
    public void process(JCas arg0)
        throws AnalysisEngineProcessException
    {

        for (Sentence s : JCasUtil.select(arg0, Sentence.class)) {

            List<GoldPOS> gold = JCasUtil.selectCovered(arg0, GoldPOS.class, s.getBegin(),
                    s.getEnd());
            List<POS> pred = JCasUtil.selectCovered(arg0, POS.class, s.getBegin(), s.getEnd());
            
            for (int i = 0; i < pred.size(); i++) {
                String predictionFine = pred.get(i).getPosValue();
                String goldFine = gold.get(i).getPosTag().getPosValue();
                fine.registerOutcome(goldFine, predictionFine);

                String predictionCoarse = pred.get(i).getClass().getSimpleName();
                String goldCoarse = gold.get(i).getCPosTag();
                coarse.registerOutcome(goldCoarse, predictionCoarse);
            }

        }

    }

    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        String fileFine = outputFolder + "/" + "fineMatrix.txt";
        String fileCoarse = outputFolder + "/" +"coarseMatrix.txt";

        String fineFormattedMatrix = fine.getFormattedMatrix()
                + fine.getWordClassAccuracyOverview();
        String coarseFormattedMatrix = coarse.getFormattedMatrix()
                + coarse.getWordClassAccuracyOverview();
        try {
            FileUtils.writeStringToFile(new File(fileFine), fineFormattedMatrix);
            FileUtils.writeStringToFile(new File(fileCoarse), coarseFormattedMatrix);
        }
        catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }

    }

}

package de.unidue.ltl.tagger.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class TagPrecisionRecallF1Report
    extends JCasAnnotator_ImplBase
{

    public static final String PARAM_OUTPUT_FOLDER = "outputFolder";
    @ConfigurationParameter(name = PARAM_OUTPUT_FOLDER, mandatory = true)
    private String outputFolder;

    List<String> fineGold = new ArrayList<String>();
    List<String> finePred = new ArrayList<String>();
    List<String> coarseGold = new ArrayList<String>();
    List<String> coarsePred = new ArrayList<String>();

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

                fineGold.add(goldFine);
                finePred.add(predictionFine);

                String predictionCoarse = pred.get(i).getClass().getSimpleName();
                String goldCoarse = gold.get(i).getCPosTag();

                coarseGold.add(goldCoarse);
                coarsePred.add(predictionCoarse);
            }

        }

    }

    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {

        List<String[]> fineCalcPreRecF1 = calcPreRecF1(fineGold, finePred);
        List<String[]> coarseCalcPreRecF1 = calcPreRecF1(coarseGold, coarsePred);

        try {
            writeResult(fineCalcPreRecF1, new File(outputFolder + "/fineTagResults.txt"));
            writeResult(coarseCalcPreRecF1, new File(outputFolder + "/coarseTagResults.txt"));
        }
        catch (Exception e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    private void writeResult(List<String[]> calcPreRecF1, File target)
        throws IOException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Tag\tRec\tPrec\tF1\n");
        for (String[] e : calcPreRecF1) {
            for (int i = 0; i < e.length; i++) {
                sb.append(e[i]);
                if (i + 1 < e.length) {
                    sb.append("\t");
                }
                else {
                    sb.append("\n");
                }
            }
        }

        FileUtils.writeStringToFile(target, sb.toString(), "utf-8");

    }

    private List<String[]> calcPreRecF1(List<String> g, List<String> p)
    {
        List<String[]> results = new ArrayList<>();

        List<String> allGoldTags = new ArrayList<>(new HashSet<>(g));
        Collections.sort(allGoldTags);

        for (String t : allGoldTags) {
            double tp = 0, fp = 0, tn = 0, fn = 0;
            for (int i = 0; i < g.size(); i++) {
                String gold = g.get(i);
                String pred = p.get(i);

                if (!gold.equals(t) && !pred.equals(t)) {
                    tn++;
                }
                else if (!gold.equals(t) && pred.equals(t)) {
                    fp++;
                }
                else if (gold.equals(t) && !pred.equals(t)) {
                    fn++;
                }
                else if (gold.equals(t) && pred.equals(t)) {
                    tp++;
                }
            }

            double precision = tp / (tp + fp);
            double recall = tp / (tp + fn);
            double f1 = (2 * (precision * recall)) / (precision + recall);

            results.add(new String[] { t, String.format("%.2f", recall),
                    String.format("%.2f", precision), String.format("%.2f", f1) });
        }

        return results;
    }

}

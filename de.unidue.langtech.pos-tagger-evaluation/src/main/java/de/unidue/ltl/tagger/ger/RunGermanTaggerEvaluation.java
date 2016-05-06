package de.unidue.ltl.tagger.ger;

import java.io.FileInputStream;
import java.util.Properties;

import org.dkpro.lab.Lab;
import org.dkpro.lab.task.Dimension;
import org.dkpro.lab.task.ParameterSpace;
import org.dkpro.lab.task.impl.DefaultBatchTask;

import de.unidue.ltl.tagger.report.DetailReport;
import de.unidue.ltl.tagger.report.DomainDetailReport;
import de.unidue.ltl.tagger.report.TaggerDomainReport;
import de.unidue.ltl.tagger.report.TaggerReport;
import de.unidue.ltl.tagger.task.RunTaggerTask;

public class RunGermanTaggerEvaluation
{
    public static String LANG = "de";

    public static final String READER = "reader";
    public static final String TAGGER = "tagger";

    public static void main(String[] args)
        throws Exception
    {

        String config = null;
        if (args.length == 1) {
            config = args[0];
        }
        else {
            config = "src/test/java/test.properties";
        }
        System.setProperty("DKPRO_HOME", System.getProperty("user.home") + "/Desktop/");
        FileInputStream input = new FileInputStream(config);
        Properties prop = new Properties();
        prop.load(input);

        String mappingFolderPrefix = prop.getProperty("mappingFolderPrefix") + "/";
        String germanDefaultMap = prop.getProperty("germanDefaultMap") + "/";
        String corporaFolderPrefix = prop.getProperty("corporaFolder") + "/";

        GermanCorpora.corporaFolderPrefix = corporaFolderPrefix;
        GermanCorpora.mappingFolderPrefix = mappingFolderPrefix;
        GermanCorpora.defaultMap = germanDefaultMap;

        Dimension<Object> dimReaderDesc = Dimension.create(
                "readerDesc",
                // written
//                GermanCorpora.getFormalTuebadz(),
//                GermanCorpora.getFormalHamburgTreebank(),
                //social
                GermanCorpora.getSocialRehbein(),
                GermanCorpora.getSocialNeunerdt()
                );

        GermanTagger.corporaFolderPrefix = corporaFolderPrefix;
        GermanTagger.mappingFolderPrefix = mappingFolderPrefix;
        GermanTagger.defaultMap = germanDefaultMap;

        Dimension<Object> dimTaggerDesc = Dimension.create("taggerDesc",
//                GermanTagger.getHunTagger(),
//                GermanTagger.getMateTagger(),
//                GermanTagger.getOpenNlpTaggerMaxent(),
//                GermanTagger.getOpenNlpTaggerPerceptron(),
                GermanTagger.getRfTagger(),
//                GermanTagger.getStanfordPosTaggerDeWac(),
//                GermanTagger.getStanfordPosTaggerHgc(),
                GermanTagger.getStanfordPosTaggerFast(),
                GermanTagger.getStanfordPosTaggerFastCaseless(),
                GermanTagger.getTreeTagger()
                );

        ParameterSpace pSpace = new ParameterSpace(dimReaderDesc, dimTaggerDesc);

        DefaultBatchTask batch = new DefaultBatchTask();
        batch.setParameterSpace(pSpace);
        batch.addTask(new RunTaggerTask());
        batch.addReport(DetailReport.class);
        batch.addReport(DomainDetailReport.class);
        batch.addReport(TaggerReport.class);
        batch.addReport(TaggerDomainReport.class);
        Lab.getInstance().run(batch);
    }
}
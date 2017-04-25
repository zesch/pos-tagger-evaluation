package de.unidue.ltl.tagger;

import java.io.FileInputStream;
import java.util.Properties;

import org.dkpro.lab.Lab;
import org.dkpro.lab.task.Dimension;
import org.dkpro.lab.task.ParameterSpace;
import org.dkpro.lab.task.impl.DefaultBatchTask;

import de.unidue.ltl.tagger.components.Keys;
import de.unidue.ltl.tagger.report.DetailReport;
import de.unidue.ltl.tagger.report.DomainDetailReport;
import de.unidue.ltl.tagger.report.TaggerDomainReport;
import de.unidue.ltl.tagger.report.TaggerReport;
import de.unidue.ltl.tagger.task.RunTaggerTask;

public class RunEnglishTaggerEvaluation
{
    public static String LANG = "en";

    public static final String READER = "reader";
    public static final String TAGGER = "tagger";

    public static void main(String[] args)
        throws Exception
    {

        String config = args[0];
        System.setProperty("DKPRO_HOME", System.getProperty("user.home") + "/Desktop/");
        FileInputStream input = new FileInputStream(config);
        Properties prop = new Properties();
        prop.load(input);

        String mappingFolderPrefix = prop.getProperty("mappingFolderPrefix") + "/";
        String englishDefaultMap = prop.getProperty("englishDefaultMap") + "/";
        String corporaFolderPrefix = prop.getProperty("corporaFolder") + "/";

        EnglishCorpora.corporaFolderPrefix = corporaFolderPrefix;
        EnglishCorpora.mappingFolderPrefix = mappingFolderPrefix;
        EnglishCorpora.defaultMap = englishDefaultMap;

        Dimension<Object> dimReaderDesc = Dimension.create(
                Keys.DISCRIMINATOR_READER,
////                // social
                EnglishCorpora.getSocialNpsIrcChat(),                
                EnglishCorpora.getSocialGimpel(),
                EnglishCorpora.getSocialAaveTwitter(),
////                // spoken
                EnglishCorpora.getSpokenTedTalk(),
                EnglishCorpora.getSpokenBnc(),
                EnglishCorpora.getSpokenSwitchboard(),
                EnglishCorpora.getSpokenGumInverview(),
                // written
                EnglishCorpora.getWrittenGumHowto(),
                EnglishCorpora.getWrittenGumNews(),
                EnglishCorpora.getWrittenGumVoyage(),                
                EnglishCorpora.getWrittenBnc100k(),
                EnglishCorpora.getWrittenBrown()
                
                );

        EnglishTagger.corporaFolderPrefix = corporaFolderPrefix;
        EnglishTagger.mappingFolderPrefix = mappingFolderPrefix;
        EnglishTagger.englishDefaultMap = englishDefaultMap;

        Dimension<Object> dimTaggerDesc = Dimension.create(Keys.DISCRIMINATOR_TAGGER,
                EnglishTagger.getStanfordBidirectionalDistSim(),
                EnglishTagger.getStanfordCaselessLeft3WordsDistim(),
                EnglishTagger.getStanfordFast41(), 
                EnglishTagger.getStanfordTwitter(),
                EnglishTagger.getStanfordTwitterFast(),
                EnglishTagger.getStanfordWsj018CaselessLeft3wordsDistsim(),
//
                EnglishTagger.getArkDefault(), 
                EnglishTagger.getArkIrc(),
                EnglishTagger.getArkRitter(),
//
                EnglishTagger.getOpenNlpMaxent(),
                EnglishTagger.getOpenNlpPerceptron(),
//
                EnglishTagger.getClearNlpOntonotes(),
                EnglishTagger.getClearNlpMayo(),
//
                EnglishTagger.getHeppleTagger(),
                EnglishTagger.getHunPosTagger(),
                EnglishTagger.getMateTagger(),
                
                EnglishTagger.getTreetagger(),
        		EnglishTagger.getDefaultTagger(),
        		EnglishTagger.getMajorityTaggerWsj()
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

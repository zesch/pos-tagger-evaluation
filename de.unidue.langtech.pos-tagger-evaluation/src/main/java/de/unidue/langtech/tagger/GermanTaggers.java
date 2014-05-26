package de.unidue.langtech.tagger;

import java.util.ArrayList;
import java.util.List;

import de.tudarmstadt.ukp.dkpro.core.hunpos.HunPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosTagger;

public class GermanTaggers
{
	static String MAPPING_FOLDER_PREFIX = "";
    static String posMappingFile =  "/de-stts-pos.map";

    static List<TaggerConfiguration> getOpenNlpTagger()
    {
        List<TaggerConfiguration> taggersDe = new ArrayList<TaggerConfiguration>();
        taggersDe.add(new TaggerConfiguration(OpenNlpPosTagger.class, posMappingFile, "de",
                "maxent"));
        taggersDe.add(new TaggerConfiguration(OpenNlpPosTagger.class, posMappingFile, "de",
                "perceptron"));
        return taggersDe;
    }

    static List<TaggerConfiguration> getStanfordTagger()
    {
        List<TaggerConfiguration> taggersDe = new ArrayList<TaggerConfiguration>();
        taggersDe.add(new TaggerConfiguration(StanfordPosTagger.class, posMappingFile, "de",
                "dewac"));
        taggersDe
                .add(new TaggerConfiguration(StanfordPosTagger.class, posMappingFile, "de", "hgc"));
        taggersDe
                .add(new TaggerConfiguration(StanfordPosTagger.class, posMappingFile, "de", "fast"));
        taggersDe.add(new TaggerConfiguration(StanfordPosTagger.class, posMappingFile, "de",
                "fast-caseless"));
        return taggersDe;
    }

    static List<TaggerConfiguration> getMatePosTagger()
    {
        List<TaggerConfiguration> taggersDe = new ArrayList<TaggerConfiguration>();
        taggersDe.add(new TaggerConfiguration(MatePosTagger.class, posMappingFile, "de", "tiger"));
        return taggersDe;
    }

    static List<TaggerConfiguration> getHunPosTagger()
    {
        List<TaggerConfiguration> taggersDe = new ArrayList<TaggerConfiguration>();
        taggersDe.add(new TaggerConfiguration(HunPosTagger.class, posMappingFile, "de", "tiger"));
        return taggersDe;
    }
    
    static List<TaggerConfiguration> getTreeTagger()
    {
        List<TaggerConfiguration> taggersDe = new ArrayList<TaggerConfiguration>();
        taggersDe.add(new TaggerConfiguration(TreeTaggerPosTagger.class, posMappingFile, "de", "le"));
        return taggersDe;
    }
}

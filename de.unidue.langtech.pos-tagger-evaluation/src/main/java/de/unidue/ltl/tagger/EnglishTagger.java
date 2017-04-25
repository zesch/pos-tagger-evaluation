package de.unidue.ltl.tagger;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;

import de.tudarmstadt.ukp.dkpro.core.arktools.ArktweetPosTagger;
import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.gate.HepplePosTagger;
import de.tudarmstadt.ukp.dkpro.core.hunpos.HunPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosTagger;
import de.unidue.ltl.lstmTaggerAuxLoss.LstmAuxLossTagger;
import de.unidue.ltl.majoritytagger.DefaultTagger;
import de.unidue.ltl.majoritytagger.MajorityTagTagger;
import de.unidue.ltl.tagger.components.NamedTaggerDescription;

public class EnglishTagger
{
    public static final String LANG = "en";
    public static String corporaFolderPrefix;
    public static String mappingFolderPrefix;
    public static String englishDefaultMap;

    public static NamedTaggerDescription getStanfordBidirectionalDistSim()
        throws Exception
    {
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_VARIANT, "bidirectional-distsim",
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", "bidirectional-distsim",
                desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordWsj018CaselessLeft3wordsDistsim()
        throws Exception
    {
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_VARIANT, "wsj-0-18-caseless-left3words-distsim",
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford",
                "wsj-0-18-caseless-left3words-distsim", desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordCaselessLeft3WordsDistim()
        throws Exception
    {
        String variant = "caseless-left3words-distsim";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_VARIANT, variant,
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);
        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordFast41()
        throws Exception
    {
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_VARIANT, "fast.41",
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);
        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", "fast.41", desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordTwitter()
        throws Exception
    {
        String variant = "twitter";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_VARIANT, variant,
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);
        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordTwitterFast()
        throws Exception
    {
        String variant = "twitter-fast";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_VARIANT, variant,
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getOpenNlpMaxent()
        throws Exception
    {
        String variant = "maxent";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, LANG,
                OpenNlpPosTagger.PARAM_VARIANT, "maxent",
                OpenNlpPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("OpenNlp", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getOpenNlpPerceptron()
        throws Exception
    {
        String variant = "perceptron";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, LANG,
                OpenNlpPosTagger.PARAM_VARIANT, variant,
                OpenNlpPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("OpenNlp", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getClearNlpMayo()
        throws Exception
    {
        String variant = "mayo";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                ClearNlpPosTagger.class, ClearNlpPosTagger.PARAM_LANGUAGE, LANG,
                ClearNlpPosTagger.PARAM_VARIANT, variant,
                ClearNlpPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("ClearNlp", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getClearNlpOntonotes()
        throws Exception
    {
        String variant = "ontonotes";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                ClearNlpPosTagger.class, ClearNlpPosTagger.PARAM_LANGUAGE, LANG,
                ClearNlpPosTagger.PARAM_VARIANT, variant,
                ClearNlpPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("ClearNlp", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getArkDefault()
        throws Exception
    {
        String variant = "default";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                ArktweetPosTagger.class, ArktweetPosTagger.PARAM_LANGUAGE, LANG,
                ArktweetPosTagger.PARAM_VARIANT, variant,
                ArktweetPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + "en-arktweet-pos.map");

        NamedTaggerDescription ntd = new NamedTaggerDescription("Arktweets", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getArkIrc()
        throws Exception
    {
        String variant = "irc";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                ArktweetPosTagger.class, ArktweetPosTagger.PARAM_LANGUAGE, LANG,
                ArktweetPosTagger.PARAM_VARIANT, variant,
                ArktweetPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);
        NamedTaggerDescription ntd = new NamedTaggerDescription("Arktweets", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getArkRitter()
        throws Exception
    {
        String variant = "ritter";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                ArktweetPosTagger.class, ArktweetPosTagger.PARAM_LANGUAGE, LANG,
                ArktweetPosTagger.PARAM_VARIANT, variant,
                ArktweetPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Arktweets", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getTreetagger()
        throws Exception
    {
        String variant = "le";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                TreeTaggerPosTagger.class, TreeTaggerPosTagger.PARAM_LANGUAGE, LANG,
                TreeTaggerPosTagger.PARAM_VARIANT, variant,
                TreeTaggerPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + "en-ptb-tt-pos.map");

        NamedTaggerDescription ntd = new NamedTaggerDescription("TreeTagger", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getMateTagger()
        throws Exception
    {
        String variant = "conll2009";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                MatePosTagger.class, MatePosTagger.PARAM_LANGUAGE, LANG,
                MatePosTagger.PARAM_VARIANT, variant, MatePosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("MateTagger", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getHeppleTagger()
        throws Exception
    {
        String variant = "";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                HepplePosTagger.class, HepplePosTagger.PARAM_LANGUAGE, LANG,
                HepplePosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Hepple", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getHunPosTagger()
        throws Exception
    {
        String variant = "wsj";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                HunPosTagger.class, HunPosTagger.PARAM_LANGUAGE, LANG, HunPosTagger.PARAM_VARIANT,
                variant, HunPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("HunPos", variant, desc);
        return ntd;
    }

    // Not released tagger
    public static NamedTaggerDescription getDefaultTagger()
        throws Exception
    {
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                DefaultTagger.class, DefaultTagger.PARAM_LANGUAGE, LANG,
                DefaultTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("DefaultTagger", "", desc);
        return ntd;
    }

    // Not released tagger
    public static NamedTaggerDescription getMajorityTaggerWsj()
        throws Exception
    {
        String variant = "wsj0-18";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                MajorityTagTagger.class, MajorityTagTagger.PARAM_LANGUAGE, LANG,
                MajorityTagTagger.PARAM_VARIANT, variant,
                MajorityTagTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("MajorityTagger", variant, desc);
        return ntd;
    }

    // This tagger depends on a local-backend installation
    public static NamedTaggerDescription getLstmTaggerWsj018()
        throws Exception
    {
        String variant = "wsj018";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                LstmAuxLossTagger.class, LstmAuxLossTagger.PARAM_LANGUAGE, "en",
                LstmAuxLossTagger.PARAM_MODEL_LOCATION,
                "/home/LTLAB/horsmann/plankTagger/model/out/en-wsj.model",
                LstmAuxLossTagger.PARAM_TAGGER_MAIN_PATH,
                "/home/LTLAB/horsmann/plankTagger/bilstm-aux/src/bilty.py",
                LstmAuxLossTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + englishDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("LstmAuxLoss", variant, desc);
        return ntd;
    }

    // Not working anymore
    // public static NamedTaggerDescription getLbjTagger()
    // throws Exception
    // {
    // String variant = "";
    // AnalysisEngineDescription desc =
    // AnalysisEngineFactory.createEngineDescription(
    // IllinoisPosTagger.class, IllinoisPosTagger.PARAM_LANGUAGE, LANG,
    // IllinoisPosTagger.PARAM_POS_MAPPING_LOCATION, mappingFolderPrefix + "/"
    // + "en-lbj-pos.map");
    //
    // NamedTaggerDescription ntd = new NamedTaggerDescription("Lbj", variant,
    // desc);
    // return ntd;
    // }

}

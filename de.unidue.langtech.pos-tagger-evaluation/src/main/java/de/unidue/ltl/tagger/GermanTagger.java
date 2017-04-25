package de.unidue.ltl.tagger;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;

import de.tudarmstadt.ukp.dkpro.core.hunpos.HunPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.rftagger.RfTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosTagger;
import de.unidue.ltl.majoritytagger.MajorityTagTagger;
import de.unidue.ltl.tagger.components.NamedTaggerDescription;

public class GermanTagger
{
    public static final String LANG = "de";
    public static String corporaFolderPrefix;
    public static String mappingFolderPrefix;
    public static String germanDefaultMap;

    public static NamedTaggerDescription getRfTagger()
        throws Exception
    {
        String variant = "tiger";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                RfTagger.class, RfTagger.PARAM_LANGUAGE, LANG, RfTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + "de-tiger-rftagger-pos.map",
                RfTagger.PARAM_MORPH_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + "de-tiger-rftagger-morph.map", RfTagger.PARAM_VARIANT,
                variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("RFTagger", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getTreeTagger()
        throws Exception
    {
        String variant = "le";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                TreeTaggerPosTagger.class, TreeTaggerPosTagger.PARAM_LANGUAGE, LANG,
                TreeTaggerPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("TreeTagger", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getOpenNlpTaggerMaxent()
        throws Exception
    {
        String variant = "maxent";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, LANG,
                OpenNlpPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, OpenNlpPosTagger.PARAM_VARIANT,
                variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("OpenNLP", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getOpenNlpTaggerPerceptron()
        throws Exception
    {
        String variant = "perceptron";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                OpenNlpPosTagger.class, OpenNlpPosTagger.PARAM_LANGUAGE, LANG,
                OpenNlpPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, OpenNlpPosTagger.PARAM_VARIANT,
                variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("OpenNLP", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordPosTaggerDeWac()
        throws Exception
    {
        String variant = "dewac";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, StanfordPosTagger.PARAM_VARIANT,
                variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordPosTaggerHgc()
        throws Exception
    {
        String variant = "hgc";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, StanfordPosTagger.PARAM_VARIANT,
                variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordPosTaggerFast()
        throws Exception
    {
        String variant = "fast";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, StanfordPosTagger.PARAM_VARIANT,
                variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getStanfordPosTaggerFastCaseless()
        throws Exception
    {
        String variant = "fast-caseless";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                StanfordPosTagger.class, StanfordPosTagger.PARAM_LANGUAGE, LANG,
                StanfordPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, StanfordPosTagger.PARAM_VARIANT,
                variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Stanford", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getMateTagger()
        throws Exception
    {
        String variant = "tiger";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                MatePosTagger.class, MatePosTagger.PARAM_LANGUAGE, LANG,
                MatePosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, MatePosTagger.PARAM_VARIANT, variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Mate", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getMajorityTaggerTiger()
        throws Exception
    {
        String variant = "tiger";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                MajorityTagTagger.class, MajorityTagTagger.PARAM_LANGUAGE, LANG,
                MajorityTagTagger.PARAM_VARIANT, variant,
                MajorityTagTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap);

        NamedTaggerDescription ntd = new NamedTaggerDescription("MajorityTagger", variant, desc);
        return ntd;
    }

    public static NamedTaggerDescription getHunTagger()
        throws Exception
    {
        String variant = "tiger";
        AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
                HunPosTagger.class, HunPosTagger.PARAM_LANGUAGE, LANG,
                HunPosTagger.PARAM_POS_MAPPING_LOCATION,
                mappingFolderPrefix + "/" + germanDefaultMap, HunPosTagger.PARAM_VARIANT, variant);

        NamedTaggerDescription ntd = new NamedTaggerDescription("Hun", variant, desc);
        return ntd;
    }
 
}

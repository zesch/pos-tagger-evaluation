package de.unidue.ltl.tagger;

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

public class MetaWriter
    extends JCasAnnotator_ImplBase
    implements Keys
{
    public static final String PARAM_CORPUS_FILE = "corpusFile";
    @ConfigurationParameter(name = PARAM_CORPUS_FILE, mandatory = true)
    private String corpusFile;

    public static final String PARAM_CORPUS_NAME = "corpusName";
    @ConfigurationParameter(name = PARAM_CORPUS_NAME, mandatory = true)
    private String corpusName;

    public static final String PARAM_CORPUS_DOMAIN= "corpusDomain";
    @ConfigurationParameter(name = PARAM_CORPUS_DOMAIN, mandatory = true)
    private String corpusDomain;

    public static final String PARAM_TAGGER_FILE = "taggerFile";
    @ConfigurationParameter(name = PARAM_TAGGER_FILE, mandatory = true)
    private String taggerFile;

    public static final String PARAM_TAGGER_NAME = "taggerName";
    @ConfigurationParameter(name = PARAM_TAGGER_NAME, mandatory = true)
    private String taggerName;

    public static final String PARAM_TAGGER_MODEL_NAME = "taggerModelName";
    @ConfigurationParameter(name = PARAM_TAGGER_MODEL_NAME, mandatory = true)
    private String taggerModelName;

    public static final String PARAM_TOKEN_COUNT_FILE = "tokenCountFile";
    @ConfigurationParameter(name = PARAM_TOKEN_COUNT_FILE, mandatory = true)
    private String tokenCountFile;

    int tokenCount = 0;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

    }

    @Override
    public void process(JCas jCas)
        throws AnalysisEngineProcessException
    {
        tokenCount += JCasUtil.select(jCas, Token.class).size();
    }

    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();

        try {
            Properties corpusProp = new Properties();
            corpusProp.setProperty(PROP_CORPUS_NAME, corpusName);
            corpusProp.setProperty(PROP_CORPUS_DOMAIN, corpusDomain);
            corpusProp.store(new FileOutputStream(corpusFile), "name/domain of the corpus");

            Properties taggerProp = new Properties();
            taggerProp.setProperty(PROP_TAGGER_NAME, taggerName);
            taggerProp.setProperty(PROP_TAGGER_MODEL_NAME, taggerModelName);
            taggerProp.store(new FileOutputStream(taggerFile), "name of the tagger and model");

            Properties tokenProp = new Properties();
            tokenProp.setProperty(PROP_TOKEN_COUNT, "" + tokenCount);
            tokenProp.setProperty(PROP_TOKEN_COUNT_1K, "" + ((double)tokenCount/1000));
            tokenProp.store(new FileOutputStream(tokenCountFile), "number of tokens in corpus");

        }
        catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

}

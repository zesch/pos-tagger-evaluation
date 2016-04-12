package de.unidue.ltl.tagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.performance.Stopwatch;

public class TimeNormalizer
    extends JCasAnnotator_ImplBase
    implements Keys
{
    public static final String PARAM_TIMER_FILE = "timerFile";
    @ConfigurationParameter(name = PARAM_TIMER_FILE, mandatory = true)
    private File timerFile;

    public static final String PARAM_TOKEN_COUNT_FILE = "tokenCountFile";
    @ConfigurationParameter(name = PARAM_TOKEN_COUNT_FILE, mandatory = true)
    private File tokenCountFile;
    
    public static final String PARAM_NORMALIZED_FILE = "normalizedFile";
    @ConfigurationParameter(name = PARAM_NORMALIZED_FILE, mandatory = true)
    private File normalizedFile;

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
    }

    @Override
    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        super.collectionProcessComplete();

        try {
            Properties timer = new Properties();
            timer.load(new FileInputStream(timerFile));
            double time = Double.valueOf(timer.getProperty(Stopwatch.KEY_SUM));

            Properties tokenCount = new Properties();
            tokenCount.load(new FileInputStream(tokenCountFile));
            double tok1k = Double.valueOf(tokenCount.getProperty(PROP_TOKEN_COUNT_1K).replaceAll(",", "."));
            
            double millTokPerSec = time / tok1k * 1000;
            Properties normalizedTimeTok = new Properties();
            normalizedTimeTok.setProperty(PROP_NORMALIZED_MIL_TOK_PER_SEC, ""+millTokPerSec);
            normalizedTimeTok.store(new FileOutputStream(normalizedFile), "million tokens per second");
        }
        catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

}

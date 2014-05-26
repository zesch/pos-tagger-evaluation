package de.unidue.langtech.stats;

import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class CorpusStatistics
    extends JCasAnnotator_ImplBase
{

    static int sentenceCount = 0;
    static int tokenCount = 0;

    @Override
    public void process(JCas aJCas)
        throws AnalysisEngineProcessException
    {
        Collection<Sentence> sentences = JCasUtil.select(aJCas, Sentence.class);
        for (Sentence s : sentences) {
            List<Token> tokens = JCasUtil.selectCovered(aJCas, Token.class, s.getBegin(),
                    s.getEnd());
            tokenCount += tokens.size();
            sentenceCount++;
        }

    }

    public void collectionProcessComplete()
        throws AnalysisEngineProcessException
    {
        System.out.println("Sentence: " + sentenceCount + " Tokens: " + tokenCount);
    }

}

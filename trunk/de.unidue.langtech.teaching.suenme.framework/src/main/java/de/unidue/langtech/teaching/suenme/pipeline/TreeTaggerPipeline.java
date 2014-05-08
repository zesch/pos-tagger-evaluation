package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;

/**
 * 
 * @author suenme
 *
 */
public class TreeTaggerPipeline
{
    public static void main(String[] args)
        throws Exception
    {
    	
     	CollectionReaderDescription reader = createReaderDescription(
                TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, "src/test/resources/test/Wikipedia_English.txt",
                TextReader.PARAM_LANGUAGE, "en"
                );


        SimplePipeline.runPipeline(
        		reader,
        		AnalysisEngineFactory.createEngineDescription(BreakIteratorSegmenter.class),
        		AnalysisEngineFactory.createEngineDescription(TreeTaggerPosLemmaTT4J.class),
        		AnalysisEngineFactory.createEngineDescription(Evaluator.class));
        
}
}

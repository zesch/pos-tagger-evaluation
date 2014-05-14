package de.unidue.langtech.teaching.suenme.pipeline;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import java.io.File;
import java.util.List;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.clearnlp.ClearNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.matetools.MatePosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;
import de.unidue.langtech.teaching.suenme.components.Evaluator2;
import de.unidue.langtech.teaching.suenme.components.GoldPOSAnnotator;
import de.unidue.langtech.teaching.suenme.components.Writer;
import de.unidue.langtech.teaching.suenme.reader.Conll2009Reader;
import dnl.utils.text.table.TextTable;


/**
 * 
 * @author suenme
 *
 */
public class GenericPipeline
{
    public static void main(String[] args)
        throws Exception
    {
    	//set enviroment variable, change to en for english data and change to correct extension
    	System.setProperty("PROJECT_HOME", "src\test\resources\test");
    	final String dkproHome = System.getenv("PROJECT_HOME");
    	String resources = dkproHome + "\\en";
    	String extension = "*.txt";

		CollectionReaderDescription reader = createReaderDescription(
				Conll2009Reader.class, 
				Conll2009Reader.PARAM_SOURCE_LOCATION, resources,
				Conll2009Reader.PARAM_PATTERNS, extension,
				Conll2009Reader.PARAM_LANGUAGE, "en");
		
    	Class[] tagger = new Class[] {OpenNlpPosTagger.class, MatePosTagger.class, StanfordPosTagger.class, 
    			TreeTaggerPosLemmaTT4J.class, ClearNlpPosTagger.class};
    	
    	for (int i=0; i<tagger.length; ) {
    		
            SimplePipeline.runPipeline(
            		reader,
            		AnalysisEngineFactory.createEngineDescription(GoldPOSAnnotator.class),
            		AnalysisEngineFactory.createEngineDescription(tagger[i]),
            		AnalysisEngineFactory.createEngineDescription(Writer.class,
            				Writer.PARAM_OUTPUT_FILE, dkproHome + "\\" + tagger[i].getSimpleName() + ".txt"));
            
            String[] columnNames = new String[tagger.length+2];
            columnNames[0] = "Token";
            columnNames[1] = "GoldPos";
            columnNames[i+2] = tagger[i].getSimpleName();
            
            List<Object> posInformation = Evaluator2.evaluate(new File(dkproHome + "\\" + tagger[i].getSimpleName() + ".txt"));
            
            List<String> tokens = (List<String>) posInformation.get(0);
            List<String> goldPos = (List<String>) posInformation.get(1);
            List<String> posAnnos = (List<String>) posInformation.get(2);
          
            String [][] posTags = new String[goldPos.size()][tagger.length+2];
            
            for(int row=0;row<posAnnos.size();row++){
                for (int col=0;col<tagger.length+1;col++){
                posTags[row][0] = tokens.get(row);
                posTags[row][1] = goldPos.get(row);
                posTags[row][i+2] = posAnnos.get(row);
                }
            }
            
            TextTable tt = new TextTable(columnNames, posTags); 
            tt.setAddRowNumbering(true);
            tt.printTable(); 
            
 
           i++;
           
    		
    	}
    	

  

}
}